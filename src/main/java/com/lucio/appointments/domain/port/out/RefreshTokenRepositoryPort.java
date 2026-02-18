package com.lucio.appointments.domain.port.out;

import com.lucio.appointments.domain.model.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepositoryPort {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
    void revoke(String token);
    void revokeAllByUserId(UUID userId);
}
