package com.pizzeria.barbaros.controller;

import com.pizzeria.barbaros.dto.request.AuthRequestDto;
import com.pizzeria.barbaros.dto.request.RegisterRequestDto;
import com.pizzeria.barbaros.dto.response.AuthResponseDto;
import com.pizzeria.barbaros.model.UserEntity;
import com.pizzeria.barbaros.security.JwtUtil;
import com.pizzeria.barbaros.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDto request) {
        logger.info("Intento de registro de usuario: {}", request.getEmail());

        if (userService.existsByEmail(request.getEmail())) {
            logger.warn("Intento de registro con email ya registrado: {}", request.getEmail());
            return ResponseEntity.badRequest().body("El email ya está en uso.");
        }

        UserEntity user = userService.registerUser(request.getEmail(), request.getPassword(), request.getRole());

        logger.info("Usuario registrado exitosamente: {}", user.getEmail());
        return ResponseEntity.ok("Usuario registrado con éxito.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto request) {
        logger.info("Intento de inicio de sesión para: {}", request.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            UserEntity user = userService.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getId());

            logger.info("Inicio de sesión exitoso para: {}", request.getEmail());
            return ResponseEntity.ok(new AuthResponseDto(token));

        } catch (Exception e) {
            logger.error("Error de autenticación para {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body(new AuthResponseDto("Credenciales incorrectas."));
        }
    }
}
