package com.lucio.appointments.application.service;

import com.lucio.appointments.domain.exception.InvalidCredentialsException;
import com.lucio.appointments.domain.port.in.LoginCommand;
import com.lucio.appointments.domain.port.in.LoginResult;
import com.lucio.appointments.domain.port.in.LoginUseCase;
import com.lucio.appointments.domain.port.out.PasswordHasher;
import com.lucio.appointments.domain.port.out.TokenProvider;
import com.lucio.appointments.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordHasher passwordHasher;
    private final TokenProvider tokenProvider;

    @Override
    public LoginResult login(LoginCommand command) {

        var user = userRepositoryPort.findByEmail(command.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordHasher.matches(command.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        var accessToken = tokenProvider.generateAccessToken(user.getId(), user.getEmail());
        var refreshToken = tokenProvider.generateRefreshToken(user.getId());

        return new LoginResult(accessToken, refreshToken);
    }
}
