package com.booking.book.service;

import com.booking.book.dto.BookingRequest;
import com.booking.book.dto.BookingResponse;
import com.booking.book.entity.Booking;
import com.booking.book.exception.CustomException;
import com.booking.book.feign.FlightClient;
import com.booking.book.feign.HotelClient;
import com.booking.book.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository repository;
    private final FlightClient flightClient;
    private final HotelClient hotelClient;
    private final WebClient webClient;

    public BookingService(BookingRepository repository,
                          FlightClient flightClient,
                          HotelClient hotelClient,
                          WebClient webClient) {

        this.repository = repository;
        this.flightClient = flightClient;
        this.hotelClient = hotelClient;
        this.webClient = webClient;
    }

    public BookingResponse createBooking(BookingRequest request) {
        log.info("Creating booking with request: userId={}, flightId={}, hotelId={}, travelDate={}",
                request.getUserId(), request.getFlightId(), request.getHotelId(), request.getTravelDate());

        // Validate request
        if (request.getUserId() == null) {
            throw new CustomException("User ID cannot be null");
        }
        if (request.getFlightId() == null) {
            throw new CustomException("Flight ID cannot be null");
        }
        if (request.getHotelId() == null) {
            throw new CustomException("Hotel ID cannot be null");
        }

        // Validate user (via User Service)
        Boolean validUser;
        try {
            validUser = webClient.get()
                    .uri("http://localhost:8081/users/validate/" + request.getUserId())
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            log.info("User validation result: {}", validUser);
        } catch (Exception e) {
            log.error("Error validating user with id {}: {}", request.getUserId(), e.getMessage());
            throw new CustomException("User service is unavailable: " + e.getMessage());
        }

        if (validUser == null || !validUser) {
            throw new CustomException("Invalid user with id: " + request.getUserId());
        }

        // Check flight availability
        if (!flightClient.isAvailable(request.getFlightId())) {
            throw new CustomException("Flight is not available");
        }

        // Check hotel availability
        if (!hotelClient.isAvailable(request.getHotelId())) {
            throw new CustomException("Hotel is not available");
        }

        // Cost calculation
        double total = flightClient.getCost(request.getFlightId())
                + hotelClient.getCost(request.getHotelId());

        // Save PENDING booking
        Booking booking = new Booking(null,
                request.getUserId(),
                request.getFlightId(),
                request.getHotelId(),
                request.getTravelDate(),
                total,
                "PENDING");

        booking = repository.save(booking);

        // Call Payment Service
        try {
            String paymentResponse = webClient.post()
                    .uri("http://localhost:8084/payments")
                    .bodyValue(
                            new com.booking.book.dto.PaymentRequest(booking.getId(), booking.getTotalCost())
                    )
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            log.info("Payment response: {}", paymentResponse);
        } catch (Exception e) {
            log.error("Payment service error: {}", e.getMessage());
            // Continue even if payment fails (you can handle this differently in production)
        }

        // Notify Notification Service
        try {
            String notificationResponse = webClient.post()
                    .uri("http://localhost:8085/notifications")
                    .bodyValue(new BookingResponse(booking.getId(), "CONFIRMED", "Your trip is booked"))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            log.info("Notification response: {}", notificationResponse);
        } catch (Exception e) {
            log.error("Notification service error: {}", e.getMessage());
            // Continue even if notification fails
        }

        // Update booking to CONFIRMED
        booking.setStatus("CONFIRMED");
        repository.save(booking);

        return new BookingResponse(booking.getId(), "CONFIRMED", "Booking successful");
    }

    public BookingResponse getBooking(Long bookingId) {
        Booking booking = repository.findById(bookingId)
                .orElseThrow(() -> new CustomException("Booking not found"));
        return new BookingResponse(booking.getId(), booking.getStatus(), "Booking found");
    }
}
