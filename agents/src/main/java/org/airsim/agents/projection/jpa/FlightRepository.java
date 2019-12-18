package org.airsim.agents.projection.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface FlightRepository extends PagingAndSortingRepository<FlightEntity, UUID>, QueryByExampleExecutor<FlightEntity> {

	List<FlightEntity> findByAirportFromAndAirportToAndTakeoffTimeBetween(String airportFrom, String airportTo, LocalDateTime minTakeoffTime, LocalDateTime maxTakeoffTime);
}
