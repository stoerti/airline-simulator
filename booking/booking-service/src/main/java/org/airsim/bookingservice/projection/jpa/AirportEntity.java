package org.airsim.bookingservice.projection.jpa;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gavaghan.geodesy.GlobalPosition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "BO_AIRPORT")
public class AirportEntity {

	@Id
	private UUID id;

	private String iataCode;
	private String name;
	private String city;
	private GlobalPosition location;
	
}
