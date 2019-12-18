package org.airsim.agents;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.airsim.api.aircrafttype.AircraftTypeCreated;
import org.airsim.api.aircrafttype.CreateAircraftTypeCommand;
import org.airsim.api.airport.CreateAirportCommand;
import org.airsim.api.customer.CreateCustomerCommand;
import org.airsim.api.flightplan.CreateFlightplanCommand;
import org.airsim.api.flightplan.Weekplan;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitialDataAgent implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private CommandGateway commandGateway;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		createAirports();
		createAircrafts();
		createFlights();
		createCustomers();
	}

	private void createFlights() {
		Integer flightnumberCounter = 1;

		for (String from : ImmutableList.of("HAM" , "FRA", "MUC", "TXL", "CDG" )) {
			int j = 0;
			for (String to : ImmutableList.of( "HAM", "FRA", "MUC", "TXL", "CDG")) {

				if (from.equals(to))
					continue;

				for (int i = 0; i < 1; i++) {

					CreateFlightplanCommand flightplanCommand = CreateFlightplanCommand
						.builder()
						.id(UUID.randomUUID())
						.flightnumber("AS" + Strings.padStart(flightnumberCounter.toString(), 3, '0'))
						.airportFrom(from)
						.airportTo(to)
						.aircraftType("A388")
						.takeoffTime(LocalTime.now().plusMinutes(1))
						.duration(Duration.ofHours(1))
						.validFrom(LocalDate.now())
						.validTo(LocalDate.now().plusWeeks(1))
						.weekplan(Weekplan.daily())
						.build();
					flightnumberCounter++;
					j++;
					commandGateway.send(flightplanCommand);
				}
			}
		}

	}

	private void createAirports() {
		commandGateway
			.send(CreateAirportCommand
				.builder()
				.id(UUID.randomUUID())
				.iataCode("HAM")
				.name("Hamburg Airport")
				.fullName("Hamburg Airport")
				.city("Hamburg")
				.location(new GlobalPosition(53.630278d, 9.991111d, 0d))
				.build());

		commandGateway
			.send(CreateAirportCommand
				.builder()
				.id(UUID.randomUUID())
				.iataCode("TXL")
				.name("Berlin Tegel")
				.fullName("Berlin Tegel")
				.city("Berlin")
				.location(new GlobalPosition(52.559722d, 13.287778, 37d))
				.build());
		commandGateway
			.send(CreateAirportCommand
				.builder()
				.id(UUID.randomUUID())
				.iataCode("FRA")
				.name("Frankfurt Airport")
				.fullName("Frankfurt Airport")
				.city("Frankfurt")
				.location(new GlobalPosition(50.033333d, 8.570556d, 111d))
				.build());
		commandGateway
			.send(CreateAirportCommand
				.builder()
				.id(UUID.randomUUID())
				.iataCode("MUC")
				.name("München Airport")
				.fullName("München Airport")
				.city("München")
				.location(new GlobalPosition(48.353889d, 11.786111d, 453d))
				.build());
		commandGateway
			.send(CreateAirportCommand
				.builder()
				.id(UUID.randomUUID())
				.iataCode("CDG")
				.name("Charles de Gaulle Airport")
				.fullName("Charles de Gaulle Airport")
				.city("Paris")
				.location(new GlobalPosition(49.009722d, 2.547778d, 119d))
				.build());
	}

	private void createAircrafts() {
		UUID a380id = UUID.randomUUID();

		commandGateway
			.send(CreateAircraftTypeCommand.builder().id(a380id).code("A388").name("Airbus A380").seats(100).build());
	}

	private void createCustomers() {
		for (String firstname : ImmutableList.of("Max", "Thomas", "Hans", "Paul", "Anke", "Anja", "Laura", "Sophie")) {
			for (String lastname : ImmutableList.of("Meier", "Mueller", "Schmidt", "Hummels", "Scholz", "Haupt")) {
				commandGateway
					.send(CreateCustomerCommand
						.builder()
						.id(UUID.randomUUID())
						.name(firstname)
						.lastname(lastname)
						.emailAddress(firstname + "." + lastname + "@gmail.com")
						.build());
			}
		}
	}
}