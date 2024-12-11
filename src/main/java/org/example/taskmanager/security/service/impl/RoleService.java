package org.example.taskmanager.security.service.impl;

import org.example.taskmanager.security.model.Role;
import org.example.taskmanager.security.model.RoleEnum;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findByName(RoleEnum name);

}
