package com.example.blogbackend.controllers;

import com.example.blogbackend.models.AppUser;
import com.example.blogbackend.models.AuthRequest;
import com.example.blogbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@RequestBody AppUser user){
        return this.userService.addUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody AuthRequest request){return this.userService.signIn(request);}

}
