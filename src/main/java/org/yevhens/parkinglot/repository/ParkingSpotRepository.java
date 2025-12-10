package org.yevhens.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.yevhens.parkinglot.entity.ParkingSpot;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;
import org.yevhens.parkinglot.model.ParkingSpotType;

import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, ParkingSpotId> {

    @Modifying
    @Query("update ParkingSpot set available = :available where id = :id")
    void updateAvailability(ParkingSpotId id, boolean available);

    Optional<ParkingSpot> findByParkingLotIdAndAvailableTrueAndType(Long parkingLotId, ParkingSpotType type);
}