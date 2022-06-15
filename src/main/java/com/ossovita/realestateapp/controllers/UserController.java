package com.ossovita.realestateapp.controllers;

import com.ossovita.realestateapp.business.abstracts.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/create-dummy-user-request")
    public ResponseEntity<String> createDummyUserRequest(@RequestParam int piece) {
        userService.createDummyUserRequest(piece);
        return ResponseEntity.ok("Your request has been added to queue");
    }
}
