package org.yevhens.parkinglot.service.impl;

import org.junit.jupiter.api.Test;
import org.yevhens.parkinglot.entity.ParkingSession;
import org.yevhens.parkinglot.entity.vehicle.Car;
import org.yevhens.parkinglot.entity.vehicle.Motorcycle;
import org.yevhens.parkinglot.entity.vehicle.Truck;
import org.yevhens.parkinglot.service.pricing.CarPricingStrategy;
import org.yevhens.parkinglot.service.pricing.MotorcyclePricingStrategy;
import org.yevhens.parkinglot.service.pricing.TruckPricingStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TruckPricingStrategyTest {

    @Test
    void calculateFeeTruck() {

        ParkingSession build = ParkingSession.builder()
                .vehicle(Truck.builder().build())
                .startDateTime(LocalDateTime.of(2025, 12, 10, 2, 23, 45).atZone(ZoneOffset.UTC).toInstant())
                .finishDateTime(LocalDateTime.of(2025, 12, 10, 12, 53, 45).atZone(ZoneOffset.UTC).toInstant())
                .build();
        BigDecimal fee = new TruckPricingStrategy().calculateFee(build);

        assertEquals(BigDecimal.valueOf(31.50).setScale(2, RoundingMode.UP), fee);
    }

    @Test
    void calculateFeeMotorcycle() {

        ParkingSession build = ParkingSession.builder()
                .vehicle(Motorcycle.builder().build())
                .startDateTime(LocalDateTime.of(2025, 12, 10, 2, 23, 45).atZone(ZoneOffset.UTC).toInstant())
                .finishDateTime(LocalDateTime.of(2025, 12, 10, 12, 53, 45).atZone(ZoneOffset.UTC).toInstant())
                .build();
        BigDecimal fee = new MotorcyclePricingStrategy().calculateFee(build);

        assertEquals(BigDecimal.valueOf(10.50).setScale(2, RoundingMode.UP), fee);
    }

    @Test
    void calculateFeeCar() {

        ParkingSession build = ParkingSession.builder()
                .vehicle(Car.builder().build())
                .startDateTime(LocalDateTime.of(2025, 12, 10, 2, 23, 45).atZone(ZoneOffset.UTC).toInstant())
                .finishDateTime(LocalDateTime.of(2025, 12, 10, 12, 53, 45).atZone(ZoneOffset.UTC).toInstant())
                .build();
        BigDecimal fee = new CarPricingStrategy().calculateFee(build);

        assertEquals(BigDecimal.valueOf(21.00).setScale(2, RoundingMode.UP), fee);
    }


}