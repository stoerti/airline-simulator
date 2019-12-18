package org.airsim.agents.projection.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "AG_FLIGHTPLAN")
public class FlightplanEntity {

	@Id
	private UUID id;
	
	private String flightnumber;
	private String airportFrom;
	private String airportTo;
	private LocalTime takeoffTime;
	private Duration duration;

}
