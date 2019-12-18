package org.airsim.domain.flight;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.airsim.api.flight.FlightStatus;
import org.airsim.api.flight.command.AllocateSeatsCommand;
import org.airsim.api.flight.command.CompleteBoarding;
import org.airsim.api.flight.command.CompleteCheckIn;
import org.airsim.api.flight.command.CompleteFlight;
import org.airsim.api.flight.command.CreateFlightCommand;
import org.airsim.api.flight.command.MoveFlyingAircraftCommand;
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
import org.airsim.api.flight.event.FlyingAircraftMoved;
import org.airsim.api.flight.event.SeatsAllocated;
import org.airsim.api.flight.exception.FlightSoldOutException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class FlightAggregate {

	@AggregateIdentifier
	private UUID flightId;

	private UUID flightplanId;
	private FlightStatus flightStatus;
	
	private int seatsAvailable;
	private Map<UUID, SeatAllocation> seatAllocations = new HashMap<>();
	
	
	public FlightAggregate() {

	}

	@CommandHandler
	public FlightAggregate(CreateFlightCommand command) {
		log.info("Adding flight {} for flight plan {} at {}", command.getId(), command.getFlightplanId(), command.getTakeoffTime());

		apply(FlightCreated
			.builder()
			.id(command.getId())
			.flightplanId(command.getFlightplanId())
			.takeoffTime(command.getTakeoffTime())
			.duration(command.getDuration())
			.flightStatus(FlightStatus.PLANNED)
			.seatsAvailable(command.getSeatsAvailable())
			.build());
	}
	
	@CommandHandler
	public void on(StartCheckIn command) {
		log.info("Start checkin for flight " + flightId);
		apply(new CheckInStarted(flightId));
	}
	
	@CommandHandler
	public void on(CompleteCheckIn command) {
		log.info("Complete checkin for flight " + flightId);
		apply(new CheckInCompleted(flightId));
	}
	
	@CommandHandler
	public void on(StartBoarding command) {
		log.info("Start boarding for flight " + flightId);
		apply(new BoardingStarted(flightId));
	}
	
	@CommandHandler
	public void on(CompleteBoarding command) {
		log.info("Complete boarding for flight " + flightId);
		apply(new BoardingCompleted(flightId));
	}
	
	@CommandHandler
	public void on(StartFlight command) {
		log.info("Start flight for flight " + flightId);
		apply(new FlightStarted(flightId));
	}
	
	@CommandHandler
	public void on(CompleteFlight command) {
		log.info("Complete flight for flight " + flightId);
		apply(new FlightCompleted(flightId));
	}
	
	@CommandHandler
	public void on(MoveFlyingAircraftCommand command) {
		log.info("Move flight " + flightId + " to (" + command.getLatitude() + ", " + command.getLongitude() + ")");
		apply(new FlyingAircraftMoved(flightId, command.getLatitude(), command.getLongitude()));
	}	

	@EventSourcingHandler
	public void on(FlightCreated event) {
		this.flightId = event.getId();
		this.flightplanId = event.getFlightplanId();
		this.flightStatus = event.getFlightStatus();
		this.seatsAvailable = event.getSeatsAvailable();
	}
	
	@CommandHandler
	public void on(AllocateSeatsCommand cmd) throws FlightSoldOutException {
		if (getNumberOfAvailableSeats() < cmd.getNumberOfSeats()) {
			throw new FlightSoldOutException();
		}
		log.info("Allocating {} seats for flight ", cmd.getNumberOfSeats(), flightId);

		apply(new SeatsAllocated(flightId, cmd.getSeatAllocationId(), cmd.getNumberOfSeats()));

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

	@EventSourcingHandler
	public void on(SeatsAllocated event) {
		this.seatAllocations
			.put(event.getSeatAllocationId(),
					new SeatAllocation(flightId, event.getSeatAllocationId(), event.getNumberOfSeats()));
	}

	
	
	private int getNumberOfAvailableSeats() {
		return seatsAvailable - seatAllocations
			.values()
			.stream()
			.map(SeatAllocation::getNumberOfSeats)
			.reduce(Integer::sum)
			.orElseGet(() -> 0);
	}

}
