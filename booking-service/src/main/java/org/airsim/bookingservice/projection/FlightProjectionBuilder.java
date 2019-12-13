package org.airsim.bookingservice.projection;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.airsim.api.flight.FlightStatus;
import org.airsim.api.flight.event.BoardingCompleted;
import org.airsim.api.flight.event.BoardingStarted;
import org.airsim.api.flight.event.CheckInCompleted;
import org.airsim.api.flight.event.CheckInStarted;
import org.airsim.api.flight.event.FlightCompleted;
import org.airsim.api.flight.event.FlightCreated;
import org.airsim.api.flight.event.FlightStarted;
import org.airsim.api.flight.event.SeatsAllocated;
import org.airsim.api.flightplan.FlightplanCreated;
import org.airsim.bookingservice.projection.jpa.FlightEntity;
import org.airsim.bookingservice.projection.jpa.FlightRepository;
import org.airsim.bookingservice.projection.jpa.FlightplanEntity;
import org.airsim.bookingservice.projection.jpa.FlightplanRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FlightProjectionBuilder {

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private FlightplanRepository flightplanRepository;

	@EventHandler
	@Transactional
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
					.bookable(true)
					.seatsAvailable(event.getSeatsAvailable())
					.seatsTaken(0)
					.build());
		} else {
			log.error("Flightplan " + event.getFlightplanId() + " not found");
		}
	}
	
	@EventHandler
	@Transactional
	public void on(SeatsAllocated event) {
		flightRepository.findById(event.getFlightId()).ifPresent(flight -> {
			log.debug("Increasing seat allocations of flight " + flight.getFlightNumber() + " by " + event.getNumberOfSeats());
			flight.setSeatsTaken(flight.getSeatsTaken() + event.getNumberOfSeats());
			flightRepository.save(flight);
		});
	}
	
	@EventHandler
	@Transactional
	public void on(CheckInStarted event) {
		flightRepository.findById(event.getFlightId()).ifPresent(flight -> {
			log.debug("Changing status of flight " + flight.getFlightNumber() + " to notBookable");
			flight.setBookable(false);
			flightRepository.save(flight);
		});
	}
	
	@EventHandler
	@Transactional
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

}
