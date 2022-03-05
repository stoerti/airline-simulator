package org.airsim.flighttracker.dto;

import java.util.List;

import lombok.Value;

@Value
public class FlightSearchResult {
	
	private final List<Flight> elements;

	private final int page;
	private final int pagesize;
	private final long totalElements;

}
