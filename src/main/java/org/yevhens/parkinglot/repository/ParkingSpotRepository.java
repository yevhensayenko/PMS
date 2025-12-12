package org.yevhens.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.yevhens.parkinglot.entity.embeddable.ParkingLevelId;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;
import org.yevhens.parkinglot.entity.spot.CompactSpot;
import org.yevhens.parkinglot.entity.spot.HandicappedSpot;
import org.yevhens.parkinglot.entity.spot.LargeSpot;
import org.yevhens.parkinglot.entity.spot.MotorcycleSpot;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;

import java.util.Collection;
import java.util.List;

// TODO: JPQL does not support "find first" queries, while it is not trivial to implement it using JPA query methods
//  so, the query returns the whole avaialble places list, that is not optimal.
//  Need to find better approach here (split repo to separate repos per each parkin spot?)
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, ParkingSpotId> {

    @Modifying
    @Query("update ParkingSpot set available = :available where id = :id")
    int updateAvailability(ParkingSpotId id, boolean available);

    @Query("select p from ParkingSpot p where p.parkingLevel.parkingLot.id = ?1 and p.available = true and type(p) = MotorcycleSpot")
    List<MotorcycleSpot> findAvailableMotorcycleSpots(Long parkingLotId);

    @Query("select p from ParkingSpot p where p.parkingLevel.parkingLot.id = ?1 and p.available = true and type(p) = LargeSpot")
    List<LargeSpot> findAvailableLargeSpots(Long parkingLotId);

    @Query("select p from ParkingSpot p where p.parkingLevel.parkingLot.id = ?1 and p.available = true and type(p) = CompactSpot")
    List<CompactSpot> findAvailableCompactSpots(Long parkingLotId);

    @Query("select p from ParkingSpot p where p.parkingLevel.parkingLot.id = ?1 and p.available = true and type(p) = HandicappedSpot ")
    List<HandicappedSpot> findAvailableHandicappedSpots(Long parkingLotId);

    Collection<ParkingSpot> findAllByParkingLevelId(ParkingLevelId parkingLevelId);
}