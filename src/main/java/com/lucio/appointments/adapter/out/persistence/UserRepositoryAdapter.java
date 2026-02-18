package com.lucio.appointments.adapter.out.persistence;

import com.lucio.appointments.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.lucio.appointments.adapter.out.persistence.repository.UserJpaRepository;
import com.lucio.appointments.domain.model.User;
import com.lucio.appointments.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserPersistenceMapper::toDomain);
    }

    @Override
    public User save(User user) {
        var saved = userJpaRepository.save(UserPersistenceMapper.toEntity(user));
        return UserPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(UserPersistenceMapper::toDomain);
    }
}
