package org.airsim.bookingservice.projection.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "BO_BOOKING")
public class BookingEntity {

	@Id
	private UUID id;
	private UUID customerId;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BookingFlightEntity> flights;
}
