package de.airsim.projection.aircraft.jpa;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftTypeEntity {
	
	@Id
	private UUID id;
	
	private String code;
	private String name;
	private int seats;
}
