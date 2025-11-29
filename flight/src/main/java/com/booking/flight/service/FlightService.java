package com.booking.flight.service;

import com.booking.flight.entity.Flight;
import com.booking.flight.exception.CustomException;
import com.booking.flight.repository.FlightRepository;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

    private final FlightRepository repository;

    public FlightService(FlightRepository repository) {
        this.repository = repository;
    }

    public boolean isAvailable(Long flightId) {
        return repository.findById(flightId)
                .map(Flight::isAvailable)
                .orElse(false);
    }

    public double getCost(Long flightId) {
        return repository.findById(flightId)
                .map(Flight::getCost)
                .orElseThrow(() -> new CustomException("Flight not found"));
    }

    public Flight getFlight(Long flightId) {
        return repository.findById(flightId)
                .orElseThrow(() -> new CustomException("Flight not found"));
    }

    public Flight saveFlight(Flight flight) {
        // Set id to null to let the database auto-generate it
        flight.setId(null);
        return repository.save(flight);
    }
}
