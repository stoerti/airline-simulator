package org.airsim.api.aircrafttype;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class CreateAircraftCommand {
	
	
	@TargetAggregateIdentifier
	private final UUID id;
	
	private final UUID type;
	private final String code;

}
