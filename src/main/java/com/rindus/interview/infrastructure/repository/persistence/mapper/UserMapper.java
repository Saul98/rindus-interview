package com.rindus.interview.infrastructure.repository.persistence.mapper;

import com.rindus.interview.domain.aggregate.User;
import com.rindus.interview.domain.valueobject.Email;
import com.rindus.interview.domain.valueobject.UserId;
import com.rindus.interview.infrastructure.repository.persistence.entity.UserEntity;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Mapper for converting between {@link User} and {@link UserEntity}. */
@Mapper
public interface UserMapper {

  /** Maps a domain User aggregate to its persistence entity. */
  @Mapping(target = "version", ignore = true)
  UserEntity toEntity(User user);

  /** Maps a persistence entity to its domain User aggregate. */
  default User toDomain(UserEntity entity) {
    if (entity == null) {
      return null;
    }

    UserId id = map(entity.getId());
    Email email = map(entity.getEmail());

    return User.fromPersistence(
        id,
        entity.getName(),
        email,
        entity.getStatus(),
        entity.isEmailVerified(),
        entity.getEmailVerifiedAt(),
        entity.getCreatedAt(),
        entity.getUpdatedAt());
  }

  default UUID map(UserId id) {
    return id == null ? null : id.getValue();
  }

  default UserId map(UUID id) {
    return id == null ? UserId.newId() : UserId.from(id);
  }

  default String map(Email email) {
    return email == null ? null : email.getValue();
  }

  default Email map(String email) {
    return email == null ? null : Email.of(email);
  }
}
