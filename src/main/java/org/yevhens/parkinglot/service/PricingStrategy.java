package org.yevhens.parkinglot.service;

import org.yevhens.parkinglot.entity.ParkingSession;
import org.yevhens.parkinglot.entity.vehicle.Vehicle;

import java.math.BigDecimal;

public interface PricingStrategy<T extends Vehicle> {

    BigDecimal calculateFee(ParkingSession session);
}