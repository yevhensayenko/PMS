package org.yevhens.parkinglot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yevhens.parkinglot.entity.Receipt;
import org.yevhens.parkinglot.model.CheckInDto;
import org.yevhens.parkinglot.model.CheckOutDto;
import org.yevhens.parkinglot.repository.ReceiptRepository;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final ReceiptRepository receiptRepository;

    public void checkIn(CheckInDto checkInDto) {
        final var receipt = new Receipt();

//        receipt.setParkingSpot();
//
//        receiptRepository.save(receipt)

    }

    public void checkOut(CheckOutDto checkOutDto) {

    }
}
