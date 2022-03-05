package org.airsim.bookingservice.projection;

import org.airsim.api.aircrafttype.AircraftTypeCreated;
import org.airsim.bookingservice.projection.jpa.AircraftTypeEntity;
import org.airsim.bookingservice.projection.jpa.AircraftTypeRepository;
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
public class AircraftProjectionBuilder {
	
	private final AircraftTypeRepository aircraftTypeRepository;
	
	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(AircraftTypeCreated event) {
		aircraftTypeRepository.save(AircraftTypeEntity.builder()
				.id(event.getId())
				.code(event.getCode())
				.name(event.getName())
				.build());
	}
	
	@ResetHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void reset() {
		log.info("-- resetted aircraftType projection --");
		aircraftTypeRepository.deleteAll();
	}

}
