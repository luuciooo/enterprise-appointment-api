package com.lucio.appointments.adapter.out.persistence;

import com.lucio.appointments.adapter.out.persistence.entity.RoleEntity;
import com.lucio.appointments.adapter.out.persistence.entity.UserEntity;
import com.lucio.appointments.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.lucio.appointments.adapter.out.persistence.repository.RoleJpaRepository;
import com.lucio.appointments.adapter.out.persistence.repository.UserJpaRepository;
import com.lucio.appointments.domain.model.User;
import com.lucio.appointments.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userRepository;
    private final RoleJpaRepository roleRepository;

    @Override
    public User save(User user) {
        UserEntity entity = UserPersistenceMapper.toEntity(user);

        Set<RoleEntity> roles = new HashSet<>();
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            roles = user.getRoles().stream()
                    .map(role -> roleRepository.findByName(role.getName())
                            .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName())))
                    .collect(Collectors.toSet());
        }
        entity.setRoles(roles);

        UserEntity saved = userRepository.save(entity);
        return UserPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id).map(UserPersistenceMapper::toDomain);
    }
}
