package org.airsim.projection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.airsim.api.aircrafttype.AircraftTypeCreated;
import org.airsim.api.airport.AirportCreated;
import org.airsim.api.customer.CustomerCreated;
import org.airsim.projection.jpa.AircraftTypeEntity;
import org.airsim.projection.jpa.AircraftTypeRepository;
import org.airsim.projection.jpa.AirportEntity;
import org.airsim.projection.jpa.AirportRepository;
import org.airsim.projection.jpa.CustomerEntity;
import org.airsim.projection.jpa.CustomerRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectionBuilder {

	private final AirportRepository airportRepository;
	private final AircraftTypeRepository aircraftTypeRepository;
	private final CustomerRepository customerRepository;

	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(AirportCreated event) {
		airportRepository.save(AirportEntity.builder()
				.id(event.getId())
				.iataCode(event.getIataCode())
				.build());
	}

	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(AircraftTypeCreated event) {
		aircraftTypeRepository.save(AircraftTypeEntity.builder()
				.id(event.getId())
				.code(event.getCode())
				.build());
	}

	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(CustomerCreated event) {
		customerRepository.save(CustomerEntity.builder()
				.id(event.getId())
				.name(event.getName())
				.lastName(event.getLastname())
				.build());
	}


	@ResetHandler
	public void reset() {
		log.info("-- resetted projection --");
		airportRepository.deleteAll();
		aircraftTypeRepository.deleteAll();
		customerRepository.deleteAll();
	}
}
