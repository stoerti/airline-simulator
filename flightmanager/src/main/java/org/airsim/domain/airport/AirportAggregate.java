package org.airsim.domain.airport;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;

import org.airsim.api.airport.AirportCreated;
import org.airsim.api.airport.CreateAirportCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AirportAggregate {
	
	@AggregateIdentifier
	private UUID id;

	@CommandHandler
	public AirportAggregate(CreateAirportCommand command) {
		apply(AirportCreated
				.builder()
				.id(command.getId())
				.iataCode(command.getIataCode())
				.fullName(command.getFullName())
				.name(command.getName())
				.city(command.getCity())
				.location(command.getLocation())
				.build());
	}

	@EventSourcingHandler
	public void on(AirportCreated event) {
		this.id = event.getId();
	}	
	
}
