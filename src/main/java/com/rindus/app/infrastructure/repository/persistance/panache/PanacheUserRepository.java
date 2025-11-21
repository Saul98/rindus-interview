package com.rindus.app.infrastructure.repository.persistance.panache;

import com.rindus.app.domain.aggregate.User;
import com.rindus.app.domain.ports.UserRepository;
import com.rindus.app.domain.valueobject.Email;
import com.rindus.app.domain.valueobject.UserId;
import com.rindus.app.infrastructure.repository.persistance.entity.UserEntity;
import com.rindus.app.infrastructure.repository.persistance.mapper.UserMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

/**
 * Panache implementation of UserRepository.
 */
@ApplicationScoped
public class PanacheUserRepository implements UserRepository, PanacheRepositoryBase<UserEntity, UUID> {

  @Override
  @Transactional
  public User save(User user) {
    UserEntity entity = UserMapper.toEntity(user);
    persist(entity);
    return user;
  }

  @Override
  public Optional<User> findById(UserId id) {
    return findByIdOptional(id.getValue()).map(UserMapper::toDomain);
  }

  @Override
  public boolean existsByEmail(Email email) {
    return find("email", email.getValue()).firstResultOptional().isPresent();
  }
}
