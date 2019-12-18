package org.airsim.api.flight.command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class MoveFlyingAircraftCommand {
	
	@TargetAggregateIdentifier
	private final UUID flightId;
	
	private final double latitude;
	private final double longitude;
}
