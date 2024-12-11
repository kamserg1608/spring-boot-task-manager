package org.example.taskmanager.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.taskmanager.security.model.User;
import org.example.taskmanager.security.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    @Transactional(readOnly = true)
    public boolean existsByLogin(String username) {
        return userRepository.existsByLogin(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
