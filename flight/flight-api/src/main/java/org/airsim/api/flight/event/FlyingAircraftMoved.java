package org.airsim.api.flight.event;

import java.util.UUID;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class FlyingAircraftMoved {
	
	private final UUID flightId;
	
	private final double latitude;
	private final double longitude;

}
