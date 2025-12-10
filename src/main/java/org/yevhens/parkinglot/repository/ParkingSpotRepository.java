package org.yevhens.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.yevhens.parkinglot.entity.ParkingSpot;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, ParkingSpotId> {

    @Modifying
    @Query("update ParkingSpot set available = :available where id = :id")
    void updateAvailability(ParkingSpotId id, boolean available);
}