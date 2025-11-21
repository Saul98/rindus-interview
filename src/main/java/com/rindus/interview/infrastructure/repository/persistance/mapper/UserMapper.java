package com.rindus.interview.infrastructure.repository.persistance.mapper;

import com.rindus.interview.domain.aggregate.User;
import com.rindus.interview.domain.valueobject.Email;
import com.rindus.interview.domain.valueobject.UserId;
import com.rindus.interview.infrastructure.repository.persistance.entity.UserEntity;

/**
 * Mapper for converting between {@link User} and {@link UserEntity}.
 */
public final class UserMapper {

  private UserMapper() {
    // utility class
  }

  public static UserEntity toEntity(User user) {
    if (user == null) {
      return null;
    }

    UserEntity entity = new UserEntity();
    entity.setId(user.getId().getValue());
    entity.setName(user.getName());
    entity.setEmail(user.getEmail().getValue());
    entity.setStatus(user.getStatus());
    entity.setEmailVerified(user.isEmailVerified());
    entity.setEmailVerifiedAt(user.getEmailVerifiedAt());
    entity.setCreatedAt(user.getCreatedAt());
    entity.setUpdatedAt(user.getUpdatedAt());

    return entity;
  }

  public static User toDomain(UserEntity entity) {
    if (entity == null) {
      return null;
    }

    UserId id = entity.getId() != null ? UserId.from(entity.getId()) : UserId.newId();
    Email email = entity.getEmail() != null ? Email.of(entity.getEmail()) : null;

    return User.fromPersistence(
        id,
        entity.getName(),
        email,
        entity.getStatus(),
        entity.isEmailVerified(),
        entity.getEmailVerifiedAt(),
        entity.getCreatedAt(),
        entity.getUpdatedAt()
    );
  }
}
