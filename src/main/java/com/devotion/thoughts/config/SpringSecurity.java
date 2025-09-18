package com.devotion.thoughts.config;

import com.devotion.thoughts.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder; // ✅ injected from PasswordConfig

    public SpringSecurity(UserDetailsServiceImpl userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder); // ✅ use injected encoder
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ✅ Public endpoints
                        .requestMatchers("/users/adduser").permitAll()
                        .requestMatchers("/users/all").hasRole("ADMIN")

                        // ✅ Quotes endpoints
                        .requestMatchers("/quotes/add/**").hasRole("USER")
                        .requestMatchers("/quotes/all/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/quotes/**").hasRole("USER")

                        // ✅ Admin only
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // ✅ All others
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout.permitAll()); // removed formLogin()

        return http.build();
    }
}
