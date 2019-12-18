package org.airsim.agents;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.airsim.agents.projection.jpa.AirportRepository;
import org.airsim.agents.projection.jpa.FlightEntity;
import org.airsim.agents.projection.jpa.FlightRepository;
import org.airsim.api.flight.event.CheckInCompleted;
import org.airsim.api.flight.event.FlightCompleted;
import org.airsim.api.flight.event.FlightStarted;
import org.airsim.api.flight.event.FlyingAircraftMoved;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Saga
@Slf4j
@NoArgsConstructor
public class FlightOperatorSaga {
	
	@Autowired
	private transient CommandGateway commandGateway;
	
	@Autowired
	private transient EventScheduler eventScheduler;
	
	@Autowired
	private transient AirportRepository airportRepository;
	
	@Autowired
	private transient FlightRepository flightRepository;
	
	private UUID flightId;
	
	private GlobalPosition from;
	private GlobalPosition to;
	
	private LocalDateTime start;
	private long duration;
	
	@SagaEventHandler(associationProperty = "flightId")
	@StartSaga
	public void on(FlightStarted event) {
		flightId = event.getFlightId();
		FlightEntity flight = flightRepository.findById(flightId).get();
		from = airportRepository.findByIataCode(flight.getAirportFrom()).getLocation();
		to = airportRepository.findByIataCode(flight.getAirportTo()).getLocation();
		
		start = LocalDateTime.now();
		duration = flight.getDuration().getSeconds();
		
		scheduleNextEvent();
	}
	
	@SagaEventHandler(associationProperty = "flightId")
	public void on(FlyingAircraftMoved event) {
		scheduleNextEvent();
	}
	
	@SagaEventHandler(associationProperty = "flightId")
	public void on(FlightCompleted event) {
		SagaLifecycle.end();
	}
	

	
	private void scheduleNextEvent() {
		double passedTime = start.until(LocalDateTime.now(), ChronoUnit.SECONDS);
		
		double latitude = from.getLatitude() + (passedTime / duration) * (to.getLatitude() - from.getLatitude());
		double longitude = from.getLongitude() + (passedTime / duration) * (to.getLongitude() - from.getLongitude());
		
		eventScheduler.schedule(Duration.ofSeconds(5), new FlyingAircraftMoved(flightId, latitude, longitude));
	}
}
