package com.rindus.app.application.usecase;

import com.rindus.app.application.dto.command.CreateUserCommand;
import com.rindus.app.application.dto.result.CreateUserResult;
import com.rindus.app.application.exception.UserAlreadyExistsException;
import com.rindus.app.domain.aggregate.User;
import com.rindus.app.domain.ports.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.Clock;

/**
 * Use case for creating a new User.
 */
@ApplicationScoped
public class CreateUserUseCase {

  private final Clock clock;

  private final UserRepository userRepository;

  @Inject
  public CreateUserUseCase(Clock clock, UserRepository userRepository) {
    this.clock = clock;
    this.userRepository = userRepository;
  }

  @Transactional
  public CreateUserResult execute(CreateUserCommand cmd) {
    User user = User.create(cmd.name(), cmd.email(), clock.instant());

    if (userRepository.existsByEmail(user.getEmail())) {
      throw new UserAlreadyExistsException(user.getEmail());
    }

    User saved = userRepository.save(user);

    return new CreateUserResult(saved.getId().getValue(), saved.getName(), saved.getEmail().getValue());
  }
}
