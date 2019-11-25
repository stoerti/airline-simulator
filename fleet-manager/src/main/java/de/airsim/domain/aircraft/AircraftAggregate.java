package de.airsim.domain.aircraft;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;

import org.airsim.api.aircrafttype.AircraftCreated;
import org.airsim.api.aircrafttype.CreateAircraftCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class AircraftAggregate {

	@AggregateIdentifier
	private UUID id;
	
	public AircraftAggregate() {
		
	}
	
	@CommandHandler
	public AircraftAggregate(CreateAircraftCommand command) {
		log.debug("Adding aircraft" + command);

		apply(AircraftCreated
			.builder()
			.id(command.getId())
			.code(command.getCode())
			.type(command.getType())
			.build());
	}
	
	@EventSourcingHandler
	public void on(AircraftCreated event) {
		this.id = event.getId();
	}

}
