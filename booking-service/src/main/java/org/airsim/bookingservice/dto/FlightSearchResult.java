package org.airsim.bookingservice.dto;

import java.util.List;

import lombok.Value;

@Value
public class FlightSearchResult {
	
	private final List<Flight> elements;

}
