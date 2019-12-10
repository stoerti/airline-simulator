package org.airsim.api.booking.command;

import java.util.Set;
import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.Value;

@Builder
@Value

@RequiredArgsConstructor
public class CreateBookingCommand {
	
	@TargetAggregateIdentifier
	private final UUID bookingId;

	private final UUID customerId;
	
	@Singular
	private final Set<FlightBooking> flightBookings;

}
