package com.mehedi.taskmanager.controller;

import com.mehedi.taskmanager.model.userdto.UserCreateDTO;
import com.mehedi.taskmanager.model.userdto.UserReadDTO;
import com.mehedi.taskmanager.service.UserService;
import com.mehedi.taskmanager.utilities.constants.APIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(APIConstants.SIGN_UP)
    public ResponseEntity<?> register(@RequestBody UserCreateDTO userCreateDTO) {
        userCreateDTO.setRole("USER");
        userService.create(userCreateDTO);
        return new ResponseEntity<>("Account successfully registered", HttpStatus.CREATED);
    }
    @PostMapping(APIConstants.SIGN_UP_ADMIN)
    public ResponseEntity<?> registerAsAdmin(@RequestBody UserCreateDTO userCreateDTO, @RequestParam String permissionToken) {
        if(permissionToken == null || !permissionToken.equals("123456"))
            return new ResponseEntity<>("Invalid permission token!", HttpStatus.BAD_REQUEST);
        userCreateDTO.setRole("ADMIN");
        userService.create(userCreateDTO);
        return new ResponseEntity<>("Account successfully registered", HttpStatus.CREATED);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserReadDTO>> getAllUser(){
        return ResponseEntity.ok(userService.readAll());
    }
}
