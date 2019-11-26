package org.airsim.flighttracker;

import java.util.ArrayList;
import java.util.List;

import org.airsim.flighttracker.dto.AircraftType;
import org.airsim.flighttracker.dto.Airport;
import org.airsim.flighttracker.dto.Flight;
import org.airsim.flighttracker.dto.FlightSearchResult;
import org.airsim.flighttracker.projection.jpa.AircraftTypeEntity;
import org.airsim.flighttracker.projection.jpa.AircraftTypeRepository;
import org.airsim.flighttracker.projection.jpa.AirportEntity;
import org.airsim.flighttracker.projection.jpa.AirportRepository;
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
	private final AirportRepository airportRepository;
	private final AircraftTypeRepository aircraftTypeRepository;

	@GetMapping(value = "/flights")
	public FlightSearchResult getFlights(@RequestParam("page") int page, @RequestParam("pagesize") int pagesize) {
		List<Flight> result = new ArrayList<>();

		Page<FlightEntity> searchResult = flightRepository
			.findAll(PageRequest.of(page, pagesize, Sort.by("takeoffTime").ascending()));

		searchResult.forEach(flight -> {
			AirportEntity airportFrom = airportRepository.findByIataCode(flight.getAirportFrom());
			AirportEntity airportTo = airportRepository.findByIataCode(flight.getAirportTo());
			AircraftTypeEntity aircraftType = aircraftTypeRepository.findByCode(flight.getAircraftTypeCode());

			result
				.add(Flight
					.builder()
					.id(flight.getId())
					.flightNumber(flight.getFlightNumber())
					.flightStatus(flight.getFlightStatus())
					.airportFrom(convert(airportFrom))
					.airportTo(convert(airportTo))
					.takeoffTime(flight.getTakeoffTime())
					.duration(flight.getDuration())
					.aircraftType(convert(aircraftType))
					.build());
		});

		return new FlightSearchResult(result, searchResult.getNumber(), searchResult.getSize(),
				searchResult.getTotalElements());
	}

	private Airport convert(AirportEntity entity) {
		return Airport
			.builder()
			.id(entity.getId())
			.iataCode(entity.getIataCode())
			.fullName(entity.getFullName())
			.name(entity.getName())
			.city(entity.getCity())
			.location(entity.getLocation())
			.build();
	}
	private AircraftType convert(AircraftTypeEntity entity) {
		return AircraftType
			.builder()
			.id(entity.getId())
			.code(entity.getCode())
			.name(entity.getName())
			.seats(entity.getSeats())
			.hasWiFi(entity.isHasWiFi())
			.hasSeatPower(entity.isHasSeatPower())
			.hasEntertainment(entity.isHasEntertainment())
			.legroom(entity.getLegroom())
			.build();
	}
}
