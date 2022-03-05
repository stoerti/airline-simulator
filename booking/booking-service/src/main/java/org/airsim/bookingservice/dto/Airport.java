package org.airsim.bookingservice.dto;

import java.util.UUID;

import org.gavaghan.geodesy.GlobalPosition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {
	
	private UUID id;
	
	private String iataCode;
	private String name;
	private String fullName;
	private String city;
	private GlobalPosition location;
}
