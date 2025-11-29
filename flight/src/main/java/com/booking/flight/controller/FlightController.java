package com.booking.flight.controller;

import com.booking.flight.entity.Flight;
import com.booking.flight.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService service;

    public FlightController(FlightService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        return ResponseEntity.ok(service.saveFlight(flight));
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<Flight> getFlight(@PathVariable Long flightId) {
        return ResponseEntity.ok(service.getFlight(flightId));
    }

    @GetMapping("/{flightId}/available")
    public ResponseEntity<Boolean> isAvailable(@PathVariable Long flightId) {
        return ResponseEntity.ok(service.isAvailable(flightId));
    }

    @GetMapping("/{flightId}/cost")
    public ResponseEntity<Double> getCost(@PathVariable Long flightId) {
        return ResponseEntity.ok(service.getCost(flightId));
    }
}
