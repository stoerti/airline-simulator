package org.airsim.domain.booking;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.Set;
import java.util.UUID;

import org.airsim.api.booking.FlightBookingStatus;
import org.airsim.api.booking.Passenger;
import org.airsim.api.booking.command.ConfirmFlightBookingCommand;
import org.airsim.api.booking.event.FlightBookingConfirmed;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class FlightBookingEntity {

	@EntityId(routingKey = "flightBookingId")
	private final UUID flightBookingId;
	
	@NonNull
	private FlightBookingStatus status;
	
	private UUID seatAllicationId;
	
	private Set<Passenger> passenger;
	
	@CommandHandler
	public void on(ConfirmFlightBookingCommand command) {
		if (status != FlightBookingStatus.CREATED) {
			throw new IllegalArgumentException("State transition from " + status + " to " + FlightBookingStatus.CONFIRMED + "is not allowed");
		}
		
		log.info("Confirm flight " + command);
		
		apply(FlightBookingConfirmed.builder()
				.bookingId(command.getBookingId())
				.flightBookingId(command.getFlightBookingId())
				.seatAllocationId(command.getSeatAllocationId())
				.build());
	}
	
	@EventSourcingHandler
	public void on(FlightBookingConfirmed event) {
		this.status = FlightBookingStatus.CONFIRMED;
		this.seatAllicationId = event.getSeatAllocationId();
	}
	
}
