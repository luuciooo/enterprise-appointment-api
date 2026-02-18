package com.lucio.appointments.domain.port.out;

public interface PasswordHasher {
    String hash(String rawPassword);
}