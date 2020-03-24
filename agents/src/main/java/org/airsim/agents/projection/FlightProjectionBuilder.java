package org.airsim.agents.projection;

import lombok.extern.slf4j.Slf4j;
import org.airsim.agents.projection.jpa.FlightEntity;
import org.airsim.agents.projection.jpa.FlightRepository;
import org.airsim.agents.projection.jpa.FlightplanEntity;
import org.airsim.agents.projection.jpa.FlightplanRepository;
import org.airsim.api.flight.event.CheckInStarted;
import org.airsim.api.flight.event.FlightCreated;
import org.airsim.api.flight.event.SeatsAllocated;
import org.airsim.api.flightplan.FlightplanCreated;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
public class FlightProjectionBuilder {

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private FlightplanRepository flightplanRepository;

	@EventHandler
    @Order(1)
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
    @Order(1)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(SeatsAllocated event) {
		flightRepository.findById(event.getFlightId()).ifPresent(flight -> {
			log.debug("Increasing seat allocations of flight " + flight.getFlightNumber() + " by " + event.getNumberOfSeats());
			flight.setSeatsTaken(flight.getSeatsTaken() + event.getNumberOfSeats());
			flightRepository.save(flight);
		});
	}
	
	@EventHandler
    @Order(1)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(CheckInStarted event) {
		flightRepository.findById(event.getFlightId()).ifPresent(flight -> {
			log.debug("Changing status of flight " + flight.getFlightNumber() + " to notBookable");
			flight.setBookable(false);
			flightRepository.save(flight);
		});
	}
	
	@EventHandler
    @Order(1)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void onNewFlightplan(FlightplanCreated event) {
		flightplanRepository
			.save(FlightplanEntity
				.builder()
				.id(event.getId())
				.airportFrom(event.getAirportFrom())
				.airportTo(event.getAirportTo())
				.flightnumber(event.getFlightnumber())
				.takeoffTime(event.getTakeoffTime())
				.duration(event.getDuration())
				.build());

	}
	
	@ResetHandler
    @Order(1)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void reset() {
		log.info("-- resetted flight projection --");
		flightplanRepository.deleteAll();
		flightRepository.deleteAll();
	}

}
