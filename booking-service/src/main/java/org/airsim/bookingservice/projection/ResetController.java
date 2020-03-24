package org.airsim.bookingservice.projection;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ResetController {

	private final EventProcessingConfiguration eventProcessingConfiguration;

	@GetMapping(value = "/admin/projections/reset")
	public void resetProjections() {
		eventProcessingConfiguration
			.eventProcessorByProcessingGroup("org.airsim.bookingservice.projection", TrackingEventProcessor.class)
			.ifPresent(trackingEventProcessor -> {
				trackingEventProcessor.shutDown();
				trackingEventProcessor.resetTokens(); // (1)
				trackingEventProcessor.start();
			});
	}

}
