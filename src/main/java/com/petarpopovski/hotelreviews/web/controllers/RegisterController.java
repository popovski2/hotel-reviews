package com.petarpopovski.hotelreviews.web.controllers;

import com.petarpopovski.hotelreviews.filter.CustomUsernamePasswordAuthenticationProvider;
import com.petarpopovski.hotelreviews.model.exceptions.InvalidUserArgumentsException;
import com.petarpopovski.hotelreviews.model.exceptions.PasswordsDoNotMatchException;
import com.petarpopovski.hotelreviews.model.exceptions.UserAlreadyExistsException;
import com.petarpopovski.hotelreviews.service.interfaces.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final CustomUsernamePasswordAuthenticationProvider authenticationProvider;


    public RegisterController(UserService userService, CustomUsernamePasswordAuthenticationProvider authenticationProvider) {
        this.userService = userService;
        this.authenticationProvider = authenticationProvider;
    }


    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "register";
    }


    @PostMapping
    public String register(HttpServletRequest request,
                           @RequestParam String username,
                           @RequestParam String name,
                           @RequestParam String password){
        try {
            this.userService.registerAsRegular(username,name,password);

            // AUTO LOGIN AFTER REGISTRATION
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
            System.out.println(" authenticationToken credentials: "+authenticationToken.getCredentials() +" authenticaionToken getDetails: "+authenticationToken.getDetails());
            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            System.out.println("after authenticationToken.setDetails(newWebAuthenticationDetails(request)" + authenticationToken.getDetails());
            Authentication authentication = this.authenticationProvider.authenticate(authenticationToken);
            System.out.println("Authentication getDetails after authenticate(authenticationToken)" + authentication.getDetails());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/home";
        } catch (UserAlreadyExistsException exception){
            return "redirect:/register?error=" + exception.getMessage();
        }
    }






}
