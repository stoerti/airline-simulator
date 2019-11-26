package org.airsim.flighttracker.projection.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface AirportRepository extends CrudRepository<AirportEntity, UUID> {
	
	AirportEntity findByIataCode(String iataCode);

}
