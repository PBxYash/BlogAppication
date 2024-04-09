package com.example.BlogApp.Service.impl;

import com.example.BlogApp.Entity.User;
import com.example.BlogApp.Repository.UserRepository;
import com.example.BlogApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    @Override
    public User findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);

    }

    @Override
    public User FindByUserID(long id) {
        User user = userRepository.findById(id).get();
        return user;
    }
}
