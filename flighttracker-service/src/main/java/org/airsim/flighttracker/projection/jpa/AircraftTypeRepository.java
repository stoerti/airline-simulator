package org.airsim.flighttracker.projection.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface AircraftTypeRepository extends CrudRepository<AircraftTypeEntity, UUID> {

	AircraftTypeEntity findByCode(String aircraftTypeCode);

}
