package org.yevhens.parkinglot.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yevhens.parkinglot.admin.dto.ChangeAvailabilityDto;
import org.yevhens.parkinglot.admin.dto.ParkingSpotDto;
import org.yevhens.parkinglot.entity.ParkingSpot;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;
import org.yevhens.parkinglot.repository.ParkingSpotRepository;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;


    public void registerParkingSpot(ParkingSpotDto dto) {
        final var parkingSpot = ParkingSpot.builder()
                .id(new ParkingSpotId(dto.parkingLotId(), dto.parkingSpotNo()))
                .level(dto.level())
                .type(dto.type())
                .build();
        parkingSpotRepository.save(parkingSpot);
    }

    public void deleteParkingSpot(Long parkingLotId, Integer parkingSpotNo) {
        parkingSpotRepository.deleteById(new ParkingSpotId(parkingLotId, parkingSpotNo));
    }

    public void changeAvailability(Long parkingLotId, Integer parkingSpotNo, ChangeAvailabilityDto dto) {
        parkingSpotRepository.updateAvailability(new ParkingSpotId(parkingLotId, parkingSpotNo), dto.isAvailable());
    }
}
