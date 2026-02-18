package com.lucio.appointments.adapter.in.web;

import com.lucio.appointments.adapter.in.web.dto.RegisterUserRequest;
import com.lucio.appointments.adapter.in.web.dto.RegisterUserResponse;
import com.lucio.appointments.domain.port.in.RegisterUserCommand;
import com.lucio.appointments.domain.port.in.RegisterUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(
            @Valid @RequestBody RegisterUserRequest request
    ) {

        var user = registerUserUseCase.register(
                RegisterUserCommand.builder()
                        .email(request.email())
                        .password(request.password())
                        .fullName(request.fullName())
                        .build()
        );

        return ResponseEntity.ok(
                new RegisterUserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getFullName()
                )
        );
    }
}
