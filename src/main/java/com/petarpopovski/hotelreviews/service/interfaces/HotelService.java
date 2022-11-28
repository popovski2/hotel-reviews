package com.petarpopovski.hotelreviews.service.interfaces;

import com.petarpopovski.hotelreviews.model.Geolocation;
import com.petarpopovski.hotelreviews.model.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {

    /**
     * @desc Lists all the hotels present in the database
     * @returns {List} List of all hotels
     */
    List<Hotel> listAllHotels();


    /**
     * @desc Finds the hotel by its id
     * @param {Long} The id of the hotel
     * @returns {Optional<Hotel>} Hotel object
     */
    Optional<Hotel> findById(Long hotelId);


    /**
     * @desc Finds hotels by address
     * @param {String} Address
     * @returns {List<Hotel>} All hotels that have the same address
     */
    List<Hotel> listAllByAddress(String address);


    /**
     * @desc Finds the hotel by its name
     * @param {String} Name of the hotel
     * @returns {Hotel} The hotel have with the hotelName parameter
     */
    Hotel findByName(String hotelName);


    /**
     * @desc The function will do database searching for hotels with name same as the input text
     * or it will look if the address contains the input text.
     * @param {String} Input text from input form
     * @returns {List} List of hotels that have the same name as the input or the address contains the search input
     */
    List<Hotel> search(String searchText);


    /**
     * @desc Creates a Hotel object
     * @param {String, String, String, String, Geolocation} Hotel name, address of the hotel, image url, description and the geolocation
     * @returns {Hotel} The created Hotel object
     */
    Hotel create(String hotelName, String address, String imageUrl, String description, Geolocation geolocation);

    /**
     * @desc Updates the hotel object.
     * @param {Long, String, String, String, String, Geolocation} Hotel id, Hotel name, address of the hotel, image url, description and the geolocation
     * @returns {Hotel} The updated Hotel object
     */
    Hotel update(Long hotelId, String hotelName, String address, String imageUrl, String description, Geolocation geolocation);

    /**
     * @desc Deletes hotel object.
     * @param {Long} The hotel id
     * @returns {Hotel} The deleted Hotel object
     */
    Hotel delete(Long hotelId);

    /**
     * @desc Updates the overral rating of the hotel with the new review grade.
     * @param {Long, Integer} Hotel id, Grade of the review (1-5)
     * @returns void
     */
    void updateOverralRating(Long hotelId, Integer grade);
}
