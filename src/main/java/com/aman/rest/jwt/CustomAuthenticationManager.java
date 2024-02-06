package com.aman.rest.jwt;

import com.aman.rest.user.User;
import com.aman.rest.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userRepository.findByUsername(authentication.getName()).orElse(new User("non-existent-user", "password"));
        if (bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())){
            return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword());
        }
        throw new BadCredentialsException("Incorrect password");
    }
}