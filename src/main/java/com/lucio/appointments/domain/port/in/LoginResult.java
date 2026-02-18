package com.lucio.appointments.domain.port.in;

public record LoginResult(
        String accessToken,
        String refreshToken
) {}