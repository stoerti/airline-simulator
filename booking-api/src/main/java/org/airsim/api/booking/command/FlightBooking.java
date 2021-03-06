package org.airsim.api.booking.command;

import java.util.Set;
import java.util.UUID;

import org.airsim.api.booking.Passenger;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class FlightBooking {

	@NonNull
	private final UUID flightBookingId;
	
	@NonNull
	private final UUID flightId;

	@NonNull
	@Singular
	private final Set<Passenger> passengers;

}