package com.example.demo.service;

//We pass the DTO object here,
//DTO objects are used to pass info from server to client and vice versa.

import com.example.demo.model.User;
import com.example.demo.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    //Pass the Dto class
    User saveToDB(UserRegistrationDto registrationDto);
}
