package com.lucio.appointments.application.service;

import com.lucio.appointments.domain.exception.InvalidRefreshTokenException;
import com.lucio.appointments.domain.model.RefreshToken;
import com.lucio.appointments.domain.port.in.*;
import com.lucio.appointments.domain.port.out.RefreshTokenRepositoryPort;
import com.lucio.appointments.domain.port.out.TokenProvider;
import com.lucio.appointments.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class RefreshAccessTokenService implements RefreshAccessTokenUseCase {

    private final RefreshTokenRepositoryPort refreshTokenRepo;
    private final UserRepositoryPort userRepositoryPort;
    private final TokenProvider tokenProvider;
    private final long refreshValidityMillis;

    @Override
    public LoginResult refresh(RefreshCommand command) {

        RefreshToken stored = refreshTokenRepo.findByToken(command.refreshToken())
                .orElseThrow(InvalidRefreshTokenException::new);

        if (stored.isRevoked() || stored.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException();
        }

        UUID userId = tokenProvider.extractUserId(stored.getToken());
        var user = userRepositoryPort.findById(userId)
                .orElseThrow(InvalidRefreshTokenException::new);

        // ROTACIÓN: revocamos el refresh actual y emitimos uno nuevo
        String newAccess = tokenProvider.generateAccessToken(user.getId(), user.getEmail());
        String newRefresh = tokenProvider.generateRefreshToken(user.getId());

        refreshTokenRepo.revoke(stored.getToken());
        refreshTokenRepo.save(
                RefreshToken.builder()
                        .id(UUID.randomUUID())
                        .userId(user.getId())
                        .token(newRefresh)
                        .expiresAt(Instant.now().plusMillis(refreshValidityMillis))
                        .revoked(false)
                        .replacedByToken(null)
                        .build()
        );

        return new LoginResult(newAccess, newRefresh);
    }
}
