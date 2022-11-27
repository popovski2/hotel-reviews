package com.petarpopovski.hotelreviews.web.controllers;

import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.model.Review;
import com.petarpopovski.hotelreviews.model.User;
import com.petarpopovski.hotelreviews.service.interfaces.HotelService;
import com.petarpopovski.hotelreviews.service.interfaces.ReviewService;
import com.petarpopovski.hotelreviews.service.interfaces.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reviews")
public class ReviewsController {

    HttpSession session = null;

    private final ReviewService reviewService;
    private final UserService userService;
    private final HotelService hotelService;

    public ReviewsController(ReviewService reviewService, UserService userService, HotelService hotelService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.hotelService = hotelService;
    }


    @PostMapping("/likeReview/{reviewId}")
    public String likeReview(@PathVariable Long reviewId, @RequestParam Long hotelId,
                             HttpServletRequest request, Authentication authentication, Model model){
        try{
            Hotel hotel = this.hotelService.findById(hotelId).get();
            model.addAttribute("hotel",hotel);

            User user = (User) authentication.getPrincipal();

            session = request.getSession();
            // ako e vekje lajknato togas unlike napravi i return
            if((session.getAttribute("liked")!=null) && session.getAttribute("liked").equals(true)){
                this.userService.removeLikeFromReview(user.getId(),reviewId);
                session.setAttribute("liked",false);
                return "redirect:/hotel-details/"+hotelId;
            }

            this.userService.likeReview(user.getId(),reviewId);
            session.setAttribute("liked",true);
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

            /**     I GET THE HOTEL IN ORDER TO SEND THE DATA BACK AGAIN TO HOTEL DETAILS   **/
            Hotel hotel = this.hotelService.findById(hotelIdDislike).get();
            model.addAttribute("hotel",hotel);

            User user = (User) authentication.getPrincipal();

            /**     I GET THE SESSION IN ORDER TO SET IF THE USER HAS LIKED THE REVIEW OR NOT  **/
            session = request.getSession();
            // ako e vekje lajknato togas unlike napravi i return
            if((session.getAttribute("disliked")!=null) && session.getAttribute("disliked").equals(true)){
                this.userService.removeDislikeFromReview(user.getId(),reviewId);
                session.setAttribute("disliked",false);
                return "redirect:/hotel-details/"+hotelIdDislike;
            }

            this.userService.dislikeReview(user.getId(),reviewId);
            session.setAttribute("disliked",true);
            return "redirect:/hotel-details/"+hotelIdDislike;

        } catch (RuntimeException exception){
            System.out.println(exception.toString());
            System.out.println(exception.getLocalizedMessage());
            return "redirect:/hotels?error="+ exception.getMessage();
        }
    }

    @GetMapping("/allLikes/{reviewId}")
    public String showAllReviewLikes(@PathVariable Long reviewId,
                                   HttpServletRequest request, Authentication authentication, Model model){


        User user = (User) authentication.getPrincipal();
        Optional<Review> review =this.reviewService.findById(reviewId);
        List<User> allLikes = review.get().getUsersLiked().stream().collect(Collectors.toList());

     /*   Hotel hotel = this.hotelService.findById(hotelId).get();
        model.addAttribute("hotel",hotel);*/
        model.addAttribute("review",review);
        model.addAttribute("usersLiked", allLikes);

        return "all-likes";

    }
    @GetMapping("/allDislikes/{reviewId}")
    public String showAllReviewDislikes(@PathVariable Long reviewId,
                                     HttpServletRequest request, Authentication authentication, Model model){


        User user = (User) authentication.getPrincipal();
        Optional<Review> review =this.reviewService.findById(reviewId);
        List<User> allDislikes = review.get().getUsersDisiked().stream().collect(Collectors.toList());

     /*   Hotel hotel = this.hotelService.findById(hotelId).get();
        model.addAttribute("hotel",hotel);*/
     model.addAttribute("review",review);
        model.addAttribute("usersDisliked", allDislikes);

        return "all-dislikes";

    }

    @GetMapping("/add/{hotelId}")
    public String getLeaveAReviewPage(@PathVariable Long hotelId,
                                      HttpServletRequest request, Authentication authentication, Model model){

        User user = (User) authentication.getPrincipal();
        Hotel hotel = this.hotelService.findById(hotelId).get();
        List<Integer> grades = new ArrayList<>();
        grades.add(1);
        grades.add(2);
        grades.add(3);
        grades.add(4);
        grades.add(5);
        model.addAttribute("grades",grades);
        model.addAttribute("hotel",hotel);
        return "add-review";
    }


    @PostMapping("/add")
    public String postLeaveAReview(@RequestParam Long hotelId,
                                      @RequestParam Integer grade,
                                      @RequestParam String description,
                                      HttpServletRequest request, Authentication authentication, Model model){

        User user = (User) authentication.getPrincipal();
        this.reviewService.create(user.getId(),hotelId,grade,description);
      //  this.hotelService.updateOverralRating(hotelId,grade);
        return "redirect:/hotel-details/" + hotelId;
    }

}
