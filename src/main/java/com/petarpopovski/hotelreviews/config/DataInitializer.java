package com.petarpopovski.hotelreviews.config;

import com.petarpopovski.hotelreviews.service.interfaces.HotelService;
import com.petarpopovski.hotelreviews.service.interfaces.ReviewService;

import javax.annotation.PostConstruct;

public class DataInitializer {

    private final HotelService hotelService;
    private final ReviewService reviewService;

    public DataInitializer(HotelService hotelService, ReviewService reviewService) {
        this.hotelService = hotelService;
        this.reviewService = reviewService;
    }

    @PostConstruct
    public void initData(){

        for (int i = 0; i <10 ; i++) {
            this.hotelService.create("Hotel"+i,"Address"+i,"Image url"+i,"Description"+i);
           // this.reviewService.cre
        }

    }
}
