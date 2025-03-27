package com.pizzeria.barbaros.service;

import com.pizzeria.barbaros.model.UserEntity;
import com.pizzeria.barbaros.util.Role;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    UserEntity registerUser(String email, String password, Role role);

    List<UserEntity> getAllUsers();

    Optional<UserEntity> getUserById(Long id);

    boolean deleteUserById(Long id);
}
