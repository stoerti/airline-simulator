package org.airsim.api.flight.event;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class CheckInStarted {
	
	@TargetAggregateIdentifier
	private final UUID flightId;

}
