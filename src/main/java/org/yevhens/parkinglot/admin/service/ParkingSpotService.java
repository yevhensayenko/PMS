package org.yevhens.parkinglot.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yevhens.parkinglot.admin.dto.ChangeAvailabilityDto;
import org.yevhens.parkinglot.admin.dto.ParkingSpotCreateRequest;
import org.yevhens.parkinglot.entity.ParkingLevel;
import org.yevhens.parkinglot.entity.ParkingSpot;
import org.yevhens.parkinglot.entity.ParkingSpotDto;
import org.yevhens.parkinglot.entity.embeddable.ParkingLevelId;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;
import org.yevhens.parkinglot.exception.ResourceNotFoundException;
import org.yevhens.parkinglot.repository.ParkingLevelRepository;
import org.yevhens.parkinglot.repository.ParkingSpotRepository;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingLevelRepository parkingLevelRepository;
    private final ParkingSpotRepository parkingSpotRepository;


    public ParkingSpotDto registerParkingSpot(Long parkingLotId, Integer level, ParkingSpotCreateRequest dto) {

        ParkingLevel parkingLevel = parkingLevelRepository.findById(new ParkingLevelId(parkingLotId, level))
                .orElseThrow(ResourceNotFoundException::new);

        final var parkingSpot = ParkingSpot.builder()
                .id(new ParkingSpotId(parkingLotId, level, dto.parkingSpotNo()))
                .type(dto.type())
                .parkingLevel(parkingLevel)
                .build();

        return ParkingSpotDto.fromEntity(parkingSpotRepository.save(parkingSpot));
    }

    public void deleteParkingSpot(Long parkingLotId, Integer level, Integer parkingSpotNo) {
        parkingSpotRepository.deleteById(new ParkingSpotId(parkingLotId, level, parkingSpotNo));
    }

    public void changeAvailability(Long parkingLotId, Integer level, Integer parkingSpotNo, ChangeAvailabilityDto dto) {
        parkingSpotRepository.updateAvailability(new ParkingSpotId(parkingLotId, level, parkingSpotNo), dto.isAvailable());
    }
}
