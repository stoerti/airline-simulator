package de.airsim.projection.aircraft;

import javax.transaction.Transactional;

import org.airsim.api.aircraft.AircraftTypeCreated;
import org.airsim.api.aircrafttype.AircraftCreated;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import de.airsim.projection.aircraft.jpa.AircraftEntity;
import de.airsim.projection.aircraft.jpa.AircraftRepository;
import de.airsim.projection.aircraft.jpa.AircraftTypeEntity;
import de.airsim.projection.aircraft.jpa.AircraftTypeRepository;
import lombok.RequiredArgsConstructor;

@Component
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
