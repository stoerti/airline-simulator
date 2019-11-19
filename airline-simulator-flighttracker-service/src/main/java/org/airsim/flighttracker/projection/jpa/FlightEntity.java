package org.airsim.flighttracker.projection.jpa;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.airsim.api.flight.FlightStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightEntity {
	
	@Id
	private UUID id;
	
	private String flightNumber;
	private LocalDateTime takeoffTime;
	private LocalDateTime landingTime;
	private String airportFrom;
	private String airportTo;
	private String aircraftTypeCode;
	private FlightStatus flightStatus;
}
