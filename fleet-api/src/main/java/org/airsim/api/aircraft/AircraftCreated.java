package org.airsim.api.aircraft;

import java.util.UUID;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class AircraftCreated {

	private final UUID id;
	
	private final UUID type;
	private final String code;

}
