package com.andy.springbootjpa.controller;


import com.andy.springbootjpa.dto.UserDTO;
import com.andy.springbootjpa.exceptions.DuplicatedKeyException;
import com.andy.springbootjpa.exceptions.ResourceNotFoundException;
import com.andy.springbootjpa.model.User;
import com.andy.springbootjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) throws DuplicatedKeyException {
        // fields: name, email, password
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        try {
            user = userService.register(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedKeyException(e.getMessage() + "; Please check for duplicated keys.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(UserDTO.createDTO(user));
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) throws ResourceNotFoundException {
        // fields: email, password
        User user = userService.login(userDTO.getEmail(), userDTO.getPassword());
        if (user == null) {
            throw new ResourceNotFoundException("Email or Password incorrect!");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(UserDTO.createDTO(user));
        }
    }

}
