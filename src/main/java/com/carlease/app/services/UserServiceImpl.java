package com.carlease.app.services;

import com.carlease.app.models.User;
import com.carlease.app.repos.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User findCurrentUser() {
        //TODO: imagine there's a JWT token in some auth class that has the user details
        return userRepo.findByUsername("someUser");
    }
}
