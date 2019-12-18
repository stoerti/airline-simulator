package org.airsim.agents;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;

import org.airsim.api.flight.command.CompleteBoarding;
import org.airsim.api.flight.command.CompleteCheckIn;
import org.airsim.api.flight.command.CompleteFlight;
import org.airsim.api.flight.command.StartBoarding;
import org.airsim.api.flight.command.StartCheckIn;
import org.airsim.api.flight.command.StartFlight;
import org.airsim.api.flight.event.FlightCreated;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class FlightOperatorAgent {

	private final TaskScheduler scheduler;

	private final CommandGateway commandGateway;

	// all times relevant to start time
	private static final long CHECKIN_OPEN_STARTTIME = 300;
	private static final long CHECKIN_CLOSE_STARTTIME = 200;
	private static final long BOARDING_OPEN_STARTTIME = 100;
	private static final long BOARDING_CLOSE_STARTTIME = 50;

	@EventHandler
	public void on(FlightCreated event) {
//		scheduler
//			.schedule(() -> startCheckin(event.getId()), convertToLocalInstant(event.getTakeoffTime())
//				.minusSeconds(CHECKIN_OPEN_STARTTIME - ((long) (Math.random() * 20))));
//		scheduler
//			.schedule(() -> completeCheckin(event.getId()),
//					convertToLocalInstant(event.getTakeoffTime()).minusSeconds(CHECKIN_CLOSE_STARTTIME - ((long) (Math.random() * 20))));
//		scheduler
//			.schedule(() -> startBoarding(event.getId()),
//					convertToLocalInstant(event.getTakeoffTime()).minusSeconds(BOARDING_OPEN_STARTTIME - ((long) (Math.random() * 20))));
//		scheduler
//			.schedule(() -> completeBoarding(event.getId()),
//					convertToLocalInstant(event.getTakeoffTime()).minusSeconds(BOARDING_CLOSE_STARTTIME - ((long) (Math.random() * 20))));
		scheduler.schedule(() -> startFlight(event.getId()), convertToLocalInstant(event.getTakeoffTime()));
		scheduler
			.schedule(() -> completeFlight(event.getId()),
					convertToLocalInstant(event.getTakeoffTime()).plus(event.getDuration()));
	}

	private void startCheckin(UUID flightId) {
		log.debug("Starting checkin for flight " + flightId);
		commandGateway.send(new StartCheckIn(flightId));
	}

	private void completeCheckin(UUID flightId) {
		log.debug("completing checkin for flight " + flightId);
		commandGateway.send(new CompleteCheckIn(flightId));
	}

	private void startBoarding(UUID flightId) {
		log.debug("Starting boarding for flight " + flightId);
		commandGateway.send(new StartBoarding(flightId));
	}

	private void completeBoarding(UUID flightId) {
		log.debug("Complete boarding for flight " + flightId);
		commandGateway.send(new CompleteBoarding(flightId));
	}

	private void startFlight(UUID flightId) {
		log.debug("Start flight for flight " + flightId);
		commandGateway.send(new StartFlight(flightId));
	}

	private void completeFlight(UUID flightId) {
		log.debug("Complete flight for flight " + flightId);
		commandGateway.send(new CompleteFlight(flightId));
	}

	private Instant convertToLocalInstant(LocalDateTime dateTime) {
		return dateTime.atZone(ZoneId.systemDefault()).toInstant();
	}

}
