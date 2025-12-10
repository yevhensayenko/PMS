package org.yevhens.parkinglot.model;

import java.math.BigDecimal;

public record CheckOutResponse(
        Long receiptId,
        BigDecimal fee
) {
}
