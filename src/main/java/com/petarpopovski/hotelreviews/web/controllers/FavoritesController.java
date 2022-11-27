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
     *                      *
     *      ADD FAVORITE    *
     *                      *
     *                      **/
    @PostMapping("/add/{id}")
    public String addHotelToFavorites(@PathVariable Long id, HttpServletRequest request, Authentication authentication, Model model){
        try{
            User user = (User) authentication.getPrincipal();
            this.userService.addHotelToFavorites(user.getId(),id);
            return "redirect:/favorites";
        } catch (RuntimeException exception){
            return "redirect:/favorites?error="+ exception.getMessage();
        }
    }





    /**
     *                      *
     *         DELETE       *
     *                      *
     *                      **/
    @DeleteMapping("/delete/{id}")
    public String deleteWineFromShoppingCart(@PathVariable Long id, Authentication authentication, Model model){
        try {
            User user = (User) authentication.getPrincipal();
            this.userService.removeHotelFromFavorites(user.getId(),id);

            return "redirect:/favorites";
        } catch (RuntimeException exception){
            return "redirect:/favorites?error="+ exception.getMessage();
        }
    }

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
