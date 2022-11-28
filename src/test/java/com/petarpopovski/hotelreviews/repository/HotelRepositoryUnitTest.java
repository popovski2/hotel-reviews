package com.petarpopovski.hotelreviews.repository;

import com.petarpopovski.hotelreviews.model.Geolocation;
import com.petarpopovski.hotelreviews.model.Hotel;
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
public class HotelRepositoryUnitTest {

    @Autowired
    private HotelRepository hotelRepository;

    // instantiating a hotel that will be used throughout all of the tests
    Hotel hotel = new Hotel("test hotel", "test address", "url", "test description",new Geolocation(2.55,3.45));



    /**
     * Testing the save method of the JPA repository that is invoked when creating a new Hotel
     * */
    @Test
    public void HotelRepository_AddNewHotel_ReturnNotNull() {
        // Arrange
        // using the instantiated hotel

        // Add
        Hotel savedHotel = hotelRepository.save(hotel);

        // Assert
        Assertions.assertThat(savedHotel).isNotNull();
        Assertions.assertThat(savedHotel.getId()).isGreaterThan(0);
    }



    /**
     * Testing the findById method of the JPA repository that is invoked when editing an existing Hotel
     * */
    @Test
    public void HotelRepository_EditHotel_ReturnIsEqualTo() {
        // Arrange
        hotelRepository.save(hotel);

        // Act
        Hotel hotelEdit = hotelRepository.findById(hotel.getId()).get(); // .get() is needed because the object is optional
        hotelEdit.setHotelName("new name");
        hotelEdit.setAddress("new address");

        // Assert
        Assertions.assertThat(hotelEdit.getHotelName()).isEqualTo("new name");
        Assertions.assertThat(hotelEdit.getAddress()).isEqualTo("new address");
    }



    /**
     * Testing the delete method of the JPA repository that is invoked when deleting an existing Hotel
     * */
    @Test
    public void HotelRepository_DeleteHotel_ReturnIsEmpty() {
        // Arrange
        hotelRepository.save(hotel);

        // Act
        Hotel hotelDelete = hotelRepository.findById(hotel.getId()).get();
        hotelRepository.delete(hotelDelete);
        Optional<Hotel> hotelReturned = hotelRepository.findById(hotel.getId());

        // Assert
        Assertions.assertThat(hotelReturned).isEmpty();
    }


    /**
     * Testing the findAll method of the JPA repository that is invoked when listing the hotels in natural order
     * */
    @Test
    public void HotelRepository_ListAllHotels_ReturnIsNotNull() {
        // Arrange
        hotelRepository.save(hotel);

        // Act
        List<Hotel> hotelsFound = hotelRepository.findAll();

        // Assert
        Assertions.assertThat(hotelsFound).isNotNull();
    }


    /**
     * Testing the findHotelByName method defined in the HotelRepository
     * */
    @Test
    public void HotelRepository_FindHotelByName_ReturnIsNotNull() {
        // Arrange
        hotelRepository.save(hotel);

        // Act
        Hotel hotelFound = hotelRepository.findByHotelName("test hotel");

        // Assert
        Assertions.assertThat(hotelFound).isNotNull();
    }

    /**
     * Testing the findAllByAddress method defined in the HotelRepository
     * */
    @Test
    public void HotelRepository_FindAllByAddress_ReturnIsNotNull() {
        // Arrange
        hotelRepository.save(hotel);

        // adding one more hotel
        Hotel hotel1 = new Hotel("test hotel1", "test address", "url1", "test description",new Geolocation(2.55,3.45));

        hotelRepository.save(hotel1);

        // Act
        List<Hotel> hotelsFound = hotelRepository.findAllByAddress("test address");

        // Assert
        Assertions.assertThat(hotelsFound).isNotNull();
        Assertions.assertThat(hotelsFound.size()).isEqualTo(2);
    }
}