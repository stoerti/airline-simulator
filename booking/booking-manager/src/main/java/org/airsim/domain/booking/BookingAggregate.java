package org.airsim.domain.booking;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.airsim.api.booking.FlightBookingStatus;
import org.airsim.api.booking.command.CreateBookingCommand;
import org.airsim.api.booking.event.BookingCreated;
import org.airsim.api.booking.event.FlightBooking;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.modelling.command.ForwardMatchingInstances;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
@NoArgsConstructor
public class BookingAggregate {

	@AggregateIdentifier
	private UUID bookingId;

	@AggregateMember(eventForwardingMode = ForwardMatchingInstances.class)
	private final Map<UUID, FlightBookingEntity> flightBookings = new HashMap<>();

	@CommandHandler
	public BookingAggregate(CreateBookingCommand command) {
		log.info("Adding booking " + command);

		apply(BookingCreated
			.builder()
			.bookingId(command.getBookingId())
			.customerId(command.getCustomerId())
			.flightBookings(command
				.getFlightBookings()
				.stream()
				.map(fb -> FlightBooking
					.builder()
					.flightBookingId(fb.getFlightBookingId())
					.flightId(fb.getFlightId())
					.status(FlightBookingStatus.CREATED)
					.passengers(fb.getPassengers())
					.build())
				.collect(Collectors.toList()))
			.build());
	}

	@EventSourcingHandler
	public void on(BookingCreated event) {
		this.bookingId = event.getBookingId();

		for (FlightBooking flightBooking : event.getFlightBookings()) {
			flightBookings
				.put(flightBooking.getFlightBookingId(),
						FlightBookingEntity
							.builder()
							.flightBookingId(flightBooking.getFlightBookingId())
							.status(FlightBookingStatus.CREATED)
							.build());
		}
	}

}
