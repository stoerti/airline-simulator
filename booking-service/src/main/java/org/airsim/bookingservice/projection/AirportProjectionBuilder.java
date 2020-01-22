package org.airsim.bookingservice.projection;


import org.airsim.api.airport.AirportCreated;
import org.airsim.bookingservice.projection.jpa.AirportEntity;
import org.airsim.bookingservice.projection.jpa.AirportRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AirportProjectionBuilder {
	
	private final AirportRepository airportRepository;
	
	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(AirportCreated event) {
		airportRepository.save(AirportEntity.builder()
				.id(event.getId())
				.iataCode(event.getIataCode())
				.name(event.getName())
				.city(event.getCity())
				.location(event.getLocation())
				.build());
	}
	
	@ResetHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void reset() {
		log.info("-- resetted airport projection --");
		airportRepository.deleteAll();
	}
}
