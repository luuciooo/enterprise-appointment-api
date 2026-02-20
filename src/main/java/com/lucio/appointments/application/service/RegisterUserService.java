package com.lucio.appointments.application.service;

import com.lucio.appointments.domain.exception.EmailAlreadyExistsException;
import com.lucio.appointments.domain.model.Role;
import com.lucio.appointments.domain.model.User;
import com.lucio.appointments.domain.port.in.RegisterUserCommand;
import com.lucio.appointments.domain.port.in.RegisterUserUseCase;
import com.lucio.appointments.domain.port.out.PasswordHasher;
import com.lucio.appointments.domain.port.out.RoleRepositoryPort;
import com.lucio.appointments.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordHasher passwordHasher;
    private final RoleRepositoryPort roleRepositoryPort;

    @Override
    public User register(RegisterUserCommand command) {
        userRepositoryPort.findByEmail(command.email())
                .ifPresent(u -> {
                    throw new EmailAlreadyExistsException(command.email());
                });

        Role userRole = roleRepositoryPort.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        User user = User.builder()
                .id(UUID.randomUUID())
                .email(command.email())
                .passwordHash(passwordHasher.hash(command.password()))
                .fullName(command.fullName())
                .roles(Set.of(userRole))
                .build();

        return userRepositoryPort.save(user);
    }
}
