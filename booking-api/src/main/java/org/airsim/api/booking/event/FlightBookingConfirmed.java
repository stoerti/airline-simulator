package org.airsim.api.booking.event;

import java.util.UUID;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class FlightBookingConfirmed {
	@NonNull
	private final UUID bookingId;

	@NonNull
	private final UUID flightBookingId;	
	
	@NonNull
	private final UUID seatAllocationId;

}
