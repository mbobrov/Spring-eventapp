package com.eventapp.prototype.service;

import com.eventapp.prototype.domain.User;

import java.util.List;

public interface UserService {

    User create(User user);

    User save(User user);

    User getUser(Long userId);

    void deleteUser(Long userId);

    List<User> getList();

}
