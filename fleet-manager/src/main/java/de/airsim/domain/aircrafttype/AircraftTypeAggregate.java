package de.airsim.domain.aircrafttype;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;

import org.airsim.api.aircraft.AircraftTypeCreated;
import org.airsim.api.aircraft.CreateAircraftTypeCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class AircraftTypeAggregate {

	@AggregateIdentifier
	private UUID id;
	
	public AircraftTypeAggregate() {
		
	}
	
	@CommandHandler
	public AircraftTypeAggregate(CreateAircraftTypeCommand command) {
		log.debug("Adding aircrafttype" + command);

		apply(AircraftTypeCreated
			.builder()
			.id(command.getId())
			.code(command.getCode())
			.name(command.getName())
			.seats(command.getSeats())
			.build());
	}
	
	@EventSourcingHandler
	public void on(AircraftTypeCreated event) {
		this.id = event.getId();
	}

}
