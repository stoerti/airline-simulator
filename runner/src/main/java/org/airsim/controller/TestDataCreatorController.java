package org.airsim.controller;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.airsim.api.aircrafttype.CreateAircraftTypeCommand;
import org.airsim.api.airport.CreateAirportCommand;
import org.airsim.api.customer.CreateCustomerCommand;
import org.airsim.api.flightplan.CreateFlightplanCommand;
import org.airsim.api.flightplan.Weekplan;
import org.airsim.projection.jpa.AircraftTypeRepository;
import org.airsim.projection.jpa.AirportRepository;
import org.airsim.projection.jpa.CustomerRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/testdata")
@RequiredArgsConstructor
public class TestDataCreatorController {

    private final CommandGateway commandGateway;

    private final AircraftTypeRepository aircraftTypeRepository;
    private final AirportRepository airportRepository;
    private final CustomerRepository customerRepository;
    
    private final List<String> activeAirports = Arrays.asList("HAM", "FRA", "MUC", "TXL", "CDG"/*, "AMS", "LHR", "DUS"*/);

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createAllStatic();
    }

    @GetMapping("/all")
    public void createAll() {
        createAirports();
        createAircraftTypes();
        createFlights();
        createCustomers();
    }

    @GetMapping("/allstatic")
    public void createAllStatic() {
        createAirports();
        createAircraftTypes();
        createCustomers();
    }

    @GetMapping("/flights")
    public void createFlights() {
        Integer flightnumberCounter = 1;

        for (String from : activeAirports) {
            int j = 0;
            for (String to : activeAirports) {
                if (from.equals(to)) {
                    continue;
                }

                for (int i = 8; i < 23; i++) {

                    CreateFlightplanCommand flightplanCommand = CreateFlightplanCommand
                            .builder()
                            .id(UUID.randomUUID())
                            .flightnumber("AS" + Strings.padStart(flightnumberCounter.toString(), 3, '0'))
                            .airportFrom(from)
                            .airportTo(to)
                            .aircraftType("A388")
                            .takeoffTime(LocalTime.now().plusHours(i).plusMinutes(new Random().nextInt(10) - 5))
                            .duration(Duration.ofHours(1))
                            .validFrom(LocalDate.now())
                            .validTo(LocalDate.now().plusWeeks(3))
                            .weekplan(Weekplan.daily())
                            .build();
                    flightnumberCounter++;
                    j++;
                    commandGateway.send(flightplanCommand);
                }
            }
        }

    }

    @GetMapping("/airports")
    public void createAirports() {

        List<CreateAirportCommand> commands = Arrays.asList(CreateAirportCommand
                        .builder()
                        .id(UUID.randomUUID())
                        .iataCode("HAM")
                        .name("Hamburg Airport")
                        .fullName("Hamburg Airport")
                        .city("Hamburg")
                        .location(new GlobalPosition(53.630278d, 9.991111d, 0d))
                        .build(),
                CreateAirportCommand
                        .builder()
                        .id(UUID.randomUUID())
                        .iataCode("TXL")
                        .name("Berlin Tegel")
                        .fullName("Berlin Tegel")
                        .city("Berlin")
                        .location(new GlobalPosition(52.559722d, 13.287778, 37d))
                        .build(),
                CreateAirportCommand
                        .builder()
                        .id(UUID.randomUUID())
                        .iataCode("FRA")
                        .name("Frankfurt Airport")
                        .fullName("Frankfurt Airport")
                        .city("Frankfurt")
                        .location(new GlobalPosition(50.033333d, 8.570556d, 111d))
                        .build(),
                CreateAirportCommand
                        .builder()
                        .id(UUID.randomUUID())
                        .iataCode("MUC")
                        .name("München Airport")
                        .fullName("München Airport")
                        .city("München")
                        .location(new GlobalPosition(48.353889d, 11.786111d, 453d))
                        .build(),
                CreateAirportCommand
                        .builder()
                        .id(UUID.randomUUID())
                        .iataCode("CDG")
                        .name("Charles de Gaulle Airport")
                        .fullName("Charles de Gaulle Airport")
                        .city("Paris")
                        .location(new GlobalPosition(49.009722d, 2.547778d, 119d))
                        .build(),
                CreateAirportCommand
                        .builder()
                        .id(UUID.randomUUID())
                        .iataCode("LHR")
                        .name("London Heathrow Airport")
                        .fullName("London Heathrow Airport")
                        .city("London")
                        .location(new GlobalPosition(51.4775d, -0.461389d, 25d))
                        .build(),
                CreateAirportCommand
                        .builder()
                        .id(UUID.randomUUID())
                        .iataCode("AMS")
                        .name("Schipol Airport")
                        .fullName("Schipol Airport")
                        .city("Amsterdam")
                        .location(new GlobalPosition(52.308056d, 4.764167d, -3d))
                        .build(),
                CreateAirportCommand
                        .builder()
                        .id(UUID.randomUUID())
                        .iataCode("DUS")
                        .name("Düsseldorf Airport")
                        .fullName("Düsseldorf Airport")
                        .city("Düsseldorf")
                        .location(new GlobalPosition(51.289444d, 6.766667d, 119d))
                        .build());

        for (CreateAirportCommand command : commands) {
            if (activeAirports.contains(command.getIataCode()) && airportRepository.findByIataCode(command.getIataCode()) == null) {
                commandGateway.send(command);
            }
        }
    }

    @GetMapping("/aircraftypes")
    public void createAircraftTypes() {
        List<CreateAircraftTypeCommand> commands = Arrays.asList(CreateAircraftTypeCommand.builder().id(UUID.randomUUID()).code("A388").name("Airbus A380").seats(100).build());

        for (CreateAircraftTypeCommand command : commands) {
            if (aircraftTypeRepository.findByCode(command.getCode()) == null) {
                commandGateway.send(command);
            }
        }
    }

    @GetMapping("/customers")
    public void createCustomers() {
        for (String firstname : ImmutableList.of("Abigail", "Alexandra", "Alison", "Amanda", "Amelia", "Amy", "Andrea", "Angela", "Anna", "Anne", "Audrey", "Ava", "Bella", "Bernadette", "Carol",
//                "Caroline", "Carolyn", "Chloe", "Claire", "Deirdre", "Diana", "Diane", "Donna", "Dorothy", "Elizabeth", "Ella", "Emily", "Emma", "Faith", "Felicity", "Fiona", "Gabrielle", "Grace",
//                "Hannah", "Heather", "Irene", "Jan", "Jane", "Jasmine", "Jennifer", "Jessica", "Joan", "Joanne", "Julia", "Karen", "Katherine", "Kimberly", "Kylie", "Lauren", "Leah", "Lillian",
//                "Lily", "Lisa", "Madeleine", "Maria", "Mary", "Megan", "Melanie", "Michelle", "Molly", "Natalie", "Nicola", "Olivia", "Penelope", "Pippa", "Rachel", "Rebecca", "Rose", "Ruth", "Sally",
//                "Samantha", "Sarah", "Sonia", "Sophie", "Stephanie", "Sue", "Theresa", "Tracey", "Una", "Vanessa", "Victoria", "Virginia", "Wanda", "Wendy", "Yvonne", "Zoe", "Adam", "Adrian", "Alan",
//                "Alexander", "Andrew", "Anthony", "Austin", "Benjamin", "Blake", "Boris", "Brandon", "Brian", "Cameron", "Carl", "Charles", "Christian", "Christopher", "Colin", "Connor", "Dan",
//                "David", "Dominic", "Dylan", "Edward", "Eric", "Evan", "Frank", "Gavin", "Gordon", "Harry", "Ian", "Isaac", "Jack", "Jacob", "Jake", "James", "Jason", "Joe", "John", "Jonathan",
//                "Joseph", "Joshua", "Julian", "Justin", "Keith", "Kevin", "Leonard", "Liam", "Lucas", "Luke", "Matt", "Max", "Michael", "Nathan", "Neil", "Nicholas", "Oliver", "Owen", "Paul", "Peter",
                "Phil", "Piers", "Richard", "Robert", "Ryan", "Sam", "Sean", "Sebastian", "Simon", "Stephen", "Steven", "Stewart", "Thomas", "Tim", "Trevor", "Victor", "Warren", "William")) {
            for (String lastname : ImmutableList.of("Abraham", "Allan", "Alsop", "Anderson", "Arnold", "Avery", "Bailey", "Baker", "Ball", "Bell", "Berry", "Black", "Blake", "Bond", "Bower", "Brown",
//                    "Buckland", "Burgess", "Butler", "Cameron", "Campbell", "Carr", "Chapman", "Churchill", "Clark", "Clarkson", "Coleman", "Cornish", "Davidson", "Davies", "Dickens", "Dowd",
//                    "Duncan", "Dyer", "Edmunds", "Ellison", "Ferguson", "Fisher", "Forsyth", "Fraser", "Gibson", "Gill", "Glover", "Graham", "Grant", "Gray", "Greene", "Hamilton", "Hardacre",
//                    "Harris", "Hart", "Hemmings", "Henderson", "Hill", "Hodges", "Howard", "Hudson", "Hughes", "Hunter", "Ince", "Jackson", "James", "Johnston", "Jones", "Kelly", "Kerr", "King",
//                    "Knox", "Lambert", "Langdon", "Lawrence", "Lee", "Lewis", "Lyman", "MacDonald", "Mackay", "Mackenzie", "MacLeod", "Manning", "Marshall", "Martin", "Mathis", "May", "McDonald",
//                    "McLean", "McGrath", "Metcalfe", "Miller", "Mills", "Mitchell", "Morgan", "Morrison", "Murray", "Nash", "Newman", "Nolan", "North", "Ogden", "Oliver", "Paige", "Parr", "Parsons",
//                    "Paterson", "Payne", "Peake", "Peters", "Piper", "Poole", "Powell", "Pullman", "Quinn", "Rampling", "Randall", "Rees", "Reid", "Roberts", "Robertson", "Ross", "Russell",
//                    "Rutherford", "Sanderson", "Scott", "Sharp", "Short", "Simpson", "Skinner", "Slater", "Smith", "Springer", "Stewart", "Sutherland", "Taylor", "Terry", "Thomson", "Tucker",
//                    "Turner", "Underwood", "Vance", "Vaughan", "Walker", "Wallace", "Walsh", "Watson", "Welch", "White", "Wilkins", "Wilson", "Wright", 
            		"Young")) {
                if (customerRepository.findByNameAndLastName(firstname, lastname).isPresent()) {
                    continue;
                }

                commandGateway
                        .send(CreateCustomerCommand
                                .builder()
                                .id(UUID.randomUUID())
                                .name(firstname)
                                .lastname(lastname)
                                .emailAddress(firstname.toLowerCase() + "." + lastname.toLowerCase() + "@gmail.com")
                                .build());
            }
        }
    }

}
