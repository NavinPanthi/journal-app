package com.panthi.journalApp.controller;

import com.panthi.journalApp.entity.User;
import com.panthi.journalApp.repository.UserRepository;
import com.panthi.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @PutMapping
    public ResponseEntity<User> updateUserByUsername(@RequestBody User newUser) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User oldUser = userService.findByUserName(userName);
            oldUser.setUserName(newUser.getUserName());
            oldUser.setPassword(newUser.getPassword());
            userService.saveNewUSer(oldUser);
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
