package org.airsim.flighttracker.projection;

import org.airsim.api.aircraft.AircraftCreated;
import org.airsim.api.aircrafttype.AircraftTypeCreated;
import org.airsim.flighttracker.projection.jpa.AircraftEntity;
import org.airsim.flighttracker.projection.jpa.AircraftRepository;
import org.airsim.flighttracker.projection.jpa.AircraftTypeEntity;
import org.airsim.flighttracker.projection.jpa.AircraftTypeRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component("bookingAircraftProjectionBuilder")
@RequiredArgsConstructor
@Slf4j
public class AircraftProjectionBuilder {
	
	private final AircraftTypeRepository aircraftTypeRepository;
	private final AircraftRepository aircraftRepository;
	
	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(AircraftTypeCreated event) {
		aircraftTypeRepository.save(AircraftTypeEntity.builder()
				.id(event.getId())
				.code(event.getCode())
				.name(event.getName())
				.build());
	}
	
	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(AircraftCreated event) {
		AircraftTypeEntity type = aircraftTypeRepository.findById(event.getType()).orElseThrow(() ->new IllegalStateException());
		
		aircraftRepository.save(AircraftEntity.builder()
				.id(event.getId())
				.code(event.getCode())
				.type(type)
				.build());
	}
	
	@ResetHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void reset() {
		log.info("-- resetted aircraft projection --");
		aircraftRepository.deleteAll();
		aircraftTypeRepository.deleteAll();
	}
}
