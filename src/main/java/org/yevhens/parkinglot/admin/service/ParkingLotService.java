package org.yevhens.parkinglot.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yevhens.parkinglot.admin.dto.ParkingLotDto;
import org.yevhens.parkinglot.entity.ParkingLot;
import org.yevhens.parkinglot.repository.ParkingLotRepository;

@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;


    public void registerParkingLot(ParkingLotDto dto) {
        final var parkingLot = ParkingLot.builder()
                .name(dto.name())
                .levelCount(dto.levelCount())
                .build();
        parkingLotRepository.save(parkingLot);
    }

    public void deleteParkingLot(Long id) {
        parkingLotRepository.deleteById(id);
    }
}
