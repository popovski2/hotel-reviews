package com.petarpopovski.hotelreviews.service.interfaces;

import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.model.Review;
import com.petarpopovski.hotelreviews.model.User;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> listAllReviews();

    List<Review> listAllReviewsByHotel(Long hotelId);

    Optional<Review> findById(Long reviewId);

    Review create(Long authorId, Long hotelId, Integer grade, String description);

    Review update(Long reviewId, Long authorId, Long hotelId, Integer grade, String description);

    Review delete(Long reviewId);
}
