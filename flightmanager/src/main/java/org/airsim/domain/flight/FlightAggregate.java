package org.airsim.domain.flight;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;

import org.airsim.api.flight.FlightStatus;
import org.airsim.api.flight.command.CompleteBoarding;
import org.airsim.api.flight.command.CompleteCheckIn;
import org.airsim.api.flight.command.CompleteFlight;
import org.airsim.api.flight.command.CreateFlightCommand;
import org.airsim.api.flight.command.StartBoarding;
import org.airsim.api.flight.command.StartCheckIn;
import org.airsim.api.flight.command.StartFlight;
import org.airsim.api.flight.event.BoardingCompleted;
import org.airsim.api.flight.event.BoardingStarted;
import org.airsim.api.flight.event.CheckInCompleted;
import org.airsim.api.flight.event.CheckInStarted;
import org.airsim.api.flight.event.FlightCompleted;
import org.airsim.api.flight.event.FlightCreated;
import org.airsim.api.flight.event.FlightStarted;
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
	private FlightStatus flightStatus;
	
	public FlightAggregate() {

	}

	@CommandHandler
	public FlightAggregate(CreateFlightCommand command) {
		log.debug("Adding flight {} for flight plan {} at {}", command.getId(), command.getFlightplanId(), command.getTakeoffTime());

		apply(FlightCreated
			.builder()
			.id(command.getId())
			.flightplanId(command.getFlightplanId())
			.takeoffTime(command.getTakeoffTime())
			.duration(command.getDuration())
			.flightStatus(FlightStatus.PLANNED)
			.build());
	}
	
	@CommandHandler
	public void on(StartCheckIn command) {
		log.info("Start checkin for flight " + id);
		apply(new CheckInStarted(id));
	}
	
	@CommandHandler
	public void on(CompleteCheckIn command) {
		log.info("Complete checkin for flight " + id);
		apply(new CheckInCompleted(id));
	}
	
	@CommandHandler
	public void on(StartBoarding command) {
		log.info("Start boarding for flight " + id);
		apply(new BoardingStarted(id));
	}
	
	@CommandHandler
	public void on(CompleteBoarding command) {
		log.info("Complete boarding for flight " + id);
		apply(new BoardingCompleted(id));
	}
	
	@CommandHandler
	public void on(StartFlight command) {
		log.info("Start flight for flight " + id);
		apply(new FlightStarted(id));
	}
	
	@CommandHandler
	public void on(CompleteFlight command) {
		log.info("Complete flight for flight " + id);
		apply(new FlightCompleted(id));
	}

	@EventSourcingHandler
	public void on(FlightCreated event) {
		this.id = event.getId();
		this.flightplanId = event.getFlightplanId();
		this.flightStatus = event.getFlightStatus();
	}
	
	/*
	 *  Event sourcing
	 */

	@EventSourcingHandler
	public void on(CheckInStarted event) {
		this.flightStatus = FlightStatus.CHECKIN_OPEN;
	}

	@EventSourcingHandler
	public void on(CheckInCompleted event) {
		this.flightStatus = FlightStatus.CHECKIN_CLOSED;
	}

	@EventSourcingHandler
	public void on(BoardingStarted event) {
		this.flightStatus = FlightStatus.BOARDING;
	}

	@EventSourcingHandler
	public void on(BoardingCompleted event) {
		this.flightStatus = FlightStatus.BOARDING_COMPLETED;
	}

	@EventSourcingHandler
	public void on(FlightStarted event) {
		this.flightStatus = FlightStatus.IN_AIR;
	}

	@EventSourcingHandler
	public void on(FlightCompleted event) {
		this.flightStatus = FlightStatus.LANDED;
	}

}
