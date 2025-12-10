package org.yevhens.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yevhens.parkinglot.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}