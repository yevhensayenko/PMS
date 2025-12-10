package org.yevhens.parkinglot.admin.controller;

import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @PostMapping("/parking-lots")
    public void registerParkingLot(@Valid @RequestBody ParkingLotDto parkingLotDto) {

    }

    @DeleteMapping("/parking-lots/{parkingLotId}")
    public void deleteParkingLot(@PathVariable Long parkingLotId) {

    }

    @PostMapping("/parking-spots")
    public void registerParkingSpot(@Valid @RequestBody ParkingSpotDto parkingSpotDto) {

    }

    @DeleteMapping("/parking-spots/{parkingSpotId}")
    public void deleteParkingSpot(@PathVariable Long parkingSpotId) {

    }

    @PatchMapping("/parking-spots/{parkingSpotId}")
    public void changeAvailability(@PathVariable Long parkingSpotId, @Valid @RequestBody ChangeAvailabilityDto dto) {

    }

}
