package com.lucio.appointments.adapter.out.persistence;

import com.lucio.appointments.adapter.out.persistence.repository.RoleJpaRepository;
import com.lucio.appointments.domain.model.Permission;
import com.lucio.appointments.domain.model.Role;
import com.lucio.appointments.domain.port.out.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository repository;

    @Override
    public java.util.Optional<Role> findByName(String name) {

        return repository.findByName(name)
                .map(entity ->
                        Role.builder()
                                .name(entity.getName())
                                .permissions(
                                        entity.getPermissions().stream()
                                                .map(permissionEntity ->
                                                        Permission.builder()
                                                                .name(permissionEntity.getName())
                                                                .build()
                                                )
                                                .collect(Collectors.toSet())
                                )
                                .build()
                );
    }
}
