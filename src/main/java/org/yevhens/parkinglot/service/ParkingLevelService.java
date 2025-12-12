package org.yevhens.parkinglot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yevhens.parkinglot.admin.dto.ParkingLevelCreateRequest;
import org.yevhens.parkinglot.admin.dto.ParkingLevelDto;
import org.yevhens.parkinglot.entity.ParkingLevel;
import org.yevhens.parkinglot.entity.ParkingLot;
import org.yevhens.parkinglot.entity.embeddable.ParkingLevelId;
import org.yevhens.parkinglot.exception.ResourceNotFoundException;
import org.yevhens.parkinglot.repository.ParkingLevelRepository;
import org.yevhens.parkinglot.repository.ParkingLotRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLevelService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLevelRepository parkingLevelRepository;

    public List<ParkingLevelDto> getAllParkingLevels(Long parkingLotId) {
        return parkingLevelRepository.findAllByParkingLotId(parkingLotId)
                .stream()
                .map(ParkingLevelDto::fromEntity)
                .toList();
    }

    public ParkingLevelDto addParkingLevel(Long parkingLotId, ParkingLevelCreateRequest dto) {

        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(ResourceNotFoundException::new);

        ParkingLevel parkingLevel = parkingLevelRepository.save(
                ParkingLevel.builder()
                        .id(new ParkingLevelId(parkingLotId, dto.level()))
                        .parkingLot(parkingLot)
                        .build());

        return ParkingLevelDto.fromEntity(parkingLevel);
    }

    public void deleteParkingLevel(Long lotId, Integer level) {
        parkingLevelRepository.deleteById(new ParkingLevelId(lotId, level));
    }
}
