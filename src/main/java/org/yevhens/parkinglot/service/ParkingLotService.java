package org.yevhens.parkinglot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yevhens.parkinglot.admin.dto.ParkingLotCreateRequest;
import org.yevhens.parkinglot.admin.dto.ParkingLotDto;
import org.yevhens.parkinglot.entity.ParkingLot;
import org.yevhens.parkinglot.repository.ParkingLotRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    public List<ParkingLotDto> getAllParkingLots() {
        return parkingLotRepository.findAll()
                .stream()
                .map(ParkingLotDto::fromEntity)
                .toList();
    }

    public ParkingLotDto registerParkingLot(ParkingLotCreateRequest dto) {
        ParkingLot parkingLot = parkingLotRepository.save(ParkingLot.builder()
                .name(dto.name())
                .build());

        return ParkingLotDto.fromEntity(parkingLot);
    }

    public void deleteParkingLot(Long id) {
        parkingLotRepository.deleteById(id);
    }
}
