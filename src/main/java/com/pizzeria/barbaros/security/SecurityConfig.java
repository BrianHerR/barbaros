package com.pizzeria.barbaros.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Inicializando PasswordEncoder...");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        logger.info("Configurando AuthenticationManager...");
        return authenticationConfiguration.getAuthenticationManager();
    }

   @Bean
   public AuthenticationProvider authenticationProvider() {
       logger.info("Configurando AuthenticationProvider...");

       DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
       authenticationStrategy.setPasswordEncoder(passwordEncoder());

       try {
           authenticationStrategy.setUserDetailsService(null);
           logger.info("UserDetailsService asignado correctamente.");
       } catch (Exception e) {
           logger.error("Error al asignar UserDetailsService: {}", e.getMessage(), e);
       }

       logger.info("AuthenticationProvider configurado con éxito.");
       return authenticationStrategy;
   }
}
