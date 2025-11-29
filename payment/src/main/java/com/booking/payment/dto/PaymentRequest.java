package com.booking.payment.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long bookingId;
    private double amount;
}
