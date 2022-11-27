package com.petarpopovski.hotelreviews.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hotelName;

    private String address;

    private String imageUrl;

    private String description;

//    Geolocation

    @OneToMany(mappedBy = "hotel",  fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    private Double overralRating;

    public Hotel(String hotelName, String address, String imageUrl, String description) {
        this.hotelName = hotelName;
        this.address = address;
        this.imageUrl = imageUrl;
        this.description = description;
        this.overralRating=0.0;
    }

    public Hotel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Double getOverralRating() {
        return overralRating;
    }

    public void setOverralRating(Double overralRating) {
        this.overralRating = overralRating;
    }
}
