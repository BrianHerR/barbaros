package com.pizzeria.barbaros.controller;

import com.pizzeria.barbaros.dto.response.UserResponseDto;
import com.pizzeria.barbaros.service.UserService;
import com.pizzeria.barbaros.service.impl.UserDetailsImpl;
import com.pizzeria.barbaros.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getAuthenticatedUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        logger.info("Solicitud de datos del usuario autenticado: {}", userDetails.getUsername());

        return userService.getUserByEmail(userDetails.getUsername())
                .map(user -> {
                    logger.info("Usuario autenticado encontrado: {} (ID: {})", user.getEmail(), user.getId());
                    return ResponseEntity.ok(new UserResponseDto(user.getId(), user.getEmail(), user.getRole()));
                })
                .orElseGet(() -> {
                    logger.warn("No se encontró información del usuario autenticado.");
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        logger.info("Solicitud para obtener todos los usuarios por {}", userDetails.getUsername());

        if (!userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            logger.warn("Acceso denegado: el usuario {} no tiene permisos de administrador.", userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<UserResponseDto> users = userService.getAllUsers()
                .stream()
                .map(user -> new UserResponseDto(user.getId(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());

        logger.info("Se encontraron {} usuarios.", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        logger.info("Solicitud para obtener usuario con ID: {} por {}", id, userDetails.getUsername());

        return userService.getUserById(id)
                .map(user -> {
                    if (!userDetails.getAuthorities().stream()
                            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN") || user.getEmail().equals(userDetails.getUsername()))) {
                        logger.warn("Acceso denegado: el usuario {} intentó acceder a un perfil no autorizado.", userDetails.getUsername());
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                    }

                    logger.info("Usuario encontrado: {} (ID: {})", user.getEmail(), user.getId());
                    return ResponseEntity.ok(new UserResponseDto(user.getId(), user.getEmail(), user.getRole()));
                })
                .orElseGet(() -> {
                    logger.warn("Usuario con ID {} no encontrado.", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        logger.info("Solicitud para eliminar usuario con ID: {} por {}", id, userDetails.getUsername());

        if (!userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            logger.warn("Acceso denegado: el usuario {} intentó eliminar un usuario sin permisos.", userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        boolean deleted = userService.deleteUserById(id);
        if (deleted) {
            logger.info("Usuario con ID {} eliminado con éxito por {}", id, userDetails.getUsername());
            return ResponseEntity.ok("Usuario eliminado con éxito.");
        } else {
            logger.warn("No se pudo eliminar el usuario con ID {}. No encontrado.", id);
            return ResponseEntity.notFound().build();
        }
    }
}
