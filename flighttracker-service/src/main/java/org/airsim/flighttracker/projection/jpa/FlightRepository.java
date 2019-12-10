package org.airsim.flighttracker.projection.jpa;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface FlightRepository extends PagingAndSortingRepository<FlightEntity, UUID>, QueryByExampleExecutor<FlightEntity> {

}
