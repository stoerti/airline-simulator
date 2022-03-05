package org.airsim.flighttracker.projection.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface FlightplanRepository extends CrudRepository<FlightplanEntity, UUID> {

}
