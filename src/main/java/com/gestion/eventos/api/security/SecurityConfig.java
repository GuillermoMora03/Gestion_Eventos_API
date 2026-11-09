package com.gestion.eventos.api.security;

import com.gestion.eventos.api.security.jwt.JwtAuthEntryPoint;
import com.gestion.eventos.api.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //Lo deshabilitamos porque vamos a trabajar con token, podria llegar a interferir si la dejamos activada
                .exceptionHandling(exception -> //Configura como spring va a gestionar las excepciones relaciondas con la seguridad
                        exception.authenticationEntryPoint(jwtAuthEntryPoint)
                )
                .sessionManagement(session -> //Configura como spring security maneja las sesiones HTTP
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Con esta arquitectura (STATELESS) el sevidor no recuerda al usuario entre peticiones,
                        ) // es decir, no hay una sesion en el servidor
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/v1/auth/**").permitAll() // Con esto le decimos que cualquier ruta que empiece con api/v1/auth no necesita autenticacion
                                .anyRequest().authenticated() // En cambio aqui le decimos que las demas rutas necesitan autenticación
                );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); //Es una forma de insertar un filtro personalizado
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration  authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
