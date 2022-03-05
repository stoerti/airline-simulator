package org.airsim.bookingservice.dto;

import java.util.List;

import lombok.Value;

@Value
public class AircraftTypeSearchResult {
	
	private final List<AircraftType> elements;

	private final int page;
	private final int pagesize;
	private final long totalElements;

}
