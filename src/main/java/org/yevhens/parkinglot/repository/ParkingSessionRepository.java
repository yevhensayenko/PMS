package org.yevhens.parkinglot.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yevhens.parkinglot.entity.ParkingSession;

import java.util.Optional;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {

    @EntityGraph("ParkingSession.vehicle")
    Optional<ParkingSession> findByIdAndFinishDateTimeNull(Long id);
}