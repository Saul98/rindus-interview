package com.rindus.interview.infrastructure.repository.persistence.panache;

import com.rindus.interview.domain.aggregate.User;
import com.rindus.interview.domain.port.UserRepository;
import com.rindus.interview.domain.valueobject.Email;
import com.rindus.interview.domain.valueobject.UserId;
import com.rindus.interview.infrastructure.repository.persistence.entity.UserEntity;
import com.rindus.interview.infrastructure.repository.persistence.mapper.UserMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;
import org.mapstruct.factory.Mappers;

/** Panache implementation of UserRepository. */
@ApplicationScoped
public class PanacheUserRepository
    implements UserRepository, PanacheRepositoryBase<UserEntity, UUID> {

  private static final UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

  @Override
  public User save(User user) {
    UserEntity entity = MAPPER.toEntity(user);
    persist(entity);
    return user;
  }

  @Override
  public Optional<User> findById(UserId id) {
    return findByIdOptional(id.getValue()).map(MAPPER::toDomain);
  }

  @Override
  public boolean existsByEmail(Email email) {
    return find("email", email.getValue()).firstResultOptional().isPresent();
  }
}
