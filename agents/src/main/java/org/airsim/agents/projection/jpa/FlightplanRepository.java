package org.airsim.agents.projection.jpa;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FlightplanRepository extends CrudRepository<FlightplanEntity, UUID> {

}
