package org.yevhens.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yevhens.parkinglot.entity.ParkingLot;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

}