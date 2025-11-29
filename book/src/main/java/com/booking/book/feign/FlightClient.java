package com.booking.book.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "flight-service", url = "http://localhost:8082")
public interface FlightClient {

    @GetMapping("/api/flights/{flightId}/available")
    boolean isAvailable(@PathVariable Long flightId);

    @GetMapping("/api/flights/{flightId}/cost")
    double getCost(@PathVariable Long flightId);
}
