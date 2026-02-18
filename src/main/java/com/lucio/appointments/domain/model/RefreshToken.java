package com.lucio.appointments.domain.model;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class RefreshToken {
    private final UUID id;
    private final UUID userId;
    private final String token;
    private final Instant expiresAt;
    private final boolean revoked;
    private final String replacedByToken;
}
