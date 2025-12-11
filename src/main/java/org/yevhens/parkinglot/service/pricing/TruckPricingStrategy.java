package org.yevhens.parkinglot.service.pricing;

import org.springframework.stereotype.Component;
import org.yevhens.parkinglot.entity.ParkingSession;
import org.yevhens.parkinglot.entity.vehicle.Truck;
import org.yevhens.parkinglot.service.PricingStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Component
public class TruckPricingStrategy implements PricingStrategy<Truck> {

    public static final BigDecimal TRUCK_FEE = BigDecimal.valueOf(3);

    @Override
    public BigDecimal calculateFee(ParkingSession session) {
        Duration duration = Duration.between(session.getStartDateTime(), session.getFinishDateTime());
        return BigDecimal.valueOf(duration.getSeconds())
                .multiply(TRUCK_FEE)
                .divide(BigDecimal.valueOf(3600), 2, RoundingMode.UP);
    }
}
