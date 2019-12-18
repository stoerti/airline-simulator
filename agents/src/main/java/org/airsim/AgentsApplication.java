package org.airsim;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.spring.eventhandling.scheduling.java.SimpleEventSchedulerFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
@EnableScheduling
public class AgentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentsApplication.class, args);
	}


	@Bean
		public SimpleEventSchedulerFactoryBean eventScheduler(EventBus eventBus,
				PlatformTransactionManager transactionManager) {
			SimpleEventSchedulerFactoryBean eventSchedulerFactory = new SimpleEventSchedulerFactoryBean();
			eventSchedulerFactory.setEventBus(eventBus);
			eventSchedulerFactory.setTransactionManager(transactionManager);
			return eventSchedulerFactory;
		}

}
