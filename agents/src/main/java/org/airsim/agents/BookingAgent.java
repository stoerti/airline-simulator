package org.airsim.agents;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.airsim.agents.projection.jpa.AirportEntity;
import org.airsim.agents.projection.jpa.AirportRepository;
import org.airsim.agents.projection.jpa.CustomerEntity;
import org.airsim.agents.projection.jpa.CustomerRepository;
import org.airsim.agents.projection.jpa.FlightEntity;
import org.airsim.agents.projection.jpa.FlightRepository;
import org.airsim.api.booking.Passenger;
import org.airsim.api.booking.command.CreateBookingCommand;
import org.airsim.api.booking.command.FlightBooking;
import org.airsim.api.flight.command.CompleteBoarding;
import org.airsim.api.flight.command.CompleteCheckIn;
import org.airsim.api.flight.command.CompleteFlight;
import org.airsim.api.flight.command.StartBoarding;
import org.airsim.api.flight.command.StartCheckIn;
import org.airsim.api.flight.command.StartFlight;
import org.airsim.api.flight.event.FlightCreated;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class BookingAgent {

	private final CustomerRepository customerRepository;
	private final AirportRepository airportRepository;
	private final FlightRepository flightRepository;

	private final CommandGateway commandGateway;

	// all times relevant to start time
	private static final long NUMBER_OF_BOOKINGS_PER_CYCLE = 20;

	@Scheduled(fixedRate = 5000)
	public void scheduleBookings() {
		List<CustomerEntity> customers = new ArrayList<>();
		customerRepository.findAll().forEach(c -> customers.add(c));

		List<AirportEntity> airports = new ArrayList<>();
		airportRepository.findAll().forEach(a -> airports.add(a));

		if (customers.isEmpty())
			return;
		if (airports.isEmpty())
			return;

		for (int i = 0;i < NUMBER_OF_BOOKINGS_PER_CYCLE; i++) {
			CustomerEntity customer = customers.get(new Random().nextInt(customers.size()));

			AirportEntity airportFrom = airports.get(new Random().nextInt(airports.size()));
			AirportEntity airportTo = airports.get(new Random().nextInt(airports.size()));
			if (airportFrom.getId().equals(airportTo.getId())) {
				// fast hack to prevent from and to to be equal
				i--;
				continue;
			}

			createRandomBooking(customer, airportFrom, airportTo);
		}
	}

	private void createRandomBooking(CustomerEntity customer, AirportEntity airportFrom, AirportEntity airportTo) {
		LocalDate futureDay = LocalDate.now().plusDays(new Random().nextInt(7) + 1); // somewhen in the following next 7 days
		LocalDate futureReturnDay = futureDay.plusDays(new Random().nextInt(2) + 6); // return flight in 6-8 days after first flight

		List<FlightEntity> flights = findFlights(airportFrom.getIataCode(), airportTo.getIataCode(), futureDay);
		List<FlightEntity> returnFlights = findFlights(airportTo.getIataCode(), airportFrom.getIataCode(), futureReturnDay);

		if (flights.isEmpty()) {
			log.info("No flight found from {} to {} at {}", airportFrom.getIataCode(), airportTo.getIataCode(), futureDay);
			return;
		}

		if (returnFlights.isEmpty()) {
			log.info("No flight found from {} to {} at {}", airportTo.getIataCode(), airportFrom.getIataCode(), futureReturnDay);
			return;
		}

		CreateBookingCommand command = CreateBookingCommand.builder()
				.bookingId(UUID.randomUUID())
				.customerId(customer.getId())
				.flightBooking(FlightBooking.builder()
						.flightBookingId(UUID.randomUUID())
						.flightId(flights.get(0).getId())
						.passenger(Passenger.builder()
								.id(customer.getId())
								.name(customer.getName())
								.lastname(customer.getLastName())
								.build())
						.build())
				.flightBooking(FlightBooking.builder()
						.flightBookingId(UUID.randomUUID())
						.flightId(returnFlights.get(0).getId())
						.passenger(Passenger.builder()
								.id(customer.getId())
								.name(customer.getName())
								.lastname(customer.getLastName())
								.build())
						.build())
				.build();

		commandGateway.send(command);
	}

	private List<FlightEntity> findFlights(String airportFrom, String airportTo, LocalDate flightDay) {
		LocalDateTime minTakeoffTime = flightDay.atStartOfDay();
		LocalDateTime maxTakeoffTime = minTakeoffTime.plusDays(1);

		List<FlightEntity> flights = flightRepository.findByAirportFromAndAirportToAndTakeoffTimeBetween(airportFrom, airportTo, minTakeoffTime, maxTakeoffTime)
				.stream()
				.filter(flightEntity ->
						flightEntity.getBookable() &&
								flightEntity.getSeatsTaken() < flightEntity.getSeatsAvailable())
				.collect(Collectors.toList());

		Collections.sort(flights, new Comparator<FlightEntity>() {
			@Override
			public int compare(FlightEntity o1, FlightEntity o2) {
				if (o1.getTakeoffTime().equals(o2.getTakeoffTime()))
					return 0;
				return o1.getTakeoffTime().isBefore(o2.getTakeoffTime()) ? -1 : 1;
			}
		});

		return flights;
	}

}
