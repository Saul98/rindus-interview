package com.rindus.app.domain.ports;

import com.rindus.app.domain.aggregate.User;
import com.rindus.app.domain.valueobject.Email;
import com.rindus.app.domain.valueobject.UserId;
import java.util.Optional;

/**
 * Domain port for persisting and retrieving Users.
 */
public interface UserRepository {

  User save(User user);

  Optional<User> findById(UserId id);

  boolean existsByEmail(Email email);
}
