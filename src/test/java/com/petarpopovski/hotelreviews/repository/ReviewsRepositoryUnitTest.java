package com.petarpopovski.hotelreviews.repository;

import com.petarpopovski.hotelreviews.model.Geolocation;
import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.model.Review;
import com.petarpopovski.hotelreviews.model.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RunWith(SpringRunner.class)
public class ReviewsRepositoryUnitTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    Hotel hotel = new Hotel("test hotel", "test address", "url", "test description", new Geolocation(2.55, 3.45));

    User user = new User("email@email.com", "user", "password", com.petarpopovski.hotelreviews.model.enumerations.Role.ADMIN);

    // instantiating a review that will be used throughout all of the tests
    Review review = new Review(user,hotel,5,"description");

    /**
     * Testing the save method of the JPA repository that is invoked when creating a new Review
     * */
    @Test
    public void ReviewRepository_AddNewReview_ReturnIsNotNull() {
        // Arrange
        // using the instantiated review

        // Add
        Review savedReview = reviewRepository.save(review);

        // Assert
        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }


    /**
     * Testing the findAllByHotel_Ud method defined in the ReviewRepository
     * */
    @Test
    public void ReviewRepository_FindAllByHotelId_ReturnIsNotNull() {
        // Arrange
        hotelRepository.save(hotel);
        userRepository.save(user);
        reviewRepository.save(review);

        // Act
        Long hotelId = review.getHotel().getId();
        List<Review> reviewsFound = reviewRepository.findAllByHotel_Id(hotelId);

        // Assert
        Assertions.assertThat(reviewsFound).isNotNull();
    }


    /**
     * Testing the findAll method of the JPA repository
     * */
    @Test
    public void ReviewRepository_FindAll_ReturnIsNotNull() {
        // Arrange
        hotelRepository.save(hotel);
        userRepository.save(user);
        reviewRepository.save(review);

        // Act
        List<Review> reviewsFound = reviewRepository.findAll();

        // Assert
        Assertions.assertThat(reviewsFound).isNotNull();
    }




    /**
     * Testing the findById method of the JPA repository
     * */
    @Test
    public void ReviewRepository_FindById_ReturnIsNotNull() {
        // Arrange
        reviewRepository.save(review);

        // Act
        Review reviewFound = reviewRepository.findById(review.getId()).get();

        // Assert
        Assertions.assertThat(reviewFound).isNotNull();
    }






}