package com.petarpopovski.hotelreviews.service.interfaces;

import com.petarpopovski.hotelreviews.model.User;
import com.petarpopovski.hotelreviews.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {


    /**
     * @desc Lists all Users from the database a Review object
     * @returns {List} List of users
     */
    List<User> getAllUsers();

    /**
     * @desc Retreives the user by its email
     * @param {String} User's email
     * @returns {User} User of users
     */
    User getUserByEmail(String email);

    /**
     * @desc Retreives the user by its display name
     * @param {String} User's display name
     * @returns {User} User of users
     */
    User getUserByDisplayName(String displayName);

    /**
     * @desc Logs in the user with its credentials
     * @param {String, String} User's email and user's password
     * @returns {User} Logged-in user
     */
    User login(String email, String password);

    User saveUser(User user);

    /**
     * @desc Registers user with REGULAR_USER role and encodes its password using BCrypt encoder
     * @param {String, String, String} User's email, user's display name and user's password
     * @returns {User} Registered user
     */
    User registerAsRegular(String email, String displayName, String password);

    /**
     * @desc Registers user with ADMIN role and encodes its password using BCrypt encoder
     * @param {String, String, String} User's email, user's display name and user's password
     * @returns {User} Registered user
     */
    User registerAsAdministrator(String email, String displayName, String password);


    /**
     * @desc Deletes user from database with provided id. With deleting the user, its reviews,
     * likes, dislikes and favorite hotels are cascadly removed from the database.
     * @param {Long} User id
     * @returns {User} Deleted user
     */
    User delete(Long userId);

    /**
     * @desc Adds the hotel to user's favorite hotels
     * @param {Long, Long} User id, hotel id
     * @returns void
     */
    void addHotelToFavorites(Long userId, Long hotelId);

    /**
     * @desc Removes the hotel from user's favorite hotels
     * @param {Long, Long} User id, hotel id
     * @returns void
     */
    void removeHotelFromFavorites(Long userId, Long hotelId);

    /**
     * @desc Clears the user's favorite hotels list
     * @param {Long} User id
     * @returns void
     */
    void removeAllFromFavorites(Long userId);

    /**
     * @desc Adds like from user to certain review.
     * All the users who liked the review are stored in Set data structure in order to have distinct values.
     * @param {Long, Long} User id, review id
     * @returns void
     */
    void likeReview(Long userId, Long reviewId);

    /**
     * @desc Removes like from user to certain review.
     * @param {Long, Long} User id, review id
     * @returns void
     */
    void removeLikeFromReview(Long userId, Long reviewId);

    /**
     * @desc Adds dislike from user to certain review.
     * All the users who disliked the review are stored in Set data structure in order to have distinct values.
     * @param {Long, Long} User id, review id
     * @returns void
     */
    void dislikeReview(Long userId, Long reviewId);

    /**
     * @desc Removes dislike from user to certain review.
     * @param {Long, Long} User id, review id
     * @returns void
     */
    void removeDislikeFromReview(Long userId, Long reviewId);

}
