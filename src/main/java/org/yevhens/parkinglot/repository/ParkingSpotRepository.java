package org.yevhens.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yevhens.parkinglot.entity.ParkingSpot;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, ParkingSpotId> {
}