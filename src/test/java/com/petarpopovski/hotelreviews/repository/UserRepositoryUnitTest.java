package com.petarpopovski.hotelreviews.repository;

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
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RunWith(SpringRunner.class)
public class UserRepositoryUnitTest {

    @Autowired
    private UserRepository userRepository;

    // instantiating a user that will be used throughout all of the tests
    User user = new User("email@email.com","user","password",com.petarpopovski.hotelreviews.model.enumerations.Role.ADMIN);



    /**
     * Testing the save method of the JPA repository that is invoked when saving a user
     * */
    @Test
    public void UserRepository_SaveUser_ReturnNotNull() {
        // Arrange
        // using the instantiated hotel

        // Add
        User savedUser = userRepository.save(user);

        // Assert
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }


    /**
     * Testing the delete method of the JPA repository that is invoked when deleting an existing Hotel
     * */
    @Test
    public void UserRepository_DeleteUser_ReturnIsEmpty() {
        // Arrange
        userRepository.save(user);

        // Act
        User userDelete = userRepository.findById(user.getId()).get();
        userRepository.delete(userDelete);
        Optional<User> userReturned = userRepository.findById(user.getId());

        // Assert
        Assertions.assertThat(userReturned).isEmpty();
    }


    /**
     * Testing the findAll method of the JPA repository
     * */
    @Test
    public void UserRepository_FindAll_ReturnIsNotNull() {
        // Arrange
        userRepository.save(user);

        // Act
        List<User> usersFound = userRepository.findAll();

        // Assert
        Assertions.assertThat(usersFound).isNotNull();
    }


    /**
     * Testing the findById method of the JPA repository
     * */
    @Test
    public void UserRepository_FindById_ReturnIsNotNull() {
        // Arrange
        userRepository.save(user);

        // Act
        User userFound = userRepository.findById(user.getId()).get();

        // Assert
        Assertions.assertThat(userFound).isNotNull();
    }


    /**
     * Testing the findByEmail method defined in the UserRepository
     * */
    @Test
    public void UserRepository_FindByEmail_ReturnIsNotNull() {
        // Arrange
        userRepository.save(user);

        // Act
        User userFound = userRepository.findByEmail("email@email.com");

        // Assert
        Assertions.assertThat(userFound).isNotNull();
    }

    /**
     * Testing the findByDisplayName method defined in the UserRepository
     * */
    @Test
    public void UserRepository_FindByDisplayName_ReturnIsNotNull() {
        // Arrange
        userRepository.save(user);

        // Act
        User userFound = userRepository.findUserByDisplayName("user");

        // Assert
        Assertions.assertThat(userFound).isNotNull();
    }


    /**
     * Testing the findByEmailAndPassword method defined in the UserRepository
     * */
    @Test
    public void UserRepository_FindByEmailAndPassword_ReturnIsNotNull() {
        // Arrange
        userRepository.save(user);

        // Act
        User userFound = userRepository.findByEmailAndPassword("email@email.com", "password");

        // Assert
        Assertions.assertThat(userFound).isNotNull();
    }


}