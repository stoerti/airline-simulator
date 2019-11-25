package org.airsim.api.flight.command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class StartBoarding {
	
	@TargetAggregateIdentifier
	private final UUID flightId;

}
