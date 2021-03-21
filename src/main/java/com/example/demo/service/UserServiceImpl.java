package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;

//Service annotation internally uses Component annotation
@Service
public class UserServiceImpl implements UserService{

    //Inject UserRepository Interface
    /*
    Injection can be obtained by using the following field based injection (Not recommended), use constructor based injection instead!

    @Autowired
    private UserRepository userRepository;
    */

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveToDB(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(), registrationDto.getPassword(), Arrays.asList(new Role("ROLE_USER")));

        //save to DataBase (CrudRepository save() method)
        return userRepository.save(user);
    }
}
