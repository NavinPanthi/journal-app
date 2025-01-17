package com.panthi.journalApp.controller;

import com.panthi.journalApp.entity.User;
import com.panthi.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/all-user")
    public ResponseEntity<?> getAll() {
        List<User> user = userService.getAll();
        return !user.isEmpty() ?
                new ResponseEntity<>(user, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminUser(@RequestBody User user) {
        User user1 = userService.saveAdminUser(user);
        return user1 != null ?
                new ResponseEntity<>(user, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/provide-admin-role/{userName}")
    public ResponseEntity<?> provideAdminRole(@PathVariable String userName){
        try{
            User user = userService.findByUserName(userName);
            user.getRoles().add("ADMIN");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}




