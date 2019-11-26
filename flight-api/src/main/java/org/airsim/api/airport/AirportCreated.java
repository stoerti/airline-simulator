package org.airsim.api.airport;

import java.util.UUID;

import org.gavaghan.geodesy.GlobalPosition;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class AirportCreated {
		
	private final UUID id;
	
	private final String iataCode;
	private final String name;
	private final String fullName;
	private final String city;
	private final GlobalPosition location;
}
