package org.yevhens.parkinglot.service.pricing;

import org.springframework.stereotype.Component;
import org.yevhens.parkinglot.entity.ParkingSession;
import org.yevhens.parkinglot.entity.vehicle.Car;
import org.yevhens.parkinglot.service.PricingStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Component
public class CarPricingStrategy implements PricingStrategy<Car> {

    public static final BigDecimal CAR_FEE = BigDecimal.valueOf(2);

    @Override
    public BigDecimal calculateFee(ParkingSession session) {
        Duration duration = Duration.between(session.getStartDateTime(), session.getFinishDateTime());
        return BigDecimal.valueOf(duration.getSeconds())
                .multiply(CAR_FEE)
                .divide(BigDecimal.valueOf(3600), 2, RoundingMode.UP);
    }
}