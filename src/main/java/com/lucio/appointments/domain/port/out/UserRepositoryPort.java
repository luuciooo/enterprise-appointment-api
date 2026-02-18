package com.lucio.appointments.domain.port.out;

import com.lucio.appointments.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    User save(User user);
    Optional<User> findById(UUID id);
}
