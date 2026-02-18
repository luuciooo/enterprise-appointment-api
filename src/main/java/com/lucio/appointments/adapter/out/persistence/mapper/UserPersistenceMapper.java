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
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .fullName(entity.getFullName())
                .build();
    }
}
