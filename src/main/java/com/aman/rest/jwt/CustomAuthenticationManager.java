package com.aman.rest.jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.aman.rest.user.User;
import com.aman.rest.user.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

public class CustomAuthenticationManager implements AuthenticationManager {
    private List<AuthenticationProvider> providers;
    protected MessageSourceAccessor messages;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public CustomAuthenticationManager(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationProvider... providers) {
        this(userRepository, passwordEncoder, Arrays.asList(providers));
    }

    public CustomAuthenticationManager(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, List<AuthenticationProvider> providers) {
        this.messages = SpringSecurityMessageSource.getAccessor();
        Assert.notNull(providers, "providers list cannot be null");
        this.providers = new ArrayList<>(providers);
        this.bCryptPasswordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void addProvider(AuthenticationProvider provider) {
        this.providers.add(provider);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userRepository.findByUsername(authentication.getName()).orElse(new User("non-existent-user", "password"));
        if (bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())){
            return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword());
        }
        throw new BadCredentialsException("Incorrect password");
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}