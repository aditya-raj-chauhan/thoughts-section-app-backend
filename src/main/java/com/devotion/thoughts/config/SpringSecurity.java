package com.devotion.thoughts.config;

import com.devotion.thoughts.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {

    private final UserDetailsServiceImpl userDetailsService;

    public SpringSecurity(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Only ADMIN can manage users
                        .requestMatchers("/users/**").hasRole("ADMIN")

                        // USER and ADMIN can read quotes
                        .requestMatchers("/quotes/all/**").hasAnyRole("USER","ADMIN")

                        // USER can add/edit/delete their own quotes
                        .requestMatchers("/quotes/add/**").hasRole("USER")
                        .requestMatchers("/quotes/**").hasRole("USER")

                        // any other request authenticated
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .httpBasic(basic -> {});
        return http.build();
    }
}
