package org.airsim.bookingservice;

import static java.util.UUID.randomUUID;

import java.util.UUID;
import java.util.stream.Collectors;

import org.airsim.api.booking.Passenger;
import org.airsim.api.booking.command.CreateBookingCommand;
import org.airsim.api.booking.command.FlightBooking;
import org.airsim.bookingservice.dto.BookingRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookingController {

	private final CommandGateway commandGateway;

	@PutMapping(value = "/booking")
	public UUID getBookFlight(@RequestBody BookingRequest bookingRequest) {

		UUID bookingId = randomUUID();

		commandGateway
			.send(CreateBookingCommand
				.builder()
				.bookingId(bookingId)
				.customerId(bookingRequest.getCustomerUuid())
				.flightBookings(bookingRequest.getFlightUuids().stream().map(flightId -> FlightBooking
					.builder()
					.flightBookingId(randomUUID())
					.flightId(flightId)
					.passenger(Passenger.builder().id(randomUUID()).build())
					.passenger(Passenger.builder().id(randomUUID()).build())
					.build()).collect(Collectors.toList()))
				.build());
		
		return bookingId;
	}

}
