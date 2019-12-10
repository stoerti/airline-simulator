package org.airsim.flighttracker.projection.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AircraftRepository extends PagingAndSortingRepository<AircraftEntity, UUID> {

}
