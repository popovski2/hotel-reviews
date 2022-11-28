package com.petarpopovski.hotelreviews.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@Entity
public class Review implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private User author;

        @ManyToOne
        private Hotel hotel;

        private Integer grade;

        private String description;

        @ManyToMany(cascade = CascadeType.REMOVE)
        private Set<User> usersLiked;

        @ManyToMany(cascade = CascadeType.REMOVE)
        private Set<User> usersDisiked;


    public Review(User author, Hotel hotel, Integer grade, String description) {
        this.author = author;
        this.hotel = hotel;
        this.grade = grade;
        this.description = description;
        this.usersLiked = new HashSet<>();
        this.usersDisiked = new HashSet<>();
    }

    public Review() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLikes() {
        return usersLiked.size();
    }

    public Integer getDislikes() {
        return this.usersDisiked.size();
    }

    public Set<User> getUsersLiked() {
        return usersLiked;
    }

    public void setUsersLiked(Set<User> usersLiked) {
        this.usersLiked = usersLiked;
    }

    public Set<User> getUsersDisiked() {
        return usersDisiked;
    }

    public void setUsersDisiked(Set<User> usersDisiked) {
        this.usersDisiked = usersDisiked;
    }

    public void addLikeFromUser(User user){
        this.usersLiked.add(user);
    }

    public void addDislikeFromUser(User user){
        this.usersDisiked.add(user);
    }


    public void removeLikeFromUser(User user){
        this.usersLiked.remove(user);

    }

    public void removeDislikeFromUser(User user){
        this.usersDisiked.remove(user);
    }
}
