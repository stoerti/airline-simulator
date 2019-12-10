package org.airsim.domain.booking;

import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;
import org.junit.Test;

public class BookFlightSagaTest {
	private SagaTestFixture<BookFlightSaga> fixture;

    @Before
    public void setUp() {
    	this.fixture = new SagaTestFixture<>(BookFlightSaga.class);
    }
    
    @Test
    public void startBookingWithTwoFlights() {

    }
}
