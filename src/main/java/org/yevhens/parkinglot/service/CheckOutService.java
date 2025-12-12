package org.yevhens.parkinglot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yevhens.parkinglot.entity.ParkingSession;
import org.yevhens.parkinglot.exception.ResourceNotFoundException;
import org.yevhens.parkinglot.model.CheckOutDto;
import org.yevhens.parkinglot.model.CheckOutResponse;
import org.yevhens.parkinglot.repository.ParkingSessionRepository;
import org.yevhens.parkinglot.repository.ParkingSpotRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CheckOutService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ParkingSessionRepository parkingSessionRepository;
    private final PricingService pricingService;


    @Transactional
    public CheckOutResponse checkOut(CheckOutDto checkOutDto) {

        final ParkingSession parkingSession = parkingSessionRepository.findByIdAndFinishDateTimeNull(checkOutDto.receiptId())
                .map(session -> {
                    session.setFinishDateTime(Instant.now());
                    return parkingSessionRepository.save(session);
                })
                .orElseThrow(ResourceNotFoundException::new);

        parkingSession.getParkingSpot().setAvailable(true);
        parkingSpotRepository.save(parkingSession.getParkingSpot());

        final BigDecimal fee = pricingService.calculateFee(parkingSession);

        Duration duration = Duration.between(parkingSession.getStartDateTime(), parkingSession.getFinishDateTime());
        return new CheckOutResponse(checkOutDto.receiptId(), parkingSession.getStartDateTime(), parkingSession.getFinishDateTime(), duration, fee);
    }
}
