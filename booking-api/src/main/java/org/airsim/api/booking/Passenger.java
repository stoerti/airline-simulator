package org.airsim.api.booking;

import java.util.UUID;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class Passenger {

	private final UUID id;
	
	private final String name;
	private final String lastname;
	
	private final String emailAddress;

}
