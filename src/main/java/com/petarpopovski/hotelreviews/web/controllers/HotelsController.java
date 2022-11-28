package com.petarpopovski.hotelreviews.web.controllers;

import com.petarpopovski.hotelreviews.model.Geolocation;
import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.model.Review;
import com.petarpopovski.hotelreviews.service.interfaces.HotelService;
import com.petarpopovski.hotelreviews.service.interfaces.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelsController {


    private final HotelService hotelService;
    private final ReviewService reviewService;


    public HotelsController(HotelService hotelService, ReviewService reviewService) {
        this.hotelService = hotelService;
        this.reviewService = reviewService;
    }


    /**
     * [GET]
     * @desc Populates the model with all hotels from the database sorted by name in ascending order and returns the hotels view.
     * Both the REGULAR_USER and ADMIN users have the rights to execute this function.
     * @returns {String} hotels view
     */
    @GetMapping
    public String getHotelsPage(@RequestParam(required = false)String error, Model model){

        if(error !=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }

        List<Hotel> hotels = this.hotelService.listAllHotels(); // make call to the service layer
        model.addAttribute("hotels",hotels);    // populate the model with the hotels
        return "hotels";
    }


    /**
     * [GET]
     * @desc Populates the model with the result from the search and returns the search result view.
     * Both the REGULAR_USER and ADMIN users have the rights to execute this function.
     * @returns {String} search-result view
     */
    @GetMapping("/search")
    public String searchHotels(@RequestParam String searchText, Model model){

        try {
            List<Hotel> searchHotels = this.hotelService.search(searchText);
            model.addAttribute("searchHotels", searchHotels);
            return "search-result";
        }
        catch (Exception e ){
            model.addAttribute("hotelError","Hotel does not exist");
            return "redirect:/hotels";
        }

    }


    /**
     * [GET]
     * @desc Populates the model with the hotel's details and returning the page with edit form for the hotel.
     * Only the admin has the exclusive right to execute this function.
     * @returns {String} add-hotel view or redirect to hotels with error message
     */
    @GetMapping("/admin/edit-form/{id}")
    public String editHotelPage(@PathVariable Long id, Model model){
        if(this.hotelService.findById(id).isPresent()){

            Hotel hotel = this.hotelService.findById(id).get();
            List<Review> reviewsForHotel = this.reviewService.listAllReviewsByHotel(hotel.getId());
            model.addAttribute("hotel", hotel);
            model.addAttribute("reviews", reviewsForHotel);
            return "add-hotel";
        }
        return "redirect:/hotels?error=HotelNotFound";
    }



    /**
     * [GET]
     * @desc Returns the page with form for adding the hotel.
     * Only the admin has the exclusive right to execute this function.
     * @returns {String} add-hotel view
     */
    @GetMapping("/admin/add-form")
    public String addHotelPage(Model model){
                return "add-hotel";
    }


    /**
     * [POST]
     * @desc Creates a hotel object with the provided details or updates depending on whether the id of the hotel is sent in the request.
     * Only the admin has the exclusive right to execute this function.
     * @returns {String} redirect to hotels view
     */
    @PostMapping("/admin/add")
    public String saveHotel(  @RequestParam(required = false) Long id,
                              @RequestParam String name,
                              @RequestParam String address,
                              @RequestParam String imageUrl,
                              @RequestParam String description,
                              @RequestParam Double longitude,
                              @RequestParam Double latitude

                              ) {
        if (id != null) {
            this.hotelService.update(id,name,address,imageUrl,description, new Geolocation(longitude,latitude));
        } else {
            this.hotelService.create(name,address,imageUrl,description,new Geolocation(longitude,latitude));
        }

        return "redirect:/hotels";
    }

    /**
     * [DELETE]
     * @desc Deletes hotel with the provided id. Only the admin has the exclusive right to execute this function.
     * @returns {String} redirect to hotels view
     */
    @DeleteMapping("/admin/delete/{id}")
    public String deleteHotel(@PathVariable Long id){
        this.hotelService.delete(id);
        return "redirect:/hotels";
    }

}
