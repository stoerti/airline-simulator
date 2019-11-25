package org.airsim.flighttracker;

import java.util.ArrayList;
import java.util.List;

import org.airsim.flighttracker.dto.Flight;
import org.airsim.flighttracker.dto.FlightSearchResult;
import org.airsim.flighttracker.projection.jpa.FlightEntity;
import org.airsim.flighttracker.projection.jpa.FlightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FlightTrackerController {

	private final FlightRepository flightRepository;

	@GetMapping(value = "/flights")
	public FlightSearchResult getFlights(@RequestParam("page") int page, @RequestParam("pagesize") int pagesize) {
		List<Flight> result = new ArrayList<Flight>();

		Page<FlightEntity> searchResult = flightRepository.findAll(PageRequest.of(page, pagesize, Sort.by("takeoffTime").ascending()));
		
		searchResult.forEach(flight -> result.add(Flight.builder().
				id(flight.getId())
				.flightNumber(flight.getFlightNumber())
				.flightStatus(flight.getFlightStatus())
				.airportFrom(flight.getAirportFrom())
				.airportTo(flight.getAirportTo())
				.takeoffTime(flight.getTakeoffTime())
				.duration(flight.getDuration())
				.aircraftTypeCode(flight.getAircraftTypeCode())
				.build()));

		return new FlightSearchResult(result, searchResult.getNumber(), searchResult.getSize(), searchResult.getTotalElements());
	}
}
