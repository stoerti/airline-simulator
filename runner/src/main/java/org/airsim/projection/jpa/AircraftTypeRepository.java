package org.airsim.projection.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface AircraftTypeRepository extends PagingAndSortingRepository<AircraftTypeEntity, UUID> {
	
	AircraftTypeEntity findByCode(String code);

}
