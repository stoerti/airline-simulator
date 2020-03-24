package org.airsim.bookingservice.projection.jpa;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.airsim.bookingservice.projection.jpa.BookingEntity.BookingEntityBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "BO_BOOKING_PASSENGER")
public class BookingPassengerEntity {

	@Id
	private UUID id;
	
	private UUID customerId;
	
	private String name;
	private String lastname;
	
	private String emailAddress;

}