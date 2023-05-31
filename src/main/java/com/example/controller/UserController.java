package com.example.controller;

import com.example.entity.UserEntity;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object>getAllUser(){
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object>getUserbyEmail(@PathVariable String email){
        return new ResponseEntity(userService.getByEmail(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object>addUser(@Valid @RequestBody UserEntity user){
        userService.addUser(user);
        return new ResponseEntity("User Created.", HttpStatus.CREATED);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Object>deleteUser(@PathVariable String email){
        userService.deleteUser(email);
        return new ResponseEntity("User deleted.", HttpStatus.NO_CONTENT);

    }
}
