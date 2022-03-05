package org.airsim.flighttracker.projection.jpa;

import java.time.Duration;
import java.time.LocalTime;
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
@Table(name = "TR_FLIGHTPLAN")
public class FlightplanEntity {

	@Id
	private UUID id;
	
	private String flightnumber;
	private String airportFrom;
	private String airportTo;
	private String aircraftType;
	private LocalTime takeoffTime;
	private Duration duration;

}
