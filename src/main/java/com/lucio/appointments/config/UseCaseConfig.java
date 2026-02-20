package com.lucio.appointments.config;

import com.lucio.appointments.application.service.LoginService;
import com.lucio.appointments.application.service.RefreshAccessTokenService;
import com.lucio.appointments.application.service.RegisterUserService;
import com.lucio.appointments.domain.port.in.LoginUseCase;
import com.lucio.appointments.domain.port.in.RefreshAccessTokenUseCase;
import com.lucio.appointments.domain.port.in.RegisterUserUseCase;
import com.lucio.appointments.domain.port.out.PasswordHasher;
import com.lucio.appointments.domain.port.out.RefreshTokenRepositoryPort;
import com.lucio.appointments.domain.port.out.RoleRepositoryPort;
import com.lucio.appointments.domain.port.out.TokenProvider;
import com.lucio.appointments.domain.port.out.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class UseCaseConfig {

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordHasher passwordHasher,
            RoleRepositoryPort roleRepositoryPort) {
        return new RegisterUserService(userRepositoryPort, passwordHasher, roleRepositoryPort);
    }

    @Bean
    public LoginUseCase loginUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordHasher passwordHasher,
            TokenProvider tokenProvider,
            RefreshTokenRepositoryPort refreshTokenRepositoryPort,
            @Value("${security.jwt.refresh-validity}") long refreshValidityMillis) {
        return new LoginService(
                userRepositoryPort,
                passwordHasher,
                tokenProvider,
                refreshTokenRepositoryPort,
                refreshValidityMillis);
    }

    @Bean
    public RefreshAccessTokenUseCase refreshAccessTokenUseCase(
            RefreshTokenRepositoryPort refreshTokenRepositoryPort,
            UserRepositoryPort userRepositoryPort,
            TokenProvider tokenProvider,
            @Value("${security.jwt.refresh-validity}") long refreshValidityMillis) {
        return new RefreshAccessTokenService(
            refreshTokenRepositoryPort, 
            userRepositoryPort, 
            tokenProvider,
            refreshValidityMillis);
    }

}
