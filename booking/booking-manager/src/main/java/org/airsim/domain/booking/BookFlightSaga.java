package org.airsim.domain.booking;

import static java.util.UUID.randomUUID;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.airsim.api.booking.command.ConfirmFlightBookingCommand;
import org.airsim.api.booking.event.BookingCreated;
import org.airsim.api.booking.event.FlightBooking;
import org.airsim.api.booking.event.FlightBookingConfirmed;
import org.airsim.api.flight.command.AllocateSeatsCommand;
import org.airsim.api.flight.event.SeatsAllocated;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Saga
@Slf4j
@NoArgsConstructor
public class BookFlightSaga {
	
	@Autowired
	private transient CommandGateway commandGateway;
	
	private UUID bookingId;
	
	private final Map<UUID, FlightBooking> flightBookingsToProcess = new HashMap<>();
	private final Map<UUID, FlightBooking> flightBookingsToConfirm = new HashMap<>();
	
	private final Map<UUID, FlightBooking> seatAllocationToFlightBooking = new HashMap<>();
	
	
	@SagaEventHandler(associationProperty = "bookingId")
	@StartSaga
	public void on(BookingCreated event) {
		log.info("Start BookFlightSaga for booking {}", event.getBookingId());
		this.bookingId = event.getBookingId();
		
		for (FlightBooking flightBooking : event.getFlightBookings()) {
			flightBookingsToProcess.put(flightBooking.getFlightBookingId(), flightBooking);
			
			UUID seatAllocationId = randomUUID();
			SagaLifecycle.associateWith("seatAllocationId", seatAllocationId.toString());
			seatAllocationToFlightBooking.put(seatAllocationId, flightBooking);
			
			commandGateway.send(AllocateSeatsCommand.builder()
					.seatAllocationId(seatAllocationId)
					.flightId(flightBooking.getFlightId())
					.numberOfSeats(flightBooking.getPassengers().size())
					.build());
		}
	}
	
	@SagaEventHandler(associationProperty = "seatAllocationId")
	public void on(SeatsAllocated event) {
		
		FlightBooking flightBooking = seatAllocationToFlightBooking.get(event.getSeatAllocationId());
		
		flightBookingsToProcess.remove(flightBooking.getFlightBookingId());
		flightBookingsToConfirm.put(flightBooking.getFlightBookingId(), flightBooking);

		SagaLifecycle.associateWith("flightBookingId", flightBooking.getFlightBookingId().toString());
		commandGateway.send(ConfirmFlightBookingCommand.builder()
				.bookingId(bookingId)
				.flightBookingId(flightBooking.getFlightBookingId())
				.seatAllocationId(event.getSeatAllocationId())
				.build());
	}
	
	@SagaEventHandler(associationProperty = "flightBookingId")
	public void on(FlightBookingConfirmed event) {
		flightBookingsToConfirm.remove(event.getFlightBookingId());

		if (flightBookingsToProcess.isEmpty() && flightBookingsToConfirm.isEmpty()) {
			SagaLifecycle.end();
		}
	}
	
	
}
