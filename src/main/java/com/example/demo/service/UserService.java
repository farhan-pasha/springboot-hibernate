package com.example.demo.service;

//We pass the DTO object here,
//DTO objects are used to pass info from server to client and vice versa.

import com.example.demo.model.User;
import com.example.demo.web.dto.UserRegistrationDto;


public interface UserService {
    //Pass the Dto class
    User saveToDB(UserRegistrationDto registrationDto);
}
