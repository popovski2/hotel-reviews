package com.petarpopovski.hotelreviews.service.interfaces;

import com.petarpopovski.hotelreviews.model.User;
import com.petarpopovski.hotelreviews.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {


    List<User> getAllUsers();

    User getUserByEmail(String email);

    User getUserByDisplayName(String displayName);

    User login(String email, String password);

    User saveUser(User user);

    User registerAsRegular(String email, String displayName, String password);

    User registerAsAdministrator(String email, String displayName, String password);

    User delete(Long userId);

    void addHotelToFavorites(Long userId, Long hotelId);
    void removeHotelFromFavorites(Long userId, Long hotelId);

    void likeReview(Long userId, Long reviewId);
    void removeLikeFromReview(Long userId, Long reviewId);

    void dislikeReview(Long userId, Long reviewId);
    void removeDislikeFromReview(Long userId, Long reviewId);

}
