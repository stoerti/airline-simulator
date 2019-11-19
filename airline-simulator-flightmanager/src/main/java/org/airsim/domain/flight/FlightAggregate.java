package org.airsim.domain.flight;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.time.LocalDate;
import java.util.UUID;

import org.airsim.api.flight.CreateFlightCommand;
import org.airsim.api.flight.FlightCreated;
import org.airsim.api.flight.FlightStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class FlightAggregate {

	@AggregateIdentifier
	private UUID id;

	private UUID flightplanId;
	private LocalDate date;
	private FlightStatus flightStatus;

	@CommandHandler
	public FlightAggregate(CreateFlightCommand command) {
		log.debug("Adding flight {} for flight plan {} at {}", command.getId(), command.getFlightplanId(), command.getDate());

		apply(FlightCreated
			.builder()
			.id(command.getId())
			.flightplanId(command.getFlightplanId())
			.date(command.getDate())
			.flightStatus(FlightStatus.PLANNED)
			.build());
	}

	@EventSourcingHandler
	public void on(FlightCreated event) {
		this.id = event.getId();
		this.flightplanId = event.getFlightplanId();
		this.date = event.getDate();
		this.flightStatus = event.getFlightStatus();
	}

}
