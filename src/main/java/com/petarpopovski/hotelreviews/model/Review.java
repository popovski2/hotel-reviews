package com.petarpopovski.hotelreviews.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
public class Review {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private User author;

        @ManyToOne
        private Hotel hotel;

        private Integer grade;

        private String description;

        private Integer likes;

        private Integer dislikes;

        @ManyToMany
        private List<User> usersLiked;

        @ManyToMany
        private List<User> usersDisiked;


    public Review(User author, Hotel hotel, Integer grade, String description) {
        this.author = author;
        this.hotel = hotel;
        this.grade = grade;
        this.description = description;
        this.likes = likes;
        this.usersLiked = usersLiked;
        this.usersDisiked = usersDisiked;
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
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public List<User> getUsersLiked() {
        return usersLiked;
    }

    public void setUsersLiked(List<User> usersLiked) {
        this.usersLiked = usersLiked;
    }

    public List<User> getUsersDisiked() {
        return usersDisiked;
    }

    public void setUsersDisiked(List<User> usersDisiked) {
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
