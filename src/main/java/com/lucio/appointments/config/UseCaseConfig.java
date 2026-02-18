package com.lucio.appointments.config;

import com.lucio.appointments.application.service.LoginService;
import com.lucio.appointments.application.service.RegisterUserService;
import com.lucio.appointments.domain.port.in.LoginUseCase;
import com.lucio.appointments.domain.port.in.RegisterUserUseCase;
import com.lucio.appointments.domain.port.out.PasswordHasher;
import com.lucio.appointments.domain.port.out.TokenProvider;
import com.lucio.appointments.domain.port.out.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordHasher passwordHasher) {
        return new RegisterUserService(userRepositoryPort, passwordHasher);
    }

    @Bean
    public LoginUseCase loginUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordHasher passwordHasher,
            TokenProvider tokenProvider) {
        return new LoginService(userRepositoryPort, passwordHasher, tokenProvider);
    }
}
