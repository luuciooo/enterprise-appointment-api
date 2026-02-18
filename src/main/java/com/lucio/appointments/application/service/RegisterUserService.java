package com.lucio.appointments.application.service;

import com.lucio.appointments.domain.exception.EmailAlreadyExistsException;
import com.lucio.appointments.domain.model.User;
import com.lucio.appointments.domain.port.in.RegisterUserCommand;
import com.lucio.appointments.domain.port.in.RegisterUserUseCase;
import com.lucio.appointments.domain.port.out.UserRepositoryPort;
import com.lucio.appointments.domain.port.out.PasswordHasher;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordHasher passwordHasher;

    @Override
    public User register(RegisterUserCommand command) {
        userRepositoryPort.findByEmail(command.email())
                .ifPresent(u -> { throw new EmailAlreadyExistsException(command.email()); });

        User user = User.builder()
                .id(UUID.randomUUID())
                .email(command.email())
                .passwordHash(passwordHasher.hash(command.password()))
                .fullName(command.fullName())
                .build();

        return userRepositoryPort.save(user);
    }
}