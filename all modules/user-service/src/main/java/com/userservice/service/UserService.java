package com.userservice.service;

import com.userservice.entities.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    List<User> getAllUser();
    User getUser(String userId);

    User updateUser(String userId, User userDetails);
    void deleteUser(String userId);

}
