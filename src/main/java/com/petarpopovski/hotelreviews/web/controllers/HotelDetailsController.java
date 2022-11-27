package com.petarpopovski.hotelreviews.web.controllers;

import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.model.Review;
import com.petarpopovski.hotelreviews.service.interfaces.HotelService;
import com.petarpopovski.hotelreviews.service.interfaces.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/hotel-details")
public class HotelDetailsController {

    private final HotelService hotelService;
    private final ReviewService reviewService;

    public HotelDetailsController(HotelService hotelService, ReviewService reviewService) {
        this.hotelService = hotelService;
        this.reviewService = reviewService;
    }

    @GetMapping("{id}")
    public String getHotelDetailsPage(@RequestParam(required = false)String error, @PathVariable Long id, Model model){

        //  if there is some error, put it into the model in order to display it on frontend
        if(error !=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }

        if(this.hotelService.findById(id).isPresent()){

            Hotel hotel = this.hotelService.findById(id).get();
            List<Review> reviewsForHotel = this.reviewService.listAllReviewsByHotel(hotel.getId());

            model.addAttribute("hotel", hotel);
            model.addAttribute("reviews", reviewsForHotel);
            return "hotel-details";
        }

        return "hotel-details";
   }




}
