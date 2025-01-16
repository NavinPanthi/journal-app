package com.panthi.journalApp.controller;


import com.panthi.journalApp.entity.User;
import com.panthi.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @GetMapping("/health-check")
    public String healthCheck(){
        return "okay";
    }

    @PostMapping("/register")
    public void createUser(@RequestBody User user){
        userService.saveNewUSer(user);
    }
}

