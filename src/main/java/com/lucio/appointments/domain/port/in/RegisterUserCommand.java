package com.lucio.appointments.domain.port.in;

import lombok.Builder;

@Builder
public record RegisterUserCommand(
        String email,
        String password,
        String fullName
) {}
