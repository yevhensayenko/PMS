package org.yevhens.parkinglot.model;

import lombok.Builder;
import org.yevhens.parkinglot.entity.ParkingSpotDto;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link org.yevhens.parkinglot.entity.ParkingSession}
 */
@Builder
public record Receipt(Long id, VehicleDto vehicle, ParkingSpotDto parkingSpot, Instant startDateTime) implements Serializable {
}