package com.pizzeria.barbaros.security;

import com.pizzeria.barbaros.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            logger.warn("Solicitud sin token de autenticación válido");
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        logger.info("Token recibido: {}", token);

        try {
            if (jwtUtil.validateToken(token)) {
                logger.info("Token válido. Extrayendo email...");
                String email = jwtUtil.extractEmail(token);
                logger.info("Email extraído del token: {}", email);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    logger.info("Cargando detalles del usuario desde UserDetailsService...");
                    UserDetails userDetails = userDetailService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    logger.info("Autenticación establecida para el usuario: {}", email);
                }
            } else {
                logger.warn("Token inválido");
            }
        } catch (Exception e) {
            logger.error("Error procesando el token JWT", e);
        }

        filterChain.doFilter(request, response);
    }
}
