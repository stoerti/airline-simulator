package org.airsim.agents.projection;

import lombok.extern.slf4j.Slf4j;
import org.airsim.agents.projection.jpa.CustomerEntity;
import org.airsim.agents.projection.jpa.CustomerRepository;
import org.airsim.agents.projection.jpa.FlightplanEntity;
import org.airsim.api.customer.CustomerCreated;
import org.airsim.api.flight.event.CheckInStarted;
import org.airsim.api.flight.event.SeatsAllocated;
import org.airsim.api.flightplan.FlightplanCreated;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Slf4j
public class CustomerProjectionBuilder {

    @Autowired
    private CustomerRepository customerRepository;

    @EventHandler
    @Transactional
    public void on(CustomerCreated event) {
        customerRepository.save(CustomerEntity.builder()
                .id(event.getId())
                .name(event.getName())
                .lastName(event.getLastname())
                .build());
    }

    @ResetHandler
    public void reset() {
        log.info("-- resetted customer projection --");
        customerRepository.deleteAll();
    }
}
