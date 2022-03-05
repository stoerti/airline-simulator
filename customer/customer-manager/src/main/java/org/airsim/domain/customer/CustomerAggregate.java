package org.airsim.domain.customer;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;

import org.airsim.api.customer.CreateCustomerCommand;
import org.airsim.api.customer.CustomerCreated;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
@NoArgsConstructor
public class CustomerAggregate {

	@AggregateIdentifier
	private UUID id;

	@CommandHandler
	public CustomerAggregate(CreateCustomerCommand command) {
		log.info("Adding customer " + command);

		apply(CustomerCreated
			.builder()
			.id(command.getId())
			.name(command.getName())
			.lastname(command.getLastname())
			.emailAddress(command.getEmailAddress())
			.build());
	}
	
	@EventSourcingHandler
	public void on(CustomerCreated event) {
		this.id = event.getId();
	}

}
