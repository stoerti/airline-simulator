package org.airsim.flighttracker.projection.jpa;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TR_AIRCRAFT")
public class AircraftEntity {
	
	@Id
	private UUID id;
	
	@ManyToOne
	private AircraftTypeEntity type;
	private String code;
}
