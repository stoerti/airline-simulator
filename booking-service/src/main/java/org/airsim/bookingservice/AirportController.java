package org.airsim.bookingservice;

import java.util.ArrayList;
import java.util.List;

import org.airsim.bookingservice.dto.Airport;
import org.airsim.bookingservice.dto.AirportSearchResult;
import org.airsim.bookingservice.projection.jpa.AirportEntity;
import org.airsim.bookingservice.projection.jpa.AirportRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AirportController {

	private final AirportRepository airportRepository;

	@GetMapping(value = "/airports")
	public AirportSearchResult getAirports() {
		List<Airport> result = new ArrayList<>();

		Iterable<AirportEntity> searchResult = airportRepository
			.findAll(Sort.by("name").ascending());

		searchResult.forEach(airport -> result.add(convert(airport)));

		return new AirportSearchResult(result);
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
}
