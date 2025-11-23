package com.rindus.interview.domain.ports;

import com.rindus.interview.domain.aggregate.User;
import com.rindus.interview.domain.valueobject.Email;
import com.rindus.interview.domain.valueobject.UserId;

import java.util.Optional;

/**
 * Domain port for persisting and retrieving Users.
 */
public interface UserRepository {

  User save(User user);

  Optional<User> findById(UserId id);

  boolean existsByEmail(Email email);
}
