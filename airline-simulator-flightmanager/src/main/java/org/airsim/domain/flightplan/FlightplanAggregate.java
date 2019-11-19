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
	
	private String flightnumber;
	private String airportFrom;
	private String airportTo;
	private String aircraftType;
	private LocalTime takeoffTime;
	private LocalTime landingTime;
	private LocalDate validFrom;
	private LocalDate validTo;
	private Weekplan weekplan;

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
				.landingTime(command.getLandingTime())
				.validFrom(command.getValidFrom())
				.validTo(command.getValidTo())
				.weekplan(command.getWeekplan())
				.build());
	}

	@EventSourcingHandler
	public void on(FlightplanCreated event) {
		this.id = event.getId();
		this.flightnumber = event.getFlightnumber();
		this.aircraftType = event.getAircraftType();
		this.airportFrom = event.getAirportFrom();
		this.airportTo = event.getAirportTo();
		this.takeoffTime = event.getTakeoffTime();
		this.landingTime = event.getLandingTime();
		this.validFrom = event.getValidFrom();
		this.validTo = event.getValidTo();
		this.weekplan = event.getWeekplan();
	}	
	
}
