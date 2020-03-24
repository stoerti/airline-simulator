package org.airsim.agents.jobs;

import java.util.UUID;

import org.airsim.agents.FlightOperatorAgent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
public class StartCheckinJob implements Job {
	
	private final FlightOperatorAgent flightOperatorAgent;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		flightOperatorAgent.startCheckin(UUID.fromString(context.get("flightId").toString()));
	}

}
