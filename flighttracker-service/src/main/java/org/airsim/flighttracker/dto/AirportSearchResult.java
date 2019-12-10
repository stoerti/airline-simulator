package org.airsim.flighttracker.dto;

import java.util.List;

import lombok.Value;

@Value
public class AirportSearchResult {
	
	private final List<Airport> elements;

	private final int page;
	private final int pagesize;
	private final long totalElements;

}
