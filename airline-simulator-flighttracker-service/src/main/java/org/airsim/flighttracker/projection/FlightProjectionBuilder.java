package org.airsim.flighttracker.projection;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.airsim.api.flight.FlightCreated;
import org.airsim.api.flight.FlightStatus;
import org.airsim.api.flightplan.FlightplanCreated;
import org.airsim.flighttracker.projection.jpa.FlightEntity;
import org.airsim.flighttracker.projection.jpa.FlightRepository;
import org.airsim.flighttracker.projection.jpa.FlightplanEntity;
import org.airsim.flighttracker.projection.jpa.FlightplanRepository;
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
					.takeoffTime(LocalDateTime.of(event.getDate(), flightplan.getTakeoffTime()))
					.landingTime(LocalDateTime.of(event.getDate(), flightplan.getLandingTime()))
					.flightStatus(FlightStatus.PLANNED)
					.build());
		} else {
			log.error("Flightplan " + event.getFlightplanId() + " not found");
		}

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
				.landingTime(event.getLandingTime())
				.build());

	}

}
