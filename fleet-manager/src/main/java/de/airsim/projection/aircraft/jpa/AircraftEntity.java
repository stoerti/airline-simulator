package de.airsim.projection.aircraft.jpa;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftEntity {
	
	@Id
	private UUID id;
	
	@ManyToOne
	private AircraftTypeEntity type;
	private String code;
}
