package com.petarpopovski.hotelreviews.web.controllers;

import com.petarpopovski.hotelreviews.model.User;
import com.petarpopovski.hotelreviews.service.interfaces.ReviewService;
import com.petarpopovski.hotelreviews.service.interfaces.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("reviews")
public class ReviewsController {

    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewsController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }


    @PostMapping("/likeReview/{reviewId}")
    public String likeReview(@PathVariable Long reviewId, @RequestParam Long hotelId, HttpServletRequest request, Authentication authentication, Model model){
        try{
            User user = (User) authentication.getPrincipal();
            this.userService.likeReview(user.getId(),reviewId);
            System.out.println("this user is liking this review: " + user.getDisplayName());
            System.out.println("the review: " + this.reviewService.findById(reviewId).get().getDescription());
            return "redirect:/hotel-details/"+hotelId;
        } catch (RuntimeException exception){
            System.out.println(exception.toString());
            System.out.println(exception.getLocalizedMessage());
            return "redirect:/hotels?error="+ exception.getMessage();
        }
    }

    @PostMapping("/dislikeReview/{reviewId}")
    public String dislikeReview(@PathVariable Long reviewId, Long hotelIdDislike,HttpServletRequest request, Authentication authentication, Model model){
        try{
            User user = (User) authentication.getPrincipal();
            this.userService.dislikeReview(user.getId(),reviewId);
            return "redirect:/hotel-details/"+hotelIdDislike;
        } catch (RuntimeException exception){
            System.out.println(exception.toString());
            System.out.println(exception.getLocalizedMessage());
            return "redirect:/hotels?error="+ exception.getMessage();
        }
    }
}
