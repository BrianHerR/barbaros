package com.pizzeria.barbaros.service.impl;

import com.pizzeria.barbaros.model.UserEntity;
import com.pizzeria.barbaros.repository.UserRepository;
import com.pizzeria.barbaros.service.UserService;
import com.pizzeria.barbaros.util.Role;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserEntity> findByEmail(String email) {

        logger.info("Buscando usuario con email: {}", email);

        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            logger.info("Usuario encontrado: {}", email);
        } else {
            logger.warn("No se encontró usuario con email: {}", email);
        }

        return user;
    }

    public boolean existsByEmail(String email) {

        logger.info("Verificando existencia de usuario con email: {}", email);

        boolean exists = userRepository.existsByEmail(email);

        if (exists) {
            logger.warn("El email {} ya está registrado", email);
        } else {
            logger.info("El email {} está disponible para registro", email);
        }

        return exists;
    }

    @Override
    public UserEntity registerUser(String email, String password, Role role) {

        logger.info("Registrando nuevo usuario con email: {}", email);

        if (existsByEmail(email)) {
            logger.error("Intento de registro con email duplicado: {}", email);
            throw new IllegalArgumentException("El email ya está en uso.");
        }

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        UserEntity savedUser = userRepository.save(user);
        logger.info("Usuario registrado con éxito: {}", savedUser.getEmail());

        return savedUser;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        logger.info("Obteniendo todos los usuarios");

        List<UserEntity> users = userRepository.findAll();

        logger.info("Se encontraron {} usuarios", users.size());

        return users;
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        logger.info("Buscando usuario con ID: {}", id);

        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isPresent()) {
            logger.info("Usuario encontrado con ID: {}", id);
        } else {
            logger.warn("No se encontró usuario con ID: {}", id);
        }

        return user;
    }

    @Override
    public boolean deleteUserById(Long id) {
        logger.info("Intentando eliminar usuario con ID: {}", id);

        if (!userRepository.existsById(id)) {
            logger.warn("No se pudo eliminar. Usuario con ID: {} no encontrado", id);
            return false;
        }

        try {
            userRepository.deleteById(id);
            logger.info("Usuario con ID: {} eliminado con éxito", id);
            return true;
        } catch (Exception e) {
            logger.error("Error al eliminar usuario con ID: {}. Error: {}", id, e.getMessage());
            return false;
        }
    }
}
