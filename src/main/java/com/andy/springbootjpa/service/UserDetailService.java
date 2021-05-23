package com.andy.springbootjpa.service;

import com.andy.springbootjpa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.findByEmail(username);
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), Collections.emptyList());
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Wrong user name");
        }
    }

}
