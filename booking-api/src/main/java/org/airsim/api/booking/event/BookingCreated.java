package org.airsim.api.booking.event;

import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class BookingCreated {

	private final UUID bookingId;

	private final UUID customerId;
	
	@Singular
	private final Set<FlightBooking> flightBookings;

}
