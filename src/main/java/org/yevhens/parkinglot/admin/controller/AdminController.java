package org.yevhens.parkinglot.admin.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.yevhens.parkinglot.admin.dto.ChangeAvailabilityDto;
import org.yevhens.parkinglot.admin.dto.ParkingLevelCreateRequest;
import org.yevhens.parkinglot.admin.dto.ParkingLevelDto;
import org.yevhens.parkinglot.admin.dto.ParkingLotCreateRequest;
import org.yevhens.parkinglot.admin.dto.ParkingLotDto;
import org.yevhens.parkinglot.admin.dto.ParkingSpotCreateRequest;
import org.yevhens.parkinglot.admin.service.ParkingLevelService;
import org.yevhens.parkinglot.admin.service.ParkingLotService;
import org.yevhens.parkinglot.admin.service.ParkingSpotService;
import org.yevhens.parkinglot.config.validation.ApiErrorResponse;
import org.yevhens.parkinglot.entity.ParkingSpotDto;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Validated
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
public class AdminController {

    private final ParkingLotService parkingLotService;
    private final ParkingLevelService parkingLevelService;
    private final ParkingSpotService parkingSpotService;

    @PostMapping("/parking-lots")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingLotDto registerParkingLot(@Valid @RequestBody ParkingLotCreateRequest request) {
        return parkingLotService.registerParkingLot(request);
    }

    @DeleteMapping("/parking-lots/{parkingLotId}")
    public void deleteParkingLot(@PathVariable @Min(1) Long parkingLotId) {
        parkingLotService.deleteParkingLot(parkingLotId);
    }

    @PostMapping("/parking-lots/{parkingLotId}/levels")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingLevelDto addParkingLevel(@PathVariable @Min(1) Long parkingLotId,
                                           @Valid @RequestBody ParkingLevelCreateRequest request) {
        return parkingLevelService.addParkingLevel(parkingLotId, request);
    }

    @DeleteMapping("/parking-lots/{parkingLotId}/levels/{level}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParkingLevel(@PathVariable @Min(1) Long parkingLotId,
                                   @PathVariable Integer level) {
        parkingLevelService.deleteParkingLevel(parkingLotId, level);
    }

    @PostMapping("/parking-lots/{parkingLotId}/levels/{level}/parking-spots")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingSpotDto registerParkingSpot(@PathVariable @Min(1) Long parkingLotId,
                                              @PathVariable @Min(1) Integer level,
                                              @Valid @RequestBody ParkingSpotCreateRequest parkingSpotDto) {
        return parkingSpotService.registerParkingSpot(parkingLotId, level, parkingSpotDto);
    }

    @DeleteMapping("/parking-lots/{parkingLotId}/levels/{level}/parking-spots/{parkingSpotNo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParkingSpot(@PathVariable @Min(1) Long parkingLotId,
                                  @PathVariable @Min(1) Integer level,
                                  @PathVariable Integer parkingSpotNo) {
        parkingSpotService.deleteParkingSpot(parkingLotId, level, parkingSpotNo);
    }

    @PatchMapping("/parking-lots/{parkingLotId}/levels/{level}/parking-spots/{parkingSpotNo}")
    @ResponseStatus(HttpStatus.OK)
    public void changeAvailability(@PathVariable @Min(1) Long parkingLotId,
                                   @PathVariable @Min(1) Integer level,
                                   @PathVariable Integer parkingSpotNo,
                                   @Valid @RequestBody ChangeAvailabilityDto dto) {
        parkingSpotService.changeAvailability(parkingLotId, level, parkingSpotNo, dto);
    }

}
