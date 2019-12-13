package org.airsim.bookingservice.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftType {

	private UUID id;
	private String code;
	private String name;
	private int seats;

	private int legroom;
	private boolean hasWiFi;
	private boolean hasSeatPower;
	private boolean hasEntertainment;
}
