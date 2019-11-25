package org.airsim.domain.flightplan;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.airsim.api.flightplan.CreateFlightplanCommand;
import org.airsim.api.flightplan.FlightplanCreated;
import org.airsim.api.flightplan.Weekplan;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class FlightplanAggregate {
	
	@AggregateIdentifier
	private UUID id;

	@CommandHandler
	public FlightplanAggregate(CreateFlightplanCommand command) {
		log.debug("Adding flight {} - {}->{} at {}", command.getFlightnumber(), command.getAirportFrom(), command.getAirportTo(), command.getTakeoffTime());
		apply(FlightplanCreated
				.builder()
				.id(command.getId())
				.flightnumber(command.getFlightnumber())
				.aircraftType(command.getAircraftType())
				.airportFrom(command.getAirportFrom())
				.airportTo(command.getAirportTo())
				.takeoffTime(command.getTakeoffTime())
				.duration(command.getDuration())
				.validFrom(command.getValidFrom())
				.validTo(command.getValidTo())
				.weekplan(command.getWeekplan())
				.build());
	}

	@EventSourcingHandler
	public void on(FlightplanCreated event) {
		this.id = event.getId();
	}	
	
}
