package org.airsim.bookingservice.dto;

import java.util.List;

import lombok.Value;

@Value
public class AirportSearchResult {
	
	private final List<Airport> elements;
}
