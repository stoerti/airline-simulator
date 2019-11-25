package org.airsim.flighttracker.projection.jpa;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface FlightRepository extends PagingAndSortingRepository<FlightEntity, UUID> {

}
