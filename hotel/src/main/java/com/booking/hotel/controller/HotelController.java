package com.booking.hotel.controller;

import com.booking.hotel.entity.Hotel;
import com.booking.hotel.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService service;

    public HotelController(HotelService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.ok(service.saveHotel(hotel));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(service.getHotel(hotelId));
    }

    @GetMapping("/{hotelId}/available")
    public ResponseEntity<Boolean> isAvailable(@PathVariable Long hotelId) {
        return ResponseEntity.ok(service.isAvailable(hotelId));
    }

    @GetMapping("/{hotelId}/cost")
    public ResponseEntity<Double> getCost(@PathVariable Long hotelId) {
        return ResponseEntity.ok(service.getCost(hotelId));
    }
}
