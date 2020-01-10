package org.airsim.projection.jpa;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findByNameAndLastName(String name, String lastname);

}
