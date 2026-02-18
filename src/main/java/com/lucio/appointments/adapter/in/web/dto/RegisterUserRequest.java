package com.lucio.appointments.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 6)
        String password,

        @NotBlank
        String fullName
) {}
