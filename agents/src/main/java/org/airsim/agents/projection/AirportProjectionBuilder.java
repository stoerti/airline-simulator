package org.airsim.agents.projection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.airsim.agents.projection.jpa.AirportEntity;
import org.airsim.agents.projection.jpa.AirportRepository;
import org.airsim.api.airport.AirportCreated;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


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
