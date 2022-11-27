package com.petarpopovski.hotelreviews.web.controllers;

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


    @GetMapping
    public String getHotelsPage(@RequestParam(required = false)String error, Model model){

        //  if there is some error, put it into the model in order to display it on frontend
        if(error !=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }

        //  get all hotels
        List<Hotel> hotels = this.hotelService.listAllHotels();
        model.addAttribute("hotels",hotels);

        return "hotels";
    }

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



    /**                             *
     *                              *
     *          GET EDIT HOTEL      *
     *                              *
     * **/
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

    /**                             *
     *                              *
     *          GET ADD HOTEL       *
     *                              *
     * **/


    @GetMapping("/admin/add-form")
    public String addHotelPage(Model model){
                return "add-hotel";
    }

    /**                             *
     *                              *
     *         POST ADD HOTEL       *
     *                              *
     * **/

    @PostMapping("/admin/add")
    public String saveHotel(  @RequestParam(required = false) Long id,
                              @RequestParam String name,
                              @RequestParam String address,
                              @RequestParam String imageUrl,
                              @RequestParam String description
                              ) {
        if (id != null) {
            this.hotelService.update(id,name,address,imageUrl,description);
        } else {
            this.hotelService.create(name,address,imageUrl,description);
        }

        return "redirect:/hotels";
    }

    /**                             *
     *                              *
     *         DELETE HOTEL         *
     *                              *
     * **/

    @DeleteMapping("/admin/delete/{id}")
    public String deleteHotel(@PathVariable Long id){
        this.hotelService.delete(id);
        return "redirect:/hotels";
    }

}
