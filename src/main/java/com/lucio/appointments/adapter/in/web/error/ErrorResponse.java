package com.lucio.appointments.adapter.in.web.error;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String code,
        String message,
        String path,
        String traceId,
        String tenantId
) {}
