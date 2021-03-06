package org.airsim.api.flightplan;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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
public class CreateFlightplanCommand {
	
	@TargetAggregateIdentifier
	private UUID id;

	private String flightnumber;
	private String airportFrom;
	private String airportTo;
	private String aircraftType;
	private LocalTime takeoffTime;
	private Duration duration;
	private LocalDate validFrom;
	private LocalDate validTo;
	private Weekplan weekplan;

}
