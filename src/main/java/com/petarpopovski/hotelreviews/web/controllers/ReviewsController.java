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

    /**
     * [POST]
     * @desc Makes method call to the service layer for particular user liking particular review.
     * Also, attribute "liked" is set to session as boolean to check whether the current user has liked the review.
     * This is is implemented this way in order to know how many times the user has pressed the like button.
     * On every press, the session attribute is set to true or false respectively.
     * That way I can know whether to call the likeReview or removeLikeFromReview function.
     * @returns {String} hotel-details view
     */
    @PostMapping("/likeReview/{reviewId}")
    public String likeReview(@PathVariable Long reviewId, @RequestParam Long hotelId,
                             HttpServletRequest request, Authentication authentication, Model model){
        try{
            Hotel hotel = this.hotelService.findById(hotelId).get();
            model.addAttribute("hotel",hotel);
            User user = (User) authentication.getPrincipal();
            session = request.getSession();

            /** if the review is already liked, then on the next press unlike it **/
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

    /**
     * [POST]
     * @desc Makes method call to the service layer for particular user disliking particular review.
     * Also, attribute "disliked" is set to session as boolean to check whether the current user has disliked the review.
     * This is is implemented this way in order to know how many times the user has pressed the dislike button.
     * On every press, the session attribute is set to true or false respectively.
     * That way I can know whether to call the dislikeReview or removeDislikeFromReview function.
     * @returns {String} hotel-details view
     */
    @PostMapping("/dislikeReview/{reviewId}")
    public String dislikeReview(@PathVariable Long reviewId, Long hotelIdDislike,HttpServletRequest request, Authentication authentication, Model model){
        try{

            Hotel hotel = this.hotelService.findById(hotelIdDislike).get();
            model.addAttribute("hotel",hotel);
            User user = (User) authentication.getPrincipal();
            session = request.getSession();

            /** if the review is already disliked, then on the next press remove the dislike **/
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


    /**
     * [GET]
     * @desc Populates the model with all the users that liked the particular review with making method call to the service layer.
     * @returns {String} all-likes view
     */
    @GetMapping("/allLikes/{reviewId}")
    public String showAllReviewLikes(@PathVariable Long reviewId,
                                   HttpServletRequest request, Authentication authentication, Model model){


        User user = (User) authentication.getPrincipal();
        Optional<Review> review =this.reviewService.findById(reviewId);
        List<User> allLikes = review.get().getUsersLiked().stream().collect(Collectors.toList());

        model.addAttribute("review",review);
        model.addAttribute("usersLiked", allLikes);
        return "all-likes";
    }

    /**
     * [GET]
     * @desc Populates the model with all the users that disliked the particular review with making method call to the service layer.
     * @returns {String} all-dislikes view
     */
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

    /**
     * [GET]
     * @desc Populates the model with the information for the hotel and the form for leaving a review and returning the view.
     * @returns {String} add-review view
     */
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


    /**
     * [POST]
     * @desc Creating a review with the provided information from the input form by making call to the service layer.
     * The overral rating for the hotel is made on every review creation.
     * @returns {String} hotel-details view
     */
    @PostMapping("/add")
    public String postLeaveAReview(@RequestParam Long hotelId,
                                      @RequestParam Integer grade,
                                      @RequestParam String description,
                                      HttpServletRequest request, Authentication authentication, Model model){

        User user = (User) authentication.getPrincipal();
        this.reviewService.create(user.getId(),hotelId,grade,description);
        return "redirect:/hotel-details/" + hotelId;
    }

}
