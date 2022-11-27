package com.petarpopovski.hotelreviews.service.impl;

import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.model.exceptions.InvalidHotelIdException;
import com.petarpopovski.hotelreviews.repository.HotelRepository;
import com.petarpopovski.hotelreviews.service.interfaces.HotelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Hotel> findById(Long hotelId)
    {
        return Optional.ofNullable(this.hotelRepository.findById(hotelId).orElseThrow(() -> new InvalidHotelIdException(hotelId)));
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

        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(() -> new InvalidHotelIdException(hotelId));
        hotel.setHotelName(hotelName);
        hotel.setAddress(address);
        hotel.setImageUrl(imageUrl);
        hotel.setDescription(description);
        this.hotelRepository.save(hotel);
        return hotel;
    }

    @Override
    public Hotel delete(Long hotelId) {
        Hotel hotelForDeletion = this.hotelRepository.findById(hotelId).orElseThrow(() -> new InvalidHotelIdException(hotelId));
        this.hotelRepository.delete(hotelForDeletion);
        return hotelForDeletion;
    }
}
