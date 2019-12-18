package org.airsim.flighttracker;

import java.util.ArrayList;
import java.util.List;

import org.airsim.api.flight.FlightStatus;
import org.airsim.flighttracker.dto.AircraftType;
import org.airsim.flighttracker.dto.AircraftTypeSearchResult;
import org.airsim.flighttracker.dto.Airport;
import org.airsim.flighttracker.dto.AirportSearchResult;
import org.airsim.flighttracker.dto.Flight;
import org.airsim.flighttracker.dto.FlightSearchResult;
import org.airsim.flighttracker.projection.jpa.AircraftTypeEntity;
import org.airsim.flighttracker.projection.jpa.AircraftTypeRepository;
import org.airsim.flighttracker.projection.jpa.AirportEntity;
import org.airsim.flighttracker.projection.jpa.AirportRepository;
import org.airsim.flighttracker.projection.jpa.FlightEntity;
import org.airsim.flighttracker.projection.jpa.FlightRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public FlightSearchResult getFlights(
			@RequestParam("page") int page,
			@RequestParam("pagesize") int pagesize,
			@RequestParam(name = "airportFrom", required = false) String airportFrom,
			@RequestParam(name = "airportTo", required = false) String airportTo,
			@RequestParam(name = "flightStatus", required = false) FlightStatus flightStatus) {
		List<Flight> result = new ArrayList<>();
		
		FlightEntity flightExample = new FlightEntity();
		if (airportFrom != null) {
			flightExample.setAirportFrom(airportFrom);
		}
		if (airportTo != null) {
			flightExample.setAirportTo(airportTo);
		}
		if (flightStatus != null) {
			flightExample.setFlightStatus(flightStatus);
		}
		
		Example<FlightEntity> example = Example.of(flightExample);

		Page<FlightEntity> searchResult = flightRepository
			.findAll(example, PageRequest.of(page, pagesize, Sort.by("takeoffTime").ascending()));

		searchResult.forEach(flight -> {
			AirportEntity airportEntityFrom = airportRepository.findByIataCode(flight.getAirportFrom());
			AirportEntity airportEntityTo = airportRepository.findByIataCode(flight.getAirportTo());
			AircraftTypeEntity aircraftType = aircraftTypeRepository.findByCode(flight.getAircraftTypeCode());

			result
				.add(Flight
					.builder()
					.id(flight.getId())
					.flightNumber(flight.getFlightNumber())
					.flightStatus(flight.getFlightStatus())
					.airportFrom(convert(airportEntityFrom))
					.airportTo(convert(airportEntityTo))
					.takeoffTime(flight.getTakeoffTime())
					.duration(flight.getDuration())
					.aircraftType(convert(aircraftType))
					.seatsAvailable(flight.getSeatsAvailable())
					.seatsTaken(flight.getSeatsTaken())
					.build());
		});

		return new FlightSearchResult(result, searchResult.getNumber(), searchResult.getSize(),
				searchResult.getTotalElements());
	}


	@GetMapping(value = "/flights/airborne")
	public FlightSearchResult getAiredFlights(
			@RequestParam("page") int page,
			@RequestParam("pagesize") int pagesize) {
		List<Flight> result = new ArrayList<>();
		
		FlightEntity flightExample = new FlightEntity();
		flightExample.setFlightStatus(FlightStatus.IN_AIR);
		
		Example<FlightEntity> example = Example.of(flightExample);

		Page<FlightEntity> searchResult = flightRepository
			.findAll(example, PageRequest.of(page, pagesize, Sort.by("takeoffTime").ascending()));

		searchResult.forEach(flight -> {
			AirportEntity airportEntityFrom = airportRepository.findByIataCode(flight.getAirportFrom());
			AirportEntity airportEntityTo = airportRepository.findByIataCode(flight.getAirportTo());
			AircraftTypeEntity aircraftType = aircraftTypeRepository.findByCode(flight.getAircraftTypeCode());

			result
				.add(Flight
					.builder()
					.id(flight.getId())
					.flightNumber(flight.getFlightNumber())
					.flightStatus(flight.getFlightStatus())
					.airportFrom(convert(airportEntityFrom))
					.airportTo(convert(airportEntityTo))
					.takeoffTime(flight.getTakeoffTime())
					.duration(flight.getDuration())
					.aircraftType(convert(aircraftType))
					.seatsAvailable(flight.getSeatsAvailable())
					.seatsTaken(flight.getSeatsTaken())
					.positionLatitude(flight.getPositionLatitude() != null ? flight.getPositionLatitude() : 0d)
					.positionLongitude(flight.getPositionLongitude() != null ? flight.getPositionLongitude() : 0d)
					.build());
		});

		return new FlightSearchResult(result, searchResult.getNumber(), searchResult.getSize(),
				searchResult.getTotalElements());
	}

	@GetMapping(value = "/airports")
	public AirportSearchResult getAirports(@RequestParam("page") int page, @RequestParam("pagesize") int pagesize) {
		List<Airport> result = new ArrayList<>();

		Page<AirportEntity> searchResult = airportRepository
			.findAll(PageRequest.of(page, pagesize, Sort.by("name").ascending()));

		searchResult.forEach(airport -> result.add(convert(airport)));

		return new AirportSearchResult(result, searchResult.getNumber(), searchResult.getSize(),
				searchResult.getTotalElements());
	}

	@GetMapping(value = "/aircraftTypes")
	public AircraftTypeSearchResult getAircraftTypes(@RequestParam("page") int page,
			@RequestParam("pagesize") int pagesize) {
		List<AircraftType> result = new ArrayList<>();

		Page<AircraftTypeEntity> searchResult = aircraftTypeRepository
			.findAll(PageRequest.of(page, pagesize, Sort.by("name").ascending()));

		searchResult.forEach(airport -> result.add(convert(airport)));

		return new AircraftTypeSearchResult(result, searchResult.getNumber(), searchResult.getSize(),
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
