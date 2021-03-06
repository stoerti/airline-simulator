package org.airsim.agents.projection;

import org.airsim.agents.projection.jpa.CustomerEntity;
import org.airsim.agents.projection.jpa.CustomerRepository;
import org.airsim.api.customer.CustomerCreated;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomerProjectionBuilder {

    @Autowired
    private CustomerRepository customerRepository;

    @EventHandler
    @Order(1)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(CustomerCreated event) {
        customerRepository.save(CustomerEntity.builder()
                .id(event.getId())
                .name(event.getName())
                .lastName(event.getLastname())
                .build());
    }

    @ResetHandler
    @Order(1)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void reset() {
        log.info("-- resetted customer projection --");
        customerRepository.deleteAll();
    }
}
