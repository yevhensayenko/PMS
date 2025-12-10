package org.yevhens.parkinglot.admin.service;

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

@Service
@RequiredArgsConstructor
public class ParkingLevelService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLevelRepository parkingLevelRepository;


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
