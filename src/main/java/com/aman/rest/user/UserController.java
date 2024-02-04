package com.aman.rest.user;

import com.aman.rest.jwt.CustomAuthenticationManager;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private CustomAuthenticationManager customAuthenticationManager;

    @PostMapping("/register")
    public User createUser(@Valid @RequestBody User user) {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(
            new InMemoryUserDetailsManager(
                org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities("read")
                    .roles("USER")
                    .build()
            )
        );
        customAuthenticationManager.addProvider(provider);
        return user;
    }
}
