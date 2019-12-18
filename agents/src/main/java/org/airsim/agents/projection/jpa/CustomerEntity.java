package org.airsim.agents.projection.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "AG_CUSTOMER")
public class CustomerEntity {

	@Id
	private UUID id;

	private String name;
	private String lastName;
}
