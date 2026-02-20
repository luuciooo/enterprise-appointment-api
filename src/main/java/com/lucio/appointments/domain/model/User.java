package com.lucio.appointments.domain.model;

import lombok.*;

import java.util.UUID;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class User {
    private final UUID id;
    private final String email;
    private final String passwordHash;
    private final String fullName;
    private final Set<Role> roles;
}
