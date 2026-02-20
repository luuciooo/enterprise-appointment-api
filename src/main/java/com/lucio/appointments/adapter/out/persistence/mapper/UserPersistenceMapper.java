package com.lucio.appointments.adapter.out.persistence.mapper;

import com.lucio.appointments.adapter.out.persistence.entity.UserEntity;
import com.lucio.appointments.domain.model.User;

import java.time.Instant;

public class UserPersistenceMapper {

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .fullName(user.getFullName())
                .createdAt(Instant.now())
                .build();
    }

    public static User toDomain(UserEntity entity) {

        var roles = entity.getRoles() == null
                ? java.util.Set.<com.lucio.appointments.domain.model.Role>of()
                : entity.getRoles().stream()
                .map(roleEntity -> com.lucio.appointments.domain.model.Role.builder()
                        .name(roleEntity.getName())
                        .permissions(
                                roleEntity.getPermissions() == null
                                        ? java.util.Set.of()
                                        : roleEntity.getPermissions().stream()
                                        .map(permissionEntity -> com.lucio.appointments.domain.model.Permission
                                                .builder()
                                                .name(permissionEntity.getName())
                                                .build())
                                        .collect(java.util.stream.Collectors.toSet()))
                        .build())
                .collect(java.util.stream.Collectors.toSet());

        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .fullName(entity.getFullName())
                .roles(roles)
                .build();
    }

}
