package com.lucio.appointments.domain.port.in;

public interface RefreshAccessTokenUseCase {
    LoginResult refresh(RefreshCommand command);
}
