package org.airsim.api.flight.command;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

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
public class CreateFlightCommand {
	
	@TargetAggregateIdentifier
	private UUID id;
	
	private UUID flightplanId;
	private LocalDateTime takeoffTime;
	private Duration duration;

	private int seatsAvailable;

}
