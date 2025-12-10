package org.yevhens.parkinglot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.yevhens.parkinglot.model.CheckInDto;
import org.yevhens.parkinglot.model.CheckOutDto;
import org.yevhens.parkinglot.model.CheckOutResponse;
import org.yevhens.parkinglot.model.Receipt;
import org.yevhens.parkinglot.service.CheckInService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @PostMapping("/check-in")
    @ResponseStatus(HttpStatus.CREATED)
    public Receipt checkIn(@Valid @RequestBody CheckInDto checkInDto) {
        return checkInService.checkIn(checkInDto);
    }

    @PostMapping("/check-out")
    @ResponseStatus(HttpStatus.OK)
    public CheckOutResponse checkOut(@Valid @RequestBody CheckOutDto checkOutDto) {
        return checkInService.checkOut(checkOutDto);
    }

}
