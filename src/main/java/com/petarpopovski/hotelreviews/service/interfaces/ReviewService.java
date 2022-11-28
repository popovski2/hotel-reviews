package com.petarpopovski.hotelreviews.service.interfaces;

import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.model.Review;
import com.petarpopovski.hotelreviews.model.User;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    /**
     * @desc Lists all the reviews present in the database
     * @returns {List} List of all reviews
     */
    List<Review> listAllReviews();

    /**
     * @desc Lists all the reviews by its hotel id
     * @param {Long} Hotel id
     * @returns {List} List of all reviews
     */
    List<Review> listAllReviewsByHotel(Long hotelId);

    /**
     * @desc Finds the review by its id
     * @param {Long} The id of the review
     * @returns {Optional<Review>} Review review
     */
    Optional<Review> findById(Long reviewId);

    /**
     * @desc Creates a Review object and automatically recalculates the overral rating of the hotel.
     * @param {Long, Long, Integer, String} Author id, Hotel id, Grade of the review and description
     * @returns {Review} The created Review object
     */
    Review create(Long authorId, Long hotelId, Integer grade, String description);

    /**
     * @desc Updates a Review object
     * @param {Long, Long, Long, Integer, String} Review id, Author id, Hotel id, Grade of the review and description
     * @returns {Review} The updated Review object
     */
    Review update(Long reviewId, Long authorId, Long hotelId, Integer grade, String description);

    /**
     * @desc Deletes a Review object
     * @param {Long} Review id
     * @returns {Review} The deleted Review object
     */
    Review delete(Long reviewId);
}
