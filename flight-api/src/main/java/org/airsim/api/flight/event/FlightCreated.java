package org.airsim.api.flight.event;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.airsim.api.flight.FlightStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightCreated {
	
	private UUID id;
	private UUID flightplanId;
	private LocalDateTime takeoffTime;
	private Duration duration;
	private FlightStatus flightStatus;

}
