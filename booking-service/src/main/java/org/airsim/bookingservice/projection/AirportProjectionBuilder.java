package org.airsim.bookingservice.projection;

import javax.transaction.Transactional;

import org.airsim.api.airport.AirportCreated;
import org.airsim.bookingservice.projection.jpa.AirportEntity;
import org.airsim.bookingservice.projection.jpa.AirportRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AirportProjectionBuilder {
	
	private final AirportRepository airportRepository;
	
	@EventHandler
	@Transactional
	public void on(AirportCreated event) {
		airportRepository.save(AirportEntity.builder()
				.id(event.getId())
				.iataCode(event.getIataCode())
				.name(event.getName())
				.city(event.getCity())
				.location(event.getLocation())
				.build());
	}


}
