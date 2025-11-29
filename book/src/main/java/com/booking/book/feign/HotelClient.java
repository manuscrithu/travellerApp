package com.booking.book.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hotel-service", url = "http://localhost:8083")
public interface HotelClient {

    @GetMapping("/api/hotels/{hotelId}/available")
    boolean isAvailable(@PathVariable Long hotelId);

    @GetMapping("/api/hotels/{hotelId}/cost")
    double getCost(@PathVariable Long hotelId);
}
