package org.airsim.flighttracker.projection;

import java.util.Optional;
import java.util.UUID;

import org.airsim.api.flight.FlightStatus;
import org.airsim.api.flight.event.BoardingCompleted;
import org.airsim.api.flight.event.BoardingStarted;
import org.airsim.api.flight.event.CheckInCompleted;
import org.airsim.api.flight.event.CheckInStarted;
import org.airsim.api.flight.event.FlightCompleted;
import org.airsim.api.flight.event.FlightCreated;
import org.airsim.api.flight.event.FlightStarted;
import org.airsim.api.flight.event.FlyingAircraftMoved;
import org.airsim.api.flight.event.SeatsAllocated;
import org.airsim.api.flightplan.FlightplanCreated;
import org.airsim.flighttracker.projection.jpa.FlightEntity;
import org.airsim.flighttracker.projection.jpa.FlightRepository;
import org.airsim.flighttracker.projection.jpa.FlightplanEntity;
import org.airsim.flighttracker.projection.jpa.FlightplanRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Component("bookingFlightProjectionBuilder")
@Slf4j
public class FlightProjectionBuilder {

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private FlightplanRepository flightplanRepository;

	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void onNewFlight(FlightCreated event) {
		Optional<FlightplanEntity> optionalFlightplan = flightplanRepository.findById(event.getFlightplanId());

		if (optionalFlightplan.isPresent()) {
			FlightplanEntity flightplan = optionalFlightplan.get();
			
			flightRepository.save(FlightEntity.builder()
					.id(event.getId())
					.flightNumber(flightplan.getFlightnumber())
					.airportFrom(flightplan.getAirportFrom())
					.airportTo(flightplan.getAirportTo())
					.aircraftTypeCode(flightplan.getAircraftType())
					.takeoffTime(event.getTakeoffTime())
					.duration(event.getDuration())
					.flightStatus(FlightStatus.PLANNED)
					.seatsAvailable(event.getSeatsAvailable())
					.seatsTaken(0)
					.build());
		} else {
			log.error("Flightplan " + event.getFlightplanId() + " not found");
		}
	}
	
	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(SeatsAllocated event) {
		flightRepository.findById(event.getFlightId()).ifPresent(flight -> {
			log.debug("Increasing seat allocations of flight " + flight.getFlightNumber() + " by " + event.getNumberOfSeats());
			flight.setSeatsTaken(flight.getSeatsTaken() + event.getNumberOfSeats());
			flightRepository.save(flight);
		});
	}
	
	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(CheckInStarted event) {
		changeFlightStatus(event.getFlightId(), FlightStatus.CHECKIN_OPEN);
	}
	
	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(CheckInCompleted event) {
		changeFlightStatus(event.getFlightId(), FlightStatus.CHECKIN_CLOSED);
	}
	
	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(BoardingStarted event) {
		changeFlightStatus(event.getFlightId(), FlightStatus.BOARDING);
	}
	
	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(BoardingCompleted event) {
		changeFlightStatus(event.getFlightId(), FlightStatus.BOARDING_COMPLETED);
	}
	
	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(FlightStarted event) {
		changeFlightStatus(event.getFlightId(), FlightStatus.IN_AIR);
	}
	
	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(FlightCompleted event) {
		changeFlightStatus(event.getFlightId(), FlightStatus.LANDED);
	}
	
	private void changeFlightStatus(UUID flightId, FlightStatus targetStatus) {
		flightRepository.findById(flightId).ifPresent(flight -> {
			log.debug("Changing status of flight " + flight.getFlightNumber() + " to " + targetStatus);
			flight.setFlightStatus(targetStatus);
			flightRepository.save(flight);
		});
	}

	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void onNewFlightplan(FlightplanCreated event) {
		flightplanRepository
			.save(FlightplanEntity
				.builder()
				.id(event.getId())
				.aircraftType(event.getAircraftType())
				.airportFrom(event.getAirportFrom())
				.airportTo(event.getAirportTo())
				.flightnumber(event.getFlightnumber())
				.takeoffTime(event.getTakeoffTime())
				.duration(event.getDuration())
				.build());

	}

	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(FlyingAircraftMoved event) {
		flightRepository.findById(event.getFlightId()).ifPresent(flight -> {
			log.info("Move flight " + event.getFlightId() + " to (" + event.getLatitude() + ", " + event.getLongitude() + ")");
			flight.setPositionLatitude(event.getLatitude());
			flight.setPositionLongitude(event.getLongitude());
			flightRepository.save(flight);
		});


	}
	
	@ResetHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void reset() {
		log.info("-- resetted flight projection --");
		flightRepository.deleteAll();
		flightplanRepository.deleteAll();
	}

}
