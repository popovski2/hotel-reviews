package com.petarpopovski.hotelreviews.web.controllers;

import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.model.User;
import com.petarpopovski.hotelreviews.service.interfaces.HotelService;
import com.petarpopovski.hotelreviews.service.interfaces.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/favorites")
public class FavoritesController {

    private final HotelService hotelService;
    private final UserService userService;


    public FavoritesController(HotelService hotelService, UserService userService) {
        this.hotelService = hotelService;
        this.userService = userService;
    }

    /**
     * [GET]
     * @desc Returns the favorites page with the model populated with the favorite hotels of the logged in user
     * @returns {String} favorites view with or without error message
     */
    @GetMapping
    public String getFavoritesPage(@RequestParam(required = false)String error,
                                      HttpServletRequest request,
                                      Model model){

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        String email = request.getRemoteUser();
        User user = this.userService.getUserByEmail(email);
        Set<Hotel> favorites = user.getFavorites();

        model.addAttribute("favorites",favorites);
        return "favorites";
    }




    /**
     * [POST]
     * @desc Adds Hotel object with provided id to the favorites list of the logged in user
     * @returns {String} favorites view with or without error message
     */
    @PostMapping("/add/{id}")
    public String addHotelToFavorites(@PathVariable Long id, HttpServletRequest request, Authentication authentication, Model model){
        try{
            User user = (User) authentication.getPrincipal();
            System.out.println("User " + user.getDisplayName());
            this.userService.addHotelToFavorites(user.getId(),id);
            System.out.println("hotel is added to favorites " + id);

            return "redirect:/favorites";
        } catch (RuntimeException exception){
            return "redirect:/favorites?error="+ exception.getMessage();
        }
    }



    /**
     * [DELETE]
     * @desc Deletes the Hotel object with provided id from the favorites list of the logged in user
     * @returns {String} favorites view with or without error message
     */
    @DeleteMapping("/delete/{id}")
    public String deleteHotelFromFavorites(@PathVariable Long id, Authentication authentication, Model model){
        try {
            User user = (User) authentication.getPrincipal();
            this.userService.removeHotelFromFavorites(user.getId(),id);

            return "redirect:/favorites";
        } catch (RuntimeException exception){
            return "redirect:/favorites?error="+ exception.getMessage();
        }
    }


    /**
     * [DELETE]
     * @desc Deletes all the Hotel objects from the favorites list of the logged in user
     * @returns {String} favorites view with or without error message
     */
    @DeleteMapping("/emptyFavorites")
    public String emptyFavorites(Authentication authentication, Model model){
        try{
            User user = (User) authentication.getPrincipal();
            this.userService.removeAllFromFavorites(user.getId());
            return "redirect:/favorites";
        }catch (RuntimeException exception){
            return "redirect:/favorites?error="+ exception.getMessage();
        }
    }

}
