package org.airsim.flighttracker.projection.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AircraftTypeRepository extends PagingAndSortingRepository<AircraftTypeEntity, UUID> {

	AircraftTypeEntity findByCode(String aircraftTypeCode);

}
