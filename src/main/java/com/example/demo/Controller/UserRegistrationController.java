package com.example.demo.Controller;

import com.example.demo.service.UserService;
import com.example.demo.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    //registration.html -> th:object="${user}"
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDtoToClient(){
        return new UserRegistrationDto();//default constructor for UserRegistrationDto
    }

    //method handler to return the associated template(thymeleaf)
    @GetMapping
    public String showRegistrationForm(){
        return "registration";
    }

    @PostMapping
    //@ModelAttribute("user") contains data coming from front end form which needs to be binded with DTO object
    public String registerUserAccount(@ModelAttribute("user")UserRegistrationDto registrationDto) {
        userService.saveToDB(registrationDto);
        return "redirect:/registration?success";
    }

}
