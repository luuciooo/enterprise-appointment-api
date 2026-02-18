package com.lucio.appointments.adapter.out.security;

import com.lucio.appointments.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UUID id = UUID.fromString(userId);

        var user = userRepositoryPort.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Por ahora sin roles (los sumamos después)
        return new org.springframework.security.core.userdetails.User(
                user.getId().toString(),
                user.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
