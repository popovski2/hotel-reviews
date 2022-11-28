package com.petarpopovski.hotelreviews.service.impl;

import com.petarpopovski.hotelreviews.model.Geolocation;
import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.model.Review;
import com.petarpopovski.hotelreviews.model.exceptions.InvalidHotelIdException;
import com.petarpopovski.hotelreviews.repository.HotelRepository;
import com.petarpopovski.hotelreviews.repository.ReviewRepository;
import com.petarpopovski.hotelreviews.service.interfaces.HotelService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ReviewRepository reviewRepository;

    public HotelServiceImpl(HotelRepository hotelRepository, ReviewRepository reviewRepository) {
        this.hotelRepository = hotelRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Hotel> listAllHotels() {

        return this.hotelRepository.findAll().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
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
    public Hotel create(String hotelName, String address, String imageUrl, String description, Geolocation geolocation) {
        Hotel hotel = new Hotel(hotelName,address,imageUrl,description,geolocation);
        this.hotelRepository.save(hotel);
        return hotel;
    }

    @Override
    public Hotel update(Long hotelId, String hotelName, String address, String imageUrl, String description,Geolocation geolocation) {

        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(() -> new InvalidHotelIdException(hotelId));
        hotel.setHotelName(hotelName);
        hotel.setAddress(address);
        hotel.setImageUrl(imageUrl);
        hotel.setDescription(description);
        hotel.setGeolocation(geolocation);
        this.hotelRepository.save(hotel);
        return hotel;
    }

    @Override
    public Hotel delete(Long hotelId) {
        Hotel hotelForDeletion = this.hotelRepository.findById(hotelId).orElseThrow(() -> new InvalidHotelIdException(hotelId));
        this.hotelRepository.delete(hotelForDeletion);
        return hotelForDeletion;
    }

    @Override
    public void updateOverralRating(Long hotelId, Integer grade) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(() -> new InvalidHotelIdException(hotelId));
        List<Review> reviews = this.reviewRepository.findAllByHotel_Id(hotelId);

        Double updatedOverralRating = reviews.stream().mapToDouble(Review::getGrade).sum() / reviews.size();
        hotel.setOverralRating(updatedOverralRating);
    }


    public List<Hotel> search(String searchText) {

        List<Hotel> result = this.hotelRepository.findAll();

        return result.stream()
                .filter(hotel ->
                        (hotel.getAddress().toLowerCase().contains(searchText.toLowerCase()))
                        || (hotel.getHotelName().equalsIgnoreCase(searchText)))
                .collect(Collectors.toList());

    }
}
