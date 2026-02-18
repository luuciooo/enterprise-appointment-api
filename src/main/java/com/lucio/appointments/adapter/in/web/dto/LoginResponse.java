package com.lucio.appointments.adapter.in.web.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {}
