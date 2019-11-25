package org.airsim.api.aircraft;

import java.util.UUID;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class AircraftTypeCreated {

	private final UUID id;
	
	private final String code;
	private final String name;
	private final int seats;

}
