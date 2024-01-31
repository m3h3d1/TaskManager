package com.mehedi.taskmanager.controller;

import com.mehedi.taskmanager.model.userdto.UserCreateDTO;
import com.mehedi.taskmanager.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreateDTO userCreateDTO) {
        userCreateDTO.setRole("USER");
        userService.create(userCreateDTO);
        return new ResponseEntity<>("Account successfully registered", HttpStatus.CREATED);
    }
}
