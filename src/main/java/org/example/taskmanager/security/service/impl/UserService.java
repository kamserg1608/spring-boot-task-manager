package org.example.taskmanager.security.service.impl;

import org.example.taskmanager.security.model.User;

public interface UserService {

    User save(User user);

    boolean existsByLogin(String username);

    boolean existsByEmail(String email);
}
