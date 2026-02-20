package com.lucio.appointments.domain.model;

import lombok.*;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class Role {

    private final String name;
    private final Set<Permission> permissions;
}
