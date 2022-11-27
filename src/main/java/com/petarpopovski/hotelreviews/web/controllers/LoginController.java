package com.petarpopovski.hotelreviews.web.controllers;

import com.petarpopovski.hotelreviews.model.User;
import com.petarpopovski.hotelreviews.model.exceptions.InvalidUserArgumentsException;
import com.petarpopovski.hotelreviews.service.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {


    /** we need this in order to put the logged in user into session **/
    private HttpSession session=null;


    private final UserService userService;


    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getLoginPage(@RequestParam(required = false) String error, Model model){
        if(error!=null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "login");
        return "login";
    }


    public String login(HttpServletRequest request, Model model){
        User user = null;

        try {
            user = this.userService.login(request.getParameter("username"), request.getParameter("password"));
            session = request.getSession();
            session.setAttribute("user",user);
            session.setAttribute("username",request.getParameter("username"));
            System.out.println("The current logged in user is: "+ session.getAttribute("username"));

            return "redirect:/home";
        }
        catch (InvalidUserArgumentsException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }
    }





}
