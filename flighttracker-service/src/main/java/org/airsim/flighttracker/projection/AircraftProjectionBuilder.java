package org.airsim.flighttracker.projection;

import javax.transaction.Transactional;

import org.airsim.api.aircraft.AircraftCreated;
import org.airsim.api.aircrafttype.AircraftTypeCreated;
import org.airsim.flighttracker.projection.jpa.AircraftEntity;
import org.airsim.flighttracker.projection.jpa.AircraftRepository;
import org.airsim.flighttracker.projection.jpa.AircraftTypeEntity;
import org.airsim.flighttracker.projection.jpa.AircraftTypeRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component("bookingAircraftProjectionBuilder")
@RequiredArgsConstructor
public class AircraftProjectionBuilder {
	
	private final AircraftTypeRepository aircraftTypeRepository;
	private final AircraftRepository aircraftRepository;
	
	@EventHandler
	@Transactional
	public void on(AircraftTypeCreated event) {
		aircraftTypeRepository.save(AircraftTypeEntity.builder()
				.id(event.getId())
				.code(event.getCode())
				.name(event.getName())
				.build());
	}
	
	@EventHandler
	@Transactional
	public void on(AircraftCreated event) {
		AircraftTypeEntity type = aircraftTypeRepository.findById(event.getType()).orElseThrow(() ->new IllegalStateException());
		
		aircraftRepository.save(AircraftEntity.builder()
				.id(event.getId())
				.code(event.getCode())
				.type(type)
				.build());
	}

}
