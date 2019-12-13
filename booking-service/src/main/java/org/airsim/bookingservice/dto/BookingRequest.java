package org.airsim.bookingservice.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BookingRequest {
	
	private UUID customerUuid;
	private List<UUID> flightUuids;

}
