package org.airsim.agents;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.airsim.api.aircraft.AircraftTypeCreated;
import org.airsim.api.aircraft.CreateAircraftTypeCommand;
import org.airsim.api.flightplan.CreateFlightplanCommand;
import org.airsim.api.flightplan.Weekplan;
import org.axonframework.commandhandling.gateway.CommandGateway;
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

	public InitialDataAgent() {
		log.info("=== InitialDataAgent ===");
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Integer flightnumberCounter = 1;

		log.info("==== Initializing with some test data ====");
		
		commandGateway.send(CreateAircraftTypeCommand.builder()
				.id(UUID.randomUUID())
				.code("A388")
				.name("Airbus A380")
				.build());
		

//		for (String from : ImmutableList.of("HAM", "FRA", "MUC", "TXL", "CDG")) {
//			int j = 0;
//			for (String to : ImmutableList.of("HAM", "FRA", "MUC", "TXL", "CDG")) {
//
//				if (from.equals(to))
//					continue;
//
//				for (int i = 1; i < 10; i++) {
//
//					CreateFlightplanCommand flightplanCommand = CreateFlightplanCommand
//						.builder()
//						.id(UUID.randomUUID())
//						.flightnumber("AS" + Strings.padStart(flightnumberCounter.toString(), 3, '0'))
//						.airportFrom(from)
//						.airportTo(to)
//						.aircraftType("A388")
//						.takeoffTime(LocalTime.now().plusMinutes(j + i * 10).plusMinutes(1))
//						.duration(Duration.ofMinutes(5))
//						.validFrom(LocalDate.now())
//						.validTo(LocalDate.now().plusDays(1))
//						.weekplan(Weekplan.daily())
//						.build();
//					flightnumberCounter++;
//					j++;
//					commandGateway.send(flightplanCommand);
//				}
//			}
//		}
	}
}