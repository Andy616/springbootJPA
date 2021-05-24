package com.andy.springbootjpa.controller;


import com.andy.springbootjpa.dto.UserDTO;
import com.andy.springbootjpa.exceptions.DuplicatedKeyException;
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
            throw new DuplicatedKeyException("This email has already been taken.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(UserDTO.createDTO(user));
    }

}
