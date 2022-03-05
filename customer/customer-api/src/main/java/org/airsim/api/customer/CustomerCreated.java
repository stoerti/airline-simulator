package org.airsim.api.customer;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class CustomerCreated {

	private final UUID id;
	
	private final String name;
	private final String lastname;
	
	private final String emailAddress;

}
