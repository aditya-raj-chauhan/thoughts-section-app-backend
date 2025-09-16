package com.devotion.thoughts.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: Replace with DB lookup
        if ("devotee".equals(username)) {
            return User.builder()
                    .username("devotee")
                    .password("$2a$10$7QshkEr2E2xAd6sclBtj4OLNQx9shkgY3xUktZiKEnMMyizfysh1y") // "password"
                    .roles("USER")
                    .build();
        } else if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password("$2a$10$7QshkEr2E2xAd6sclBtj4OLNQx9shkgY3xUktZiKEnMMyizfysh1y") // "password"
                    .roles("ADMIN")
                    .build();
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
