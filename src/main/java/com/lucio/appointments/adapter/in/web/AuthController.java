package com.lucio.appointments.adapter.in.web;

import com.lucio.appointments.adapter.in.web.dto.LoginRequest;
import com.lucio.appointments.adapter.in.web.dto.LoginResponse;
import com.lucio.appointments.adapter.in.web.dto.RefreshRequest;
import com.lucio.appointments.adapter.in.web.dto.RegisterUserRequest;
import com.lucio.appointments.adapter.in.web.dto.RegisterUserResponse;
import com.lucio.appointments.domain.port.in.LoginCommand;
import com.lucio.appointments.domain.port.in.LoginUseCase;
import com.lucio.appointments.domain.port.in.RefreshAccessTokenUseCase;
import com.lucio.appointments.domain.port.in.RefreshCommand;
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

        private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;
        private final RegisterUserUseCase registerUserUseCase;
        private final LoginUseCase loginUseCase;

        @PostMapping("/register")
        public ResponseEntity<RegisterUserResponse> register(
                        @Valid @RequestBody RegisterUserRequest request) {

                var user = registerUserUseCase.register(
                                RegisterUserCommand.builder()
                                                .email(request.email())
                                                .password(request.password())
                                                .fullName(request.fullName())
                                                .build());

                return ResponseEntity.ok(
                                new RegisterUserResponse(
                                                user.getId(),
                                                user.getEmail(),
                                                user.getFullName()));
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(
                        @Valid @RequestBody LoginRequest request) {

                var result = loginUseCase.login(
                                new LoginCommand(request.email(), request.password()));

                return ResponseEntity.ok(
                                new LoginResponse(result.accessToken(), result.refreshToken()));
        }

        @PostMapping("/refresh")
        public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody RefreshRequest request) {
                var result = refreshAccessTokenUseCase.refresh(new RefreshCommand(request.refreshToken()));
                return ResponseEntity.ok(new LoginResponse(result.accessToken(), result.refreshToken()));
        }
}
