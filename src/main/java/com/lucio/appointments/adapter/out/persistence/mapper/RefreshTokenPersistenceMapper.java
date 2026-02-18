package com.lucio.appointments.adapter.out.persistence.mapper;

import com.lucio.appointments.adapter.out.persistence.entity.RefreshTokenEntity;
import com.lucio.appointments.domain.model.RefreshToken;

import java.time.Instant;

public class RefreshTokenPersistenceMapper {

    public static RefreshTokenEntity toEntity(RefreshToken rt) {
        return RefreshTokenEntity.builder()
                .id(rt.getId())
                .userId(rt.getUserId())
                .token(rt.getToken())
                .expiresAt(rt.getExpiresAt())
                .revoked(rt.isRevoked())
                .replacedByToken(rt.getReplacedByToken())
                .createdAt(Instant.now())
                .build();
    }

    public static RefreshToken toDomain(RefreshTokenEntity e) {
        return RefreshToken.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .token(e.getToken())
                .expiresAt(e.getExpiresAt())
                .revoked(e.isRevoked())
                .replacedByToken(e.getReplacedByToken())
                .build();
    }
}
