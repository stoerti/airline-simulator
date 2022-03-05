package org.airsim.bookingservice.projection.jpa;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface AirportRepository extends PagingAndSortingRepository<AirportEntity, UUID> {
	
	AirportEntity findByIataCode(String iataCode);

}
