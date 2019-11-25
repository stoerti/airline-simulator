package org.airsim.flighttracker.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.airsim.api.flight.FlightStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
	
	private UUID id;
	
	private String flightNumber;
	private LocalDateTime takeoffTime;
	private Duration duration;
	private String airportFrom;
	private String airportTo;
	private String aircraftTypeCode;
	private FlightStatus flightStatus;
}
