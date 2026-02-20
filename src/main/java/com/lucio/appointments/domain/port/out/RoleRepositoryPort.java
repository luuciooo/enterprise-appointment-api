package com.lucio.appointments.domain.port.out;

import com.lucio.appointments.domain.model.Role;

import java.util.Optional;

public interface RoleRepositoryPort {
    Optional<Role> findByName(String name);
}
