package org.airsim.projection.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gavaghan.geodesy.GlobalPosition;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "RU_AIRPORT")
public class AirportEntity {

	@Id
	private UUID id;

	private String iataCode;
}
