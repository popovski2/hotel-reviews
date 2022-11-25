package com.petarpopovski.hotelreviews.service.impl;

import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.model.Review;
import com.petarpopovski.hotelreviews.model.User;
import com.petarpopovski.hotelreviews.model.enumerations.Role;
import com.petarpopovski.hotelreviews.model.exceptions.InvalidRoleException;
import com.petarpopovski.hotelreviews.model.exceptions.InvalidUserArgumentsException;
import com.petarpopovski.hotelreviews.repository.HotelRepository;
import com.petarpopovski.hotelreviews.repository.ReviewRepository;
import com.petarpopovski.hotelreviews.repository.UserRepository;
import com.petarpopovski.hotelreviews.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HotelRepository hotelRepository;
    private final ReviewRepository reviewRepository;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, HotelRepository hotelRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.hotelRepository = hotelRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User getUserByDisplayName(String displayName) {
        return this.userRepository.findUserByDisplayName(displayName);
    }

    @Override
    public User login(String email, String password) {
        if (email==null || email.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidUserArgumentsException();
        }
        return userRepository.findByEmailAndPassword(email, password);
    }



    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User registerAsRegular(String email, String displayName, String password) {
        if (email==null || email.isEmpty()  || password==null || password.isEmpty()) {
            throw new InvalidUserArgumentsException();
        }
        String encryptedPassword = this.passwordEncoder.encode(password);
        User user = new User(email, displayName, encryptedPassword, Role.REGULAR_USER);
        return userRepository.save(user);
    }

    @Override
    public User registerAsAdministrator(String email, String displayName, String password) {
        if (email==null || email.isEmpty()  || password==null || password.isEmpty()) {
            throw new InvalidUserArgumentsException();
        }
        String encryptedPassword = this.passwordEncoder.encode(password);
        User user = new User(email, displayName, encryptedPassword, Role.ADMINISTRATOR);
        return userRepository.save(user);
    }

    @Override
    public User delete(Long userId) {
        User userForDeletion = this.userRepository.findById(userId).orElseThrow();
        this.userRepository.delete(userForDeletion);
        return userForDeletion;
    }

    @Override
    public void addHotelToFavorites(Long userId, Long hotelId) {
        User user = this.userRepository.findById(userId).orElseThrow();
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow();
        if(user.getRole().equals(Role.REGULAR_USER)) {
            user.getFavorites().add(hotel);
        }
        else throw new InvalidRoleException(user.getDisplayName());

    }

    @Override
    public void removeHotelFromFavorites(Long userId, Long hotelId) {
        User user = this.userRepository.findById(userId).orElseThrow();
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow();

        if(user.getRole().equals(Role.REGULAR_USER)) {
            user.getFavorites().remove(hotel);
        }
        else throw new InvalidRoleException(user.getDisplayName());

    }

    @Override
    public void likeReview(Long userId, Long reviewId) {
        User user = this.userRepository.findById(userId).orElseThrow();
        Review review = this.reviewRepository.findById(reviewId).orElseThrow();
        review.setLikes(review.getLikes()+1);
        review.addLikeFromUser(user);
    }

    @Override
    public void removeLikeFromReview(Long userId, Long reviewId) {
        User user = this.userRepository.findById(userId).orElseThrow();
        Review review = this.reviewRepository.findById(reviewId).orElseThrow();
        review.setLikes(review.getLikes()-1);
        review.removeLikeFromUser(user);


    }

    @Override
    public void dislikeReview(Long userId, Long reviewId) {
        User user = this.userRepository.findById(userId).orElseThrow();
        Review review = this.reviewRepository.findById(reviewId).orElseThrow();
        review.setDislikes(review.getDislikes()+1);
        review.addDislikeFromUser(user);

    }

    @Override
    public void removeDislikeFromReview(Long userId, Long reviewId) {
        User user = this.userRepository.findById(userId).orElseThrow();
        Review review = this.reviewRepository.findById(reviewId).orElseThrow();
        review.setDislikes(review.getLikes()-1);
        review.removeDislikeFromUser(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByDisplayName(username);
    }
}
