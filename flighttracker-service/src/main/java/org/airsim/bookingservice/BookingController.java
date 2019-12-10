package org.airsim.bookingservice;

import static java.util.UUID.randomUUID;

import java.util.UUID;

import org.airsim.api.booking.Passenger;
import org.airsim.api.booking.command.CreateBookingCommand;
import org.airsim.api.booking.command.FlightBooking;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookingController {

	private final CommandGateway commandGateway;

	@GetMapping(value = "/booking")
	public UUID getBookFlight(@RequestParam("customerId") UUID customerId, @RequestParam("flightId") UUID flightId,
			@RequestParam("numberOfPassengers") int numberOfPassengers) {

		UUID bookingId = randomUUID();

		commandGateway
			.send(CreateBookingCommand
				.builder()
				.bookingId(bookingId)
				.customerId(customerId)
				.flightBooking(FlightBooking
					.builder()
					.flightBookingId(randomUUID())
					.flightId(flightId)
					.passenger(Passenger.builder().id(randomUUID()).build())
					.passenger(Passenger.builder().id(randomUUID()).build())
					.build())
				.build());
		
		return bookingId;
	}

}
