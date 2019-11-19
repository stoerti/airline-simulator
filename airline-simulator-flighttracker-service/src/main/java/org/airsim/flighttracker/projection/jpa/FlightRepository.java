package org.airsim.flighttracker.projection.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<FlightEntity, UUID> {

}
