package com.pizzeria.barbaros.dto.response;
import com.pizzeria.barbaros.util.Role;


public record UserResponseDto(Long id, String email, Role role) {
}
