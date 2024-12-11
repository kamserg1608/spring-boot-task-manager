package org.example.taskmanager.security.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.taskmanager.security.model.Role;
import org.example.taskmanager.security.model.RoleEnum;
import org.example.taskmanager.security.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Optional<Role> findByName(RoleEnum name) {
        return roleRepository.findByName(name);
    }
}
