package org.yevhens.parkinglot.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

public record CheckOutResponse(
        Long receiptId,
        Instant entryTime,
        Instant exitTime,
        Duration duration,
        BigDecimal fee
) {
}
