package org.airsim.bookingservice.projection;

import org.airsim.api.customer.CustomerCreated;
import org.airsim.bookingservice.projection.jpa.CustomerEntity;
import org.airsim.bookingservice.projection.jpa.CustomerRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(CustomerCreated event) {
        customerRepository.save(CustomerEntity.builder()
                .id(event.getId())
                .name(event.getName())
                .lastName(event.getLastname())
                .build());
    }

    @ResetHandler
	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void reset() {
        log.info("-- resetted customer projection --");
        customerRepository.deleteAll();
    }
}
