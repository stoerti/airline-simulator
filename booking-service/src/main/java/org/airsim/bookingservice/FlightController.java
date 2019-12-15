package org.airsim.bookingservice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.airsim.api.flight.FlightStatus;
import org.airsim.bookingservice.dto.AircraftType;
import org.airsim.bookingservice.dto.AircraftTypeSearchResult;
import org.airsim.bookingservice.dto.Airport;
import org.airsim.bookingservice.dto.AirportSearchResult;
import org.airsim.bookingservice.dto.Flight;
import org.airsim.bookingservice.dto.FlightSearchResult;
import org.airsim.bookingservice.projection.jpa.AircraftTypeEntity;
import org.airsim.bookingservice.projection.jpa.AircraftTypeRepository;
import org.airsim.bookingservice.projection.jpa.AirportEntity;
import org.airsim.bookingservice.projection.jpa.AirportRepository;
import org.airsim.bookingservice.projection.jpa.FlightEntity;
import org.airsim.bookingservice.projection.jpa.FlightRepository;
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
public class FlightController {

	private final FlightRepository flightRepository;
	private final AirportRepository airportRepository;
	private final AircraftTypeRepository aircraftTypeRepository;

	@GetMapping(value = "/flights")
	public FlightSearchResult getFlights(
			@RequestParam(name = "airportFrom") String airportFrom,
			@RequestParam(name = "airportTo") String airportTo,
			@RequestParam(name = "day") LocalDate flightDay) {
		List<Flight> result = new ArrayList<>();
		
		LocalDateTime minTakeoffTime = flightDay.atStartOfDay();
		LocalDateTime maxTakeoffTime = minTakeoffTime.plusDays(1);

		List<FlightEntity> searchResult = flightRepository.findByAirportFromAndAirportToAndTakeoffTimeBetween(airportFrom, airportTo, minTakeoffTime, maxTakeoffTime);

		searchResult.forEach(flight -> {
			AirportEntity airportEntityFrom = airportRepository.findByIataCode(flight.getAirportFrom());
			AirportEntity airportEntityTo = airportRepository.findByIataCode(flight.getAirportTo());
			AircraftTypeEntity aircraftType = aircraftTypeRepository.findByCode(flight.getAircraftTypeCode());

			result
				.add(Flight
					.builder()
					.id(flight.getId())
					.flightNumber(flight.getFlightNumber())
					.airportFrom(convert(airportEntityFrom))
					.airportTo(convert(airportEntityTo))
					.takeoffTime(flight.getTakeoffTime())
					.duration(flight.getDuration())
					.aircraftType(convert(aircraftType))
					.build());
		});

		return new FlightSearchResult(result);
	}

	private Airport convert(AirportEntity entity) {
		return Airport
			.builder()
			.id(entity.getId())
			.iataCode(entity.getIataCode())
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
			.hasWiFi(entity.isHasWiFi())
			.hasSeatPower(entity.isHasSeatPower())
			.hasEntertainment(entity.isHasEntertainment())
			.legroom(entity.getLegroom())
			.build();
	}
	
}
