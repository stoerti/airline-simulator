package org.airsim.domain.flight;

import java.util.UUID;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class SeatAllocation {
	
	private final UUID id;
	private final UUID flightId;
	
	private final int numberOfSeats;

}
