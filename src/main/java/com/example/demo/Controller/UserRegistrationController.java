package com.example.demo.Controller;

import com.example.demo.service.ICaptchaService;
import com.example.demo.service.InvalidReCaptchaException;
import com.example.demo.service.ReCaptchaInvalidException;
import com.example.demo.service.UserService;
import com.example.demo.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private ICaptchaService captchaService;

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
    @ResponseBody
    //@ModelAttribute("user") contains data coming from front end form which needs to be binded with DTO object
    public String registerUserAccount(@ModelAttribute("user")UserRegistrationDto registrationDto, HttpServletRequest request) throws InvalidReCaptchaException, ReCaptchaInvalidException {
        //Shouldnt this be in a BEAN? request
        captchaService.processResponse(request);
        userService.saveToDB(registrationDto);
        return "success";
    }

}
