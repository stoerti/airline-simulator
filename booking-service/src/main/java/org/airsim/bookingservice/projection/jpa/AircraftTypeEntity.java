package org.airsim.bookingservice.projection.jpa;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "BO_AIRCRAFT_TYPE")
public class AircraftTypeEntity {

	@Id
	private UUID id;

	private String code;
	private String name;

	private int legroom;
	private boolean hasWiFi;
	private boolean hasSeatPower;
	private boolean hasEntertainment;
}
