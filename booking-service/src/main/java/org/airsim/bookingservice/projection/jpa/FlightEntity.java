package org.airsim.bookingservice.projection.jpa;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "BO_FLIGHT")
public class FlightEntity {
	
	@Id
	private UUID id;
	
	private String flightNumber;
	private LocalDateTime takeoffTime;
	private Duration duration;
	private String airportFrom;
	private String airportTo;
	private String aircraftTypeCode;
	private Integer seatsAvailable;
	private Integer seatsTaken;
	private Boolean bookable;
}
