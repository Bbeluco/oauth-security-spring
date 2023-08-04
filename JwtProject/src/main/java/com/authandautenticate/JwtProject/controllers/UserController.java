package com.authandautenticate.JwtProject.controllers;

import com.authandautenticate.JwtProject.DTO.UserDTO;
import com.authandautenticate.JwtProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String helloWorld(){
        return "Hello World";
    }

    @PostMapping
    public String saveUserInDatabase(@RequestBody UserDTO userDTO){
        return userService.saveDTOInDB(userDTO);
    }
}
