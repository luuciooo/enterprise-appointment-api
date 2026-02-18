package com.lucio.appointments.adapter.in.web.dto;

import java.util.UUID;

public record RegisterUserResponse(
        UUID id,
        String email,
        String fullName
) {}
