package org.airsim.api.booking.command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class ConfirmFlightBookingCommand {
	
	@TargetAggregateIdentifier
	@NonNull
	private final UUID bookingId;

	@NonNull
	private final UUID flightBookingId;	
	
	@NonNull
	private final UUID seatAllocationId;

}
