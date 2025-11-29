package com.booking.payment.service;

import com.booking.payment.dto.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    public String makePayment(PaymentRequest request) {
        log.info("Processing payment for booking {} with amount {}",
                request.getBookingId(), request.getAmount());

        // Validate request
        if (request.getBookingId() == null) {
            throw new IllegalArgumentException("Booking ID cannot be null");
        }
        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than 0");
        }

        // In real app: payment gateway integration would go here
        // Simulate payment processing
        log.info("Payment processed successfully for booking {}", request.getBookingId());

        return "Payment successful for booking " + request.getBookingId() +
               " with amount $" + request.getAmount();
    }
}
