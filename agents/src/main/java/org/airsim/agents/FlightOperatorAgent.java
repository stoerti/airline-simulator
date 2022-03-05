package org.airsim.agents;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.airsim.agents.projection.jpa.FlightEntity;
import org.airsim.agents.projection.jpa.FlightRepository;
import org.airsim.api.flight.command.CompleteBoarding;
import org.airsim.api.flight.command.CompleteCheckIn;
import org.airsim.api.flight.command.CompleteFlight;
import org.airsim.api.flight.command.StartBoarding;
import org.airsim.api.flight.command.StartCheckIn;
import org.airsim.api.flight.command.StartFlight;
import org.airsim.api.flight.event.BoardingCompleted;
import org.airsim.api.flight.event.BoardingStarted;
import org.airsim.api.flight.event.CheckInCompleted;
import org.airsim.api.flight.event.CheckInStarted;
import org.airsim.api.flight.event.FlightCreated;
import org.airsim.api.flight.event.FlightStarted;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.DisallowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class FlightOperatorAgent {

    // all times relevant to start time
    private static final long CHECKIN_OPEN_STARTTIME = 300;
    private static final long CHECKIN_CLOSE_STARTTIME = 200;
    private static final long BOARDING_OPEN_STARTTIME = 100;
    private static final long BOARDING_CLOSE_STARTTIME = 50;
    private final CommandGateway commandGateway;
    private final FlightRepository flightRepository;

    private final Scheduler jobScheduler;
	private final JobDetail startCheckinJobDetail;
	private final JobDetail closeCheckinJobDetail;

    @EventHandler
    @Order(10)
    public void on(FlightCreated event) throws Exception {
		jobScheduler.scheduleJob(TriggerBuilder
				.newTrigger()
				.withIdentity(UUID.randomUUID().toString())
				.forJob(startCheckinJobDetail)
				.withDescription("Sample trigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
				.startAt(convertToDate(event.getTakeoffTime()
                        .minusSeconds(CHECKIN_OPEN_STARTTIME - ((long) (Math.random() * 20)))))
				.usingJobData("flightId", event.getId().toString())
				.build());
    }

    public void startCheckin(UUID flightId) {
        log.debug("Starting checkin for flight " + flightId);
        commandGateway.send(new StartCheckIn(flightId));
    }

    @EventHandler
    @DisallowReplay
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(CheckInStarted event) throws Exception {
        FlightEntity flight = flightRepository.findById(event.getFlightId()).get();

		jobScheduler.scheduleJob(TriggerBuilder
				.newTrigger()
				.withIdentity(UUID.randomUUID().toString())
				.forJob(closeCheckinJobDetail)
				.withDescription("Sample trigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
				.startAt(convertToDate(flight.getTakeoffTime().minusSeconds(CHECKIN_CLOSE_STARTTIME - ((long) (Math.random() * 20)))))
				.usingJobData("flightId", event.getFlightId().toString())
				.build());
    }

    public void completeCheckin(UUID flightId) {
        log.debug("completing checkin for flight " + flightId);
        commandGateway.send(new CompleteCheckIn(flightId));
    }

    @EventHandler
    @DisallowReplay
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(CheckInCompleted event) {
        FlightEntity flight = flightRepository.findById(event.getFlightId()).get();
//        scheduler
//                .schedule(() -> startBoarding(event.getFlightId()),
//                        convertToLocalInstant(flight.getTakeoffTime()).minusSeconds(BOARDING_OPEN_STARTTIME - ((long) (Math.random() * 20))));
    }

    private void startBoarding(UUID flightId) {
        log.debug("Starting boarding for flight " + flightId);
        commandGateway.send(new StartBoarding(flightId));
    }

    @EventHandler
    @DisallowReplay
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(BoardingStarted event) {
        FlightEntity flight = flightRepository.findById(event.getFlightId()).get();
//        scheduler
//                .schedule(() -> completeBoarding(event.getFlightId()),
//                        convertToLocalInstant(flight.getTakeoffTime()).minusSeconds(BOARDING_CLOSE_STARTTIME - ((long) (Math.random() * 20))));
    }

    private void completeBoarding(UUID flightId) {
        log.debug("Complete boarding for flight " + flightId);
        commandGateway.send(new CompleteBoarding(flightId));
    }

    @EventHandler
    @DisallowReplay
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(BoardingCompleted event) {
        FlightEntity flight = flightRepository.findById(event.getFlightId()).get();
//        scheduler.schedule(() -> startFlight(event.getFlightId()), convertToLocalInstant(flight.getTakeoffTime()));
    }

    private void startFlight(UUID flightId) {
        log.debug("Start flight for flight " + flightId);
        commandGateway.send(new StartFlight(flightId));
    }

    @EventHandler
    @DisallowReplay
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(FlightStarted event) {
        FlightEntity flight = flightRepository.findById(event.getFlightId()).get();
//        scheduler
//                .schedule(() -> completeFlight(event.getFlightId()),
//                        convertToLocalInstant(flight.getTakeoffTime()).plus(flight.getDuration()));
    }

    private void completeFlight(UUID flightId) {
        log.debug("Complete flight for flight " + flightId);
        commandGateway.send(new CompleteFlight(flightId));
    }

    private Date convertToDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
