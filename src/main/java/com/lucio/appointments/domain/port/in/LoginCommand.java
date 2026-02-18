package com.lucio.appointments.domain.port.in;

public record LoginCommand(
        String email,
        String password
) {}
