package com.aman.rest.jwt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

public class CustomAuthenticationManager implements AuthenticationManager {
    private List<AuthenticationProvider> providers;
    protected MessageSourceAccessor messages;

    public CustomAuthenticationManager(AuthenticationProvider... providers) {
        this(Arrays.asList(providers));
    }

    public CustomAuthenticationManager(List<AuthenticationProvider> providers) {
        this.providers = Collections.emptyList();
        this.messages = SpringSecurityMessageSource.getAccessor();
        Assert.notNull(providers, "providers list cannot be null");
        this.providers = providers;
    }

    public void addProvider(AuthenticationProvider provider) {
        this.providers.add(provider);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> toTest = authentication.getClass();
        AuthenticationException lastException = null;
        Authentication result = null;

        for (AuthenticationProvider provider : this.providers) {
            if (provider.supports(toTest)) {
                try {
                    result = provider.authenticate(authentication);
                    if (result != null) {
                        this.copyDetails(authentication, result);
                        break;
                    }
                } catch (InternalAuthenticationServiceException | AccountStatusException var14) {
                    throw var14;
                } catch (AuthenticationException var15) {
                    lastException = var15;
                }
            }
        }

        if (result != null) {
            if (result instanceof CredentialsContainer credentialsContainer) {
                credentialsContainer.eraseCredentials();
            }

            return result;
        } else {
            if (lastException == null) {
                lastException = new ProviderNotFoundException(
                    this.messages.getMessage("ProviderManager.providerNotFound", new Object[] { toTest.getName() }, "No AuthenticationProvider found for {0}"));
            }
            throw lastException;
        }
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    private void copyDetails(Authentication source, Authentication dest) {
        if (dest instanceof AbstractAuthenticationToken token && dest.getDetails() == null) {
            token.setDetails(source.getDetails());
        }

    }
}