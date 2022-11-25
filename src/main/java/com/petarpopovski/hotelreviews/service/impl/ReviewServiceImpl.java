package com.petarpopovski.hotelreviews.service.impl;

import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.model.Review;
import com.petarpopovski.hotelreviews.model.User;
import com.petarpopovski.hotelreviews.repository.HotelRepository;
import com.petarpopovski.hotelreviews.repository.ReviewRepository;
import com.petarpopovski.hotelreviews.repository.UserRepository;
import com.petarpopovski.hotelreviews.service.interfaces.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, HotelRepository hotelRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Review> listAllReviews() {
        return this.reviewRepository.findAll();
    }

    @Override
    public List<Review> listAllReviewsByHotel(Long hotelId) {
        return this.reviewRepository.findAllByHotel_Id(hotelId);
    }

    @Override
    public Review findById(Long reviewId) {
        return this.reviewRepository.findById(reviewId).orElseThrow();
    }

    @Override
    public Review create(Long authorId, Long hotelId, Integer grade, String description) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow();
        User author = this.userRepository.findById(authorId).orElseThrow();
        Review review = new Review(author,hotel,grade,description);



        return this.reviewRepository.save(review);
    }

    @Override
    public Review update(Long reviewId, Long authorId, Long hotelId, Integer grade, String description) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow();
        User author = this.userRepository.findById(authorId).orElseThrow();
        Review review = this.reviewRepository.findById(reviewId).orElseThrow();

        review.setAuthor(author);
        review.setHotel(hotel);
        review.setGrade(grade);
        review.setDescription(description);
        return this.reviewRepository.save(review);
    }

    @Override
    public Review delete(Long reviewId) {
        Review reviewForDeletion = this.reviewRepository.findById(reviewId).orElseThrow();
        this.reviewRepository.delete(reviewForDeletion);
        return reviewForDeletion;


    }
}
