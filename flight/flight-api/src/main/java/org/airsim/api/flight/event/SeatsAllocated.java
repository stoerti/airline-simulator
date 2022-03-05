package org.airsim.api.flight.event;

import java.util.UUID;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class SeatsAllocated {
	
	private final UUID flightId;
	private final UUID seatAllocationId;
	
	private final int numberOfSeats;

}
