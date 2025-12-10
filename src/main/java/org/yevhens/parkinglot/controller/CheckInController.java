package org.yevhens.parkinglot.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yevhens.parkinglot.model.CheckInDto;
import org.yevhens.parkinglot.model.CheckOutDto;

@RestController
@RequestMapping("/api/v1")
public class CheckInController {

    @PostMapping("/check-in")
    public void checkIn(@Valid @RequestBody CheckInDto checkInDto) {

    }

    @PostMapping("/check-out")
    public void checkOut(@Valid @RequestBody CheckOutDto checkOutDto) {

    }

}
