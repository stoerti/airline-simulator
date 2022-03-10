package org.airsim.controller;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/testdata")
@RequiredArgsConstructor
public class TestDataCreatorController {

    private final CommandGateway commandGateway;

    private final AircraftTypeRepository aircraftTypeRepository;
    private final AirportRepository airportRepository;
    private final CustomerRepository customerRepository;

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
        try (BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource("testdata/flights.tsv").getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] lineData = line.split("\\s+");
                String from = lineData[0];
                String to = lineData[1];
                Weekplan weekdays = Weekplan.fromString(lineData[2]);
                LocalTime timeStart = LocalTime.parse(lineData[3]);
                LocalTime timeEnd = LocalTime.parse(lineData[4]);
                String flightNo = lineData[5];
                String aircraftCode = lineData[6];

                CreateFlightplanCommand flightplanCommand = CreateFlightplanCommand
                        .builder()
                        .id(UUID.randomUUID())
                        .flightnumber(flightNo)
                        .airportFrom(from)
                        .airportTo(to)
                        .aircraftType("A388")
                        .takeoffTime(timeStart)
                        .duration(Duration.between(timeStart, timeEnd))
                        .validFrom(LocalDate.now())
                        .validTo(LocalDate.now().plusWeeks(3))
                        .weekplan(weekdays)
                        .build();
                commandGateway.send(flightplanCommand);
                // process the line.
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/airports")
    public void createAirports() {
        List<CreateAirportCommand> commands = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource("testdata/airports.csv").getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] lineData = line.split(",");
                String iataCode = lineData[0].trim();
                String airportName = lineData[1].trim();
                String cityName = lineData[2].trim();
                double posLatitude = Double.parseDouble(lineData[3].trim());
                double posLongitude = Double.parseDouble(lineData[4].trim());
                double elevation = Double.parseDouble(lineData[4].trim());

                commands.add(CreateAirportCommand
                        .builder()
                        .id(UUID.randomUUID())
                        .iataCode(iataCode)
                        .name(airportName)
                        .fullName(airportName)
                        .city(cityName)
                        .location(new GlobalPosition(posLatitude, posLongitude, elevation))
                        .build());
                // process the line.
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (CreateAirportCommand command : commands) {
            if (airportRepository.findByIataCode(command.getIataCode()) == null) {
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
