package org.airsim.agents.projection.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "AG_FLIGHT")
public class FlightEntity {
	
	@Id
	private UUID id;
	
	private String flightNumber;
	private LocalDateTime takeoffTime;
	private Duration duration;
	private String airportFrom;
	private String airportTo;
	private Integer seatsAvailable;
	private Integer seatsTaken;
	private Boolean bookable;
}
