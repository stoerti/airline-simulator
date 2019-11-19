package org.airsim.domain.flight;

import java.time.LocalDate;
import java.util.UUID;

import org.airsim.api.flight.CreateFlightCommand;
import org.airsim.api.flightplan.FlightplanCreated;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FlightManager {

	@Autowired
	private CommandGateway commandGateway;

	@EventHandler
	public void onFlightPlanCreated(FlightplanCreated event) {
		for (LocalDate currentDate = event.getValidFrom(); currentDate
			.isBefore(event.getValidTo().plusDays(1)); currentDate = currentDate.plusDays(1)) {
			CreateFlightCommand command = CreateFlightCommand
				.builder()
				.flightplanId(event.getId())
				.date(currentDate)
				.id(UUID.randomUUID())
				.build();
			
			commandGateway.send(command);
		}

	}

}
