package org.airsim.bookingservice.projection;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.stream.Collectors;

import org.airsim.api.booking.FlightBookingStatus;
import org.airsim.api.booking.event.BookingCreated;
import org.airsim.api.booking.event.FlightBookingConfirmed;
import org.airsim.bookingservice.projection.jpa.BookingEntity;
import org.airsim.bookingservice.projection.jpa.BookingFlightEntity;
import org.airsim.bookingservice.projection.jpa.BookingPassengerEntity;
import org.airsim.bookingservice.projection.jpa.BookingRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookingProjectionBuilder {

	@Autowired
	private BookingRepository bookingRepository;

	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(BookingCreated event) {
		BookingEntity booking = BookingEntity
			.builder()
			.id(event.getBookingId())
			.customerId(event.getCustomerId())
			.flights(event
				.getFlightBookings()
				.stream()
				.map(fb -> BookingFlightEntity
					.builder()
					.id(fb.getFlightBookingId())
					.flightId(fb.getFlightId())
					.status(FlightBookingStatus.CREATED)
					.passengers(fb
						.getPassengers()
						.stream()
						.map(p -> BookingPassengerEntity
							.builder()
							.id(p.getId())
							.customerId(p.getCustomerId())
							.name(p.getName())
							.lastname(p.getLastname())
							.emailAddress(p.getEmailAddress())
							.build())
						.collect(Collectors.toList()))
					.build())
				.collect(Collectors.toList()))
			.build();

		bookingRepository.save(booking);
	}

	@EventHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void on(FlightBookingConfirmed event) {
		bookingRepository.findById(event.getBookingId()).ifPresent(booking -> {
			booking
				.getFlights()
				.stream()
				.filter(f -> f.getId().equals(event.getFlightBookingId()))
				.findFirst()
				.ifPresent(f -> f.setStatus(FlightBookingStatus.CONFIRMED));

			bookingRepository.save(booking);
		});
	}

	@ResetHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void reset() {
		log.info("-- resetted booking projection --");
		bookingRepository.deleteAll();
	}

}
