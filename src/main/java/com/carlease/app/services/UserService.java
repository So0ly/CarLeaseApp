package com.carlease.app.services;

import com.carlease.app.models.User;

public interface UserService {
    User findByUsername(String username);
    User findCurrentUser();
}
