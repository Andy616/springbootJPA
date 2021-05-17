package com.andy.springbootjpa.service;

import com.andy.springbootjpa.model.User;
import com.andy.springbootjpa.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User register(User user) {
        return userRepo.save(user);
    }

    public User login(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password);
    }


}
