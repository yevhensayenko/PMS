package org.yevhens.parkinglot.service.impl;

import org.junit.jupiter.api.Test;
import org.yevhens.parkinglot.entity.ParkingSession;
import org.yevhens.parkinglot.entity.vehicle.Vehicle;
import org.yevhens.parkinglot.model.VehicleType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TruckPricingStrategyTest {

    @Test
    void calculateFee() {

        ParkingSession build = ParkingSession.builder()
                .vehicle(Vehicle.builder().type(VehicleType.TRUCK).build())
                .startDateTime(LocalDateTime.of(2025,12, 10, 2,23,45).atZone(ZoneOffset.UTC).toInstant())
                .finishDateTime(LocalDateTime.of(2025,12, 10, 12,53,45).atZone(ZoneOffset.UTC).toInstant())
                .build();
        BigDecimal fee = new TruckPricingStrategy().calculateFee(build);

        assertEquals(BigDecimal.valueOf(123), fee);

    }
}