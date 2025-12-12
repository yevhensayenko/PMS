package org.yevhens.parkinglot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yevhens.parkinglot.admin.dto.ChangeAvailabilityDto;
import org.yevhens.parkinglot.admin.dto.ParkingSpotCreateRequest;
import org.yevhens.parkinglot.entity.ParkingLevel;
import org.yevhens.parkinglot.entity.ParkingSpotDto;
import org.yevhens.parkinglot.entity.embeddable.ParkingLevelId;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;
import org.yevhens.parkinglot.exception.ResourceNotFoundException;
import org.yevhens.parkinglot.repository.ParkingLevelRepository;
import org.yevhens.parkinglot.repository.ParkingSpotRepository;
import org.yevhens.parkinglot.util.ParkingSpotFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingLevelRepository parkingLevelRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    public List<ParkingSpotDto> getAllParkingSpots(Long parkingLotId, Integer level) {
        return parkingSpotRepository.findAllByParkingLevelId(new ParkingLevelId(parkingLotId, level))
                .stream()
                .map(ParkingSpotDto::fromEntity)
                .toList();
    }

    public ParkingSpotDto registerParkingSpot(Long parkingLotId, Integer level, ParkingSpotCreateRequest dto) {

        ParkingLevel parkingLevel = parkingLevelRepository.findById(new ParkingLevelId(parkingLotId, level))
                .orElseThrow(ResourceNotFoundException::new);

        final var parkingSpot = ParkingSpotFactory.create(dto.type(), parkingLevel, dto.parkingSpotNo(), dto.available());
        return ParkingSpotDto.fromEntity(parkingSpotRepository.save(parkingSpot));
    }

    public void deleteParkingSpot(Long parkingLotId, Integer level, Integer parkingSpotNo) {
        parkingSpotRepository.deleteById(new ParkingSpotId(parkingLotId, level, parkingSpotNo));
    }

    @Transactional
    public void changeAvailability(Long parkingLotId, Integer level, Integer parkingSpotNo, ChangeAvailabilityDto dto) {
        int updatedCount = parkingSpotRepository.updateAvailability(new ParkingSpotId(parkingLotId, level, parkingSpotNo), dto.isAvailable());
        if (updatedCount == 0) {
            throw new ResourceNotFoundException();
        }
    }
}
