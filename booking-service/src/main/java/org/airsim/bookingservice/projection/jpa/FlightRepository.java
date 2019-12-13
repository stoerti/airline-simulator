package org.airsim.bookingservice.projection.jpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface FlightRepository extends PagingAndSortingRepository<FlightEntity, UUID>, QueryByExampleExecutor<FlightEntity> {

	List<FlightEntity> findByAirportFromAndAirportToAndTakeoffTimeBetween(String airportFrom, String airportTo, LocalDateTime minTakeoffTime, LocalDateTime maxTakeoffTime);
}
