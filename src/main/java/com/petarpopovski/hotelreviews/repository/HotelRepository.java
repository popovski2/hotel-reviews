package com.petarpopovski.hotelreviews.repository;

import com.petarpopovski.hotelreviews.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

    Hotel findByHotelName(String hotelName);

    List<Hotel> findAllByAddress(String address);
}
