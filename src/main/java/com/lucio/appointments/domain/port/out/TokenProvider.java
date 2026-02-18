package com.lucio.appointments.domain.port.out;

import java.util.UUID;

public interface TokenProvider {

    String generateAccessToken(UUID userId, String email);

    String generateRefreshToken(UUID userId);

    boolean validateToken(String token);

    UUID extractUserId(String token);
}
