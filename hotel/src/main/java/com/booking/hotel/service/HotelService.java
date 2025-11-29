package com.booking.hotel.service;

import com.booking.hotel.entity.Hotel;
import com.booking.hotel.exception.CustomException;
import com.booking.hotel.repository.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private final HotelRepository repository;

    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }

    public boolean isAvailable(Long hotelId) {
        return repository.findById(hotelId)
                .map(Hotel::isAvailable)
                .orElse(false);
    }

    public double getCost(Long hotelId) {
        return repository.findById(hotelId)
                .map(Hotel::getCost)
                .orElseThrow(() -> new CustomException("Hotel not found"));
    }

    public Hotel getHotel(Long hotelId) {
        return repository.findById(hotelId)
                .orElseThrow(() -> new CustomException("Hotel not found"));
    }

    public Hotel saveHotel(Hotel hotel) {
        // Set id to null to let the database auto-generate it
        hotel.setId(null);
        return repository.save(hotel);
    }
}
