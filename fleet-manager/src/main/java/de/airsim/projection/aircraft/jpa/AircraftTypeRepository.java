package de.airsim.projection.aircraft.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface AircraftTypeRepository extends CrudRepository<AircraftTypeEntity, UUID> {

}
