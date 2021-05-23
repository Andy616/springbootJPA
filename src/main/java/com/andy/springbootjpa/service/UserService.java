package com.andy.springbootjpa.service;

import com.andy.springbootjpa.model.User;
import com.andy.springbootjpa.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User register(User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(newUser);
    }

    public User login(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password);
    }


}
