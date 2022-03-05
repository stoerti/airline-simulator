package org.airsim.bookingservice.projection.jpa;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface AircraftTypeRepository extends PagingAndSortingRepository<AircraftTypeEntity, UUID> {

	AircraftTypeEntity findByCode(String aircraftTypeCode);

}
