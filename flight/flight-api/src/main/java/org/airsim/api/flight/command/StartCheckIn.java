package org.airsim.api.flight.command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class StartCheckIn {

	@TargetAggregateIdentifier
	private final UUID flightId;

}
