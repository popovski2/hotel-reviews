package com.petarpopovski.hotelreviews.web.controllers;

import com.petarpopovski.hotelreviews.model.User;
import com.petarpopovski.hotelreviews.model.exceptions.UserAlreadyExistsException;
import com.petarpopovski.hotelreviews.service.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    public UsersController(UserService userService) {
        this.userService = userService;
    }




    /**
     * [GET]
     * @desc Populate the model with all the users from the databaset.
     * Only the admin has the exclusive right to execute this function.
     * @returns {String} users view
     */
    @GetMapping
    public String getUsersPage(@RequestParam(required = false)String error, Model model){
        if(error!= null && !error.isEmpty()){
            model.addAttribute("hasError",error);
            model.addAttribute("error",error);
        }
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users",users);
        return "users";
    }

    /**
     * [GET]
     * @desc Returns the page with form for adding new administrator.
     * Only the admin has the exclusive right to execute this function.
     * @returns {String} add-administrator view
     */
    @GetMapping("/admin/addAdministrator")
    public String addAdministratorPage(){
        return "add-administrator";
    }


    /**
     * [POST]
     * @desc Populate the model with all information needed for creating new administrator.
     * Only the admin has the exclusive right to execute this function.
     * @returns {String} users view
     */
    @PostMapping("/admin/add")
    public String addAdministrator(@RequestParam String email,
                                   @RequestParam String displayName,
                                   @RequestParam String password){

    try {
    this.userService.registerAsAdministrator(email, displayName, password);
    }
    catch (UserAlreadyExistsException exception){
    return "redirect:/users/addAdministrator?error=" + exception.getMessage();
    }
        return "redirect:/users";

    }


    /**
     * [DELETE]
     * @desc Deletes an user from the database.
     * Only the admin has the exclusive right to execute this function.
     * @returns {String} users view
     */
    @DeleteMapping("/admin/delete/{userId}")
    public String deleteUser(@PathVariable Long userId){
        this.userService.delete(userId);
        return "redirect:/users";
    }
}
