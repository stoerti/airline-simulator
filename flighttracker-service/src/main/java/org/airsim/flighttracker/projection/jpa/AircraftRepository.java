package org.airsim.flighttracker.projection.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface AircraftRepository extends CrudRepository<AircraftEntity, UUID> {

}
