package org.airsim.configuration;

import org.airsim.agents.BookingAgent;
import org.airsim.agents.jobs.CompleteCheckinJob;
import org.airsim.agents.jobs.StartCheckinJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

	@Bean
	public JobDetail bookingJobDetail() {
		return JobBuilder
			.newJob()
			.ofType(BookingAgent.class)
			.storeDurably()
			.withIdentity("BookingAgent")
			.withDescription("Invoke BookingAgent...")
			.build();
	}

	@Bean
	public Trigger bookingJobTrigger(JobDetail bookingJobDetail) {
		return TriggerBuilder
			.newTrigger()
			.forJob(bookingJobDetail)
			.withIdentity("Qrtz_Trigger")
			.withDescription("Sample trigger")
			.withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(5))
			.build();
	}

	@Bean
	public JobDetail startCheckinJobDetail() {
		return JobBuilder
			.newJob()
			.ofType(StartCheckinJob.class)
			.storeDurably()
			.withIdentity("StartCheckinJob")
			.withDescription("Invoke StartCheckinJob service...")
			.build();
	}

	@Bean
	public JobDetail completeCheckinJobDetail() {
		return JobBuilder
			.newJob()
			.ofType(CompleteCheckinJob.class)
			.storeDurably()
			.withIdentity("CompleteCheckinJob")
			.withDescription("Invoke CompleteCheckinJob service...")
			.build();
	}
		
//	@Bean
//	public Scheduler jobScheduler(Trigger trigger, JobDetail bookingJobDetail, JobDetail startCheckinJobDetail, SchedulerFactoryBean factory)
//			throws SchedulerException {
//		Scheduler scheduler = factory.getScheduler();
//		scheduler.scheduleJob(trigger);
//		scheduler.addJob(startCheckinJobDetail, false);
//		scheduler.start();
//		return scheduler;
//	}
}
