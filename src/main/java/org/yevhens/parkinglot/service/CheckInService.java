package org.yevhens.parkinglot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yevhens.parkinglot.entity.ParkingLot;
import org.yevhens.parkinglot.entity.ParkingSession;
import org.yevhens.parkinglot.entity.ParkingSpotDto;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;
import org.yevhens.parkinglot.entity.vehicle.Vehicle;
import org.yevhens.parkinglot.exception.NoAvailableSpotsException;
import org.yevhens.parkinglot.exception.ResourceNotFoundException;
import org.yevhens.parkinglot.model.CheckInDto;
import org.yevhens.parkinglot.model.Receipt;
import org.yevhens.parkinglot.model.VehicleDto;
import org.yevhens.parkinglot.repository.ParkingLotRepository;
import org.yevhens.parkinglot.repository.ParkingSessionRepository;
import org.yevhens.parkinglot.repository.VehicleRepository;
import org.yevhens.parkinglot.util.VehicleFactory;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final ParkingLotRepository parkingLotRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSessionRepository parkingSessionRepository;
    private final SpotAssignmentService spotAssignmentService;

    @Transactional
    public Receipt checkIn(CheckInDto checkInDto) {

        final ParkingLot parkingLot = parkingLotRepository.findById(checkInDto.parkingLotId())
                .orElseThrow(ResourceNotFoundException::new);

        final Vehicle vehicle = upsertVehicle(checkInDto);

        final ParkingSpot parkingSpot = spotAssignmentService.assignSpot(vehicle, parkingLot)
                .orElseThrow(NoAvailableSpotsException::new);

        final ParkingSession savedSession = createParkingSession(parkingSpot, vehicle);

        return Receipt.builder()
                .id(savedSession.getId())
                .startDateTime(savedSession.getStartDateTime())
                .vehicle(VehicleDto.fromDto(vehicle))
                .parkingSpot(ParkingSpotDto.fromEntity(parkingSpot))
                .build();
    }

    private Vehicle upsertVehicle(CheckInDto checkInDto) {
        return vehicleRepository.findById(checkInDto.licensePlate())
                .orElseGet(() -> {
                    Vehicle vehicle = VehicleFactory.create(checkInDto.vehicleType(), checkInDto.licensePlate(), checkInDto.handicapped());
                    return vehicleRepository.save(vehicle);
                });
    }

    private ParkingSession createParkingSession(ParkingSpot parkingSpot, Vehicle vehicle) {
        final var session = ParkingSession.builder()
                .parkingSpot(parkingSpot)
                .startDateTime(Instant.now())
                .vehicle(vehicle)
                .build();

        return parkingSessionRepository.save(session);
    }
}
