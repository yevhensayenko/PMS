package org.yevhens.parkinglot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yevhens.parkinglot.entity.ParkingSession;
import org.yevhens.parkinglot.entity.vehicle.Car;
import org.yevhens.parkinglot.entity.vehicle.Motorcycle;
import org.yevhens.parkinglot.entity.vehicle.Truck;
import org.yevhens.parkinglot.entity.vehicle.Vehicle;
import org.yevhens.parkinglot.service.pricing.CarPricingStrategy;
import org.yevhens.parkinglot.service.pricing.MotorcyclePricingStrategy;
import org.yevhens.parkinglot.service.pricing.TruckPricingStrategy;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PricingServiceTest {

    private PricingService pricingService;

    @BeforeEach
    void setUp() {
        pricingService = new PricingService(List.of(
                new CarPricingStrategy(),
                new MotorcyclePricingStrategy(),
                new TruckPricingStrategy()
        ));
    }

    @Test
    void calculatesFeeWithVehicleSpecificStrategy() {
        Instant start = Instant.now().minus(Duration.ofMinutes(90));
        Instant finish = Instant.now();

        Car car = Car.builder().handicapped(false).build();
        car.setLicensePlate("CAR-123");
        ParkingSession carSession = ParkingSession.builder()
                .vehicle(car)
                .startDateTime(start)
                .finishDateTime(finish)
                .build();

        Motorcycle motorcycle = Motorcycle.builder().build();
        motorcycle.setLicensePlate("MOTO-1");
        ParkingSession motoSession = ParkingSession.builder()
                .vehicle(motorcycle)
                .startDateTime(start)
                .finishDateTime(finish)
                .build();

        Truck truck = Truck.builder().build();
        truck.setLicensePlate("TRUCK-9");
        ParkingSession truckSession = ParkingSession.builder()
                .vehicle(truck)
                .startDateTime(start)
                .finishDateTime(finish)
                .build();

        assertThat(pricingService.calculateFee(carSession)).isEqualTo(new BigDecimal("3.00"));
        assertThat(pricingService.calculateFee(motoSession)).isEqualTo(new BigDecimal("1.50"));
        assertThat(pricingService.calculateFee(truckSession)).isEqualTo(new BigDecimal("4.50"));
    }

    @Test
    void throwsWhenVehicleTypeUnsupported() {
        class Plane extends Vehicle {
        }

        Vehicle unsupported = new Plane();
        unsupported.setLicensePlate("PLANE-404");

        ParkingSession parkingSession = ParkingSession.builder()
                .vehicle(unsupported)
                .startDateTime(Instant.now())
                .finishDateTime(Instant.now().plusSeconds(60))
                .build();

        assertThatThrownBy(() -> pricingService.calculateFee(parkingSession))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("Plane vehicle is not supported");
    }
}