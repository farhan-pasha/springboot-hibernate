package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiTest {

    @RequestMapping("/HiTest")
    public String Hi(){
        return "Hello???";
    }
}