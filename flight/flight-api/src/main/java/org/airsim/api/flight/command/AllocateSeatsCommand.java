package org.airsim.api.flight.command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class AllocateSeatsCommand {

	@TargetAggregateIdentifier
	private final UUID flightId;
	
	private final UUID seatAllocationId;
	
	private final int numberOfSeats;

}
