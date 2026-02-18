package com.lucio.appointments.domain.port.in;

import com.lucio.appointments.domain.model.User;

public interface RegisterUserUseCase {
    User register(RegisterUserCommand command);
}
