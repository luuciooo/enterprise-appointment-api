package com.lucio.appointments.adapter.out.persistence;

import com.lucio.appointments.adapter.out.persistence.mapper.RefreshTokenPersistenceMapper;
import com.lucio.appointments.adapter.out.persistence.repository.RefreshTokenJpaRepository;
import com.lucio.appointments.domain.model.RefreshToken;
import com.lucio.appointments.domain.port.out.RefreshTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepositoryPort {

    private final RefreshTokenJpaRepository repo;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        var saved = repo.save(RefreshTokenPersistenceMapper.toEntity(refreshToken));
        return RefreshTokenPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return repo.findByToken(token).map(RefreshTokenPersistenceMapper::toDomain);
    }

    @Override
    public void revoke(String token) {
        repo.findByToken(token).ifPresent(e -> {
            e.setRevoked(true);
            repo.save(e);
        });
    }

    @Override
    public void revokeAllByUserId(UUID userId) {
        repo.deleteByUserId(userId);
    }
}
