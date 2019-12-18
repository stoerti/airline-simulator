package org.airsim.agents.projection.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface AirportRepository extends PagingAndSortingRepository<AirportEntity, UUID> {
	
	AirportEntity findByIataCode(String iataCode);

}
