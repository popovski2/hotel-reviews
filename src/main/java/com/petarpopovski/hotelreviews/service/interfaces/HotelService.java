package com.petarpopovski.hotelreviews.service.interfaces;

import com.petarpopovski.hotelreviews.model.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {

    List<Hotel> listAllHotels();

    Optional<Hotel> findById(Long hotelId);

    List<Hotel> listAllByAddress(String address);

    Hotel findByName(String hotelName);

    Hotel create(String hotelName, String address, String imageUrl, String description);

    Hotel update(Long hotelId, String hotelName, String address, String imageUrl, String description);

    Hotel delete(Long hotelId);
}