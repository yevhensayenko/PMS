package org.yevhens.parkinglot.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yevhens.parkinglot.admin.dto.ChangeAvailabilityDto;
import org.yevhens.parkinglot.admin.dto.ParkingLotDto;
import org.yevhens.parkinglot.admin.dto.ParkingSpotDto;
import org.yevhens.parkinglot.admin.service.ParkingLotService;
import org.yevhens.parkinglot.admin.service.ParkingSpotService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ParkingLotService parkingLotService;
    private final ParkingSpotService parkingSpotService;

    @PostMapping("/parking-lots")
    public void registerParkingLot(@Valid @RequestBody ParkingLotDto parkingLotDto) {
        parkingLotService.registerParkingLot(parkingLotDto);
    }

    @DeleteMapping("/parking-lots/{parkingLotId}")
    public void deleteParkingLot(@PathVariable Long parkingLotId) {
        parkingLotService.deleteParkingLot(parkingLotId);
    }

    @PostMapping("/parking-lots/{parkingLotId}/parking-spots")
    public void registerParkingSpot(@Valid @RequestBody ParkingSpotDto parkingSpotDto) {
        parkingSpotService.registerParkingSpot(parkingSpotDto);
    }

    @DeleteMapping("/parking-lots/{parkingLotId}/parking-spots/{parkingSpotNo}")
    public void deleteParkingSpot(@PathVariable Long parkingLotId, @PathVariable Integer parkingSpotNo) {
        parkingSpotService.deleteParkingSpot(parkingLotId, parkingSpotNo);
    }

    @PatchMapping("/parking-lots/{parkingLotId}/parking-spots/{parkingSpotNo}")
    public void changeAvailability(@PathVariable Long parkingLotId, @PathVariable Integer parkingSpotNo, @Valid @RequestBody ChangeAvailabilityDto dto) {
        parkingSpotService.changeAvailability(parkingLotId, parkingSpotNo, dto);
    }

}
