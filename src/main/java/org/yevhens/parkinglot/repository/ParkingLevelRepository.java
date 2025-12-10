package org.yevhens.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yevhens.parkinglot.entity.ParkingLevel;
import org.yevhens.parkinglot.entity.embeddable.ParkingLevelId;

public interface ParkingLevelRepository extends JpaRepository<ParkingLevel, ParkingLevelId> {
}