package com.lucio.appointments.domain.port.in;

public interface LoginUseCase {
    LoginResult login(LoginCommand command);
}
