package com.petarpopovski.hotelreviews.web.controllers;

import com.petarpopovski.hotelreviews.model.Hotel;
import com.petarpopovski.hotelreviews.service.interfaces.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/home","/"})
public class HomeController {

    private final HotelService hotelService;


    public HomeController(HotelService hotelService) {
        this.hotelService = hotelService;
    }


    @GetMapping
    public String getHomePage(@RequestParam(required = false)String error, Model model){

        //  if there is some error, put it into the model in order to display it on frontend
        if(error !=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }

        //  get all hotels
        List<Hotel> hotels = this.hotelService.listAllHotels().stream().limit(4).collect(Collectors.toList());
        model.addAttribute("hotels",hotels);

        return "index";
    }
}
