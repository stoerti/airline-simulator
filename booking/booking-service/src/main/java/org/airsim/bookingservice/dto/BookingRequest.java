package org.airsim.bookingservice.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
	
	private UUID customerUuid;
	private List<UUID> flightUuids;

}
