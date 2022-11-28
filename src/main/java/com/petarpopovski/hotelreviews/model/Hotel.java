package com.petarpopovski.hotelreviews.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@AllArgsConstructor
@Entity
public class Hotel implements Serializable, Comparable<Hotel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hotelName;

    private String address;

    private String imageUrl;

    private String description;

    //because i try to save the hotel without firstly creating geolocation
    @OneToOne(cascade=CascadeType.PERSIST)
    private Geolocation geolocation;

    @OneToMany(mappedBy = "hotel",  fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    private Double overralRating;

    /** CONSTRUCTORS **/
    public Hotel(String hotelName, String address, String imageUrl, String description, Geolocation geolocation) {
        this.hotelName = hotelName;
        this.address = address;
        this.imageUrl = imageUrl;
        this.description = description;
        this.overralRating=0.0;
        this.geolocation=geolocation;
    }

    public Hotel() {
    }


    /** GETTERS **/
    public Geolocation getGeolocation() {
        return geolocation;
    }

    public Long getId() {
        return id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getAddress() {
        return address;
    }

    public List<Review> getReviews() {
        return reviews;
    }
    public String getDescription() {
        return description;
    }
    public Double getOverralRating() {
        return overralRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    /** SETTERS **/

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setOverralRating(Double overralRating) {
        this.overralRating = overralRating;
    }

    @Override
    public int compareTo(Hotel o) {
        return hotelName.compareTo(o.hotelName);
    }
}
