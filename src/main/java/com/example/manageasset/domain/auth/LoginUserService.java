package com.example.manageasset.domain.auth;

import com.example.manageasset.domain.shared.exceptions.InvalidRequestException;
import com.example.manageasset.infrastructure.shared.security.JwtTokenProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public LoginUserService(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    public AuthOutput login(AuthInput input) {
        if (Strings.isNullOrEmpty(input.username)) {
            throw new InvalidRequestException("Require [username]");
        }
        if (Strings.isNullOrEmpty(input.password)) {
            throw new InvalidRequestException("Require [password]");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                input.username, input.password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        return AuthOutput.create(token, input.username);
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AuthInput {
        private String username;
        private String password;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AuthOutput {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("token_type")
        private String tokenType = "Bearer";
        private String username;

        public AuthOutput(String accessToken, String username) {
            this.accessToken = accessToken;
            this.username = username;
        }

        public static AuthOutput create(String accessToken, String username) {
            return new AuthOutput(accessToken, username);
        }
    }
}
