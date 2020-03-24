package org.airsim.bookingservice.projection.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.airsim.api.booking.FlightBookingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "BO_BOOKING_FLIGHT")
public class BookingFlightEntity {

	@Id
	private UUID id;
	
	private UUID flightId;
	
	private FlightBookingStatus status;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BookingPassengerEntity> passengers;

}