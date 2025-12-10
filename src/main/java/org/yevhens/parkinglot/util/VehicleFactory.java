package org.yevhens.parkinglot.util;

import org.yevhens.parkinglot.entity.vehicle.Car;
import org.yevhens.parkinglot.entity.vehicle.Motorcycle;
import org.yevhens.parkinglot.entity.vehicle.Truck;
import org.yevhens.parkinglot.entity.vehicle.Vehicle;
import org.yevhens.parkinglot.model.VehicleType;

import java.time.Instant;

public final class VehicleFactory {

    private VehicleFactory() {
    }

    public static Vehicle create(VehicleType type, String plate) {
        String normalizedPlate = plate.trim().toUpperCase();

        return switch (type) {
            case CAR -> {
                Car car = new Car();
                car.setLicensePlate(normalizedPlate);
                car.setCreatedAt(Instant.now());
                yield car;
            }
            case MOTORCYCLE -> {
                Motorcycle m = new Motorcycle();
                m.setLicensePlate(normalizedPlate);
                m.setCreatedAt(Instant.now());
                yield m;
            }
            case TRUCK -> {
                Truck t = new Truck();
                t.setLicensePlate(normalizedPlate);
                t.setCreatedAt(Instant.now());
                yield t;
            }
        };
    }
}