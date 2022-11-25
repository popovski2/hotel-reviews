package com.petarpopovski.hotelreviews.service.impl;

import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.repository.HotelRepository;
import com.petarpopovski.hotelreviews.service.interfaces.HotelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> listAllHotels() {
        return this.hotelRepository.findAll();
    }

    @Override
    public Hotel findById(Long hotelId) {
        return this.hotelRepository.findById(hotelId).orElseThrow();
    }

    @Override
    public List<Hotel> listAllByAddress(String address) {
        return this.hotelRepository.findAllByAddress(address);
    }

    @Override
    public Hotel findByName(String hotelName) {
        return this.hotelRepository.findByHotelName(hotelName);
    }

    @Override
    public Hotel create(String hotelName, String address, String imageUrl, String description) {
        Hotel hotel = new Hotel(hotelName,address,imageUrl,description);
        this.hotelRepository.save(hotel);
        return hotel;
    }

    @Override
    public Hotel update(Long hotelId, String hotelName, String address, String imageUrl, String description) {

        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow();
        hotel.setHotelName(hotelName);
        hotel.setAddress(address);
        hotel.setImageUrl(imageUrl);
        hotel.setDescription(description);
        this.hotelRepository.save(hotel);
        return hotel;
    }

    @Override
    public Hotel delete(Long hotelId) {
        Hotel hotelForDeletion = this.hotelRepository.findById(hotelId).orElseThrow();
        this.hotelRepository.delete(hotelForDeletion);
        return hotelForDeletion;
    }
}
