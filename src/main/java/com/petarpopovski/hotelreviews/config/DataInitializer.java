package com.petarpopovski.hotelreviews.config;

import com.petarpopovski.hotelreviews.service.interfaces.HotelService;
import com.petarpopovski.hotelreviews.service.interfaces.ReviewService;
import com.petarpopovski.hotelreviews.service.interfaces.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final HotelService hotelService;
    private final ReviewService reviewService;
    private final UserService userService;

    public DataInitializer(HotelService hotelService, ReviewService reviewService, UserService userService) {
        this.hotelService = hotelService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostConstruct
    public void initData(){

        this.userService.registerAsAdministrator("ppopovski53@gmail.com","petarpopovski","1234");
        this.userService.registerAsRegular("loofland@gmail.com","loof_land","1234");

        this.hotelService.create("ZHotel","Address","https://media.istockphoto.com/id/104731717/photo/luxury-resort.jpg?s=612x612&w=0&k=20&c=cODMSPbYyrn1FHake1xYz9M8r15iOfGz9Aosy9Db7mI=","Description");
        this.hotelService.create("CHotel","Address","https://media.istockphoto.com/id/104731717/photo/luxury-resort.jpg?s=612x612&w=0&k=20&c=cODMSPbYyrn1FHake1xYz9M8r15iOfGz9Aosy9Db7mI=","Description");

        for (int i = 0; i <10 ; i++) {
            this.hotelService.create("Hotel"+i,"Address"+i,"https://media.istockphoto.com/id/104731717/photo/luxury-resort.jpg?s=612x612&w=0&k=20&c=cODMSPbYyrn1FHake1xYz9M8r15iOfGz9Aosy9Db7mI=","Description"+i);
            this.reviewService.create(1L,1L,4,"This is review for the hotel 1");
        }

    }
}
