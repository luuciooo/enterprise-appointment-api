package com.lucio.appointments.application.service;

import com.lucio.appointments.domain.exception.InvalidCredentialsException;
import com.lucio.appointments.domain.model.RefreshToken;
import com.lucio.appointments.domain.port.in.LoginCommand;
import com.lucio.appointments.domain.port.in.LoginResult;
import com.lucio.appointments.domain.port.in.LoginUseCase;
import com.lucio.appointments.domain.port.out.PasswordHasher;
import com.lucio.appointments.domain.port.out.RefreshTokenRepositoryPort;
import com.lucio.appointments.domain.port.out.TokenProvider;
import com.lucio.appointments.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordHasher passwordHasher;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepositoryPort refreshTokenRepo;
    private final long refreshValidityMillis; // 🔥 faltaba esto

    @Override
    public LoginResult login(LoginCommand command) {

        var user = userRepositoryPort.findByEmail(command.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordHasher.matches(command.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        var accessToken = tokenProvider.generateAccessToken(user.getId(), user.getEmail());
        var refreshToken = tokenProvider.generateRefreshToken(user.getId());

        refreshTokenRepo.save(
                RefreshToken.builder()
                        .id(UUID.randomUUID())
                        .userId(user.getId())
                        .token(refreshToken)
                        .expiresAt(Instant.now().plusMillis(refreshValidityMillis))
                        .revoked(false)
                        .replacedByToken(null)
                        .build()
        );

        return new LoginResult(accessToken, refreshToken);
    }
}