package org.airsim.flighttracker.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.airsim.api.flight.FlightStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime takeoffTime;
	
	private Duration duration;
	private Airport airportFrom;
	private Airport airportTo;
	private AircraftType aircraftType;
	private FlightStatus flightStatus;
	private int seatsAvailable;
	private int seatsTaken;
	
	private double positionLatitude;
	private double positionLongitude;
}
