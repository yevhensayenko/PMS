package org.yevhens.parkinglot.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.yevhens.parkinglot.admin.dto.ParkingLevelDto;
import org.yevhens.parkinglot.admin.dto.ParkingLotDto;
import org.yevhens.parkinglot.config.validation.ApiErrorResponse;
import org.yevhens.parkinglot.entity.ParkingSpotDto;
import org.yevhens.parkinglot.service.ParkingLevelService;
import org.yevhens.parkinglot.service.ParkingLotService;
import org.yevhens.parkinglot.service.ParkingSpotService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@ApiResponse(
        responseCode = "400",
        description = "Validation error",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
)
@ApiResponse(
        responseCode = "404",
        description = "Not found",
        content = @Content(mediaType = "application/json")
)
public class ParkingLotController {

    private final ParkingLotService parkingLotService;
    private final ParkingLevelService parkingLevelService;
    private final ParkingSpotService parkingSpotService;

    @GetMapping("/parking-lots")
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingLotDto> getAllParkingLots() {
        return parkingLotService.getAllParkingLots();
    }

    @GetMapping("/parking-lots/{parkingLotId}/levels")
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingLevelDto> getAllParkingLevels(@PathVariable @Min(1) Long parkingLotId) {
        return parkingLevelService.getAllParkingLevels(parkingLotId);
    }

    @GetMapping("/parking-lots/{parkingLotId}/levels/{level}/parking-spots")
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingSpotDto> getAllParkingSpots(@PathVariable @Min(1) Long parkingLotId,
                                                   @PathVariable @Min(1) Integer level) {
        return parkingSpotService.getAllParkingSpots(parkingLotId, level);
    }

}
