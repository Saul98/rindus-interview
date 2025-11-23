package com.rindus.interview.application.usecase;

import com.rindus.interview.application.dto.command.CreateUserCommand;
import com.rindus.interview.application.dto.result.CreateUserResult;
import com.rindus.interview.application.exception.UserAlreadyExistsException;
import com.rindus.interview.domain.aggregate.User;
import com.rindus.interview.domain.port.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

/** Use case for creating a new User. */
@ApplicationScoped
public class CreateUserUseCase {

  private static final Logger LOG = Logger.getLogger(CreateUserUseCase.class);
  private final UserRepository userRepository;

  @Inject
  public CreateUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public CreateUserResult execute(CreateUserCommand cmd) {
    LOG.debugf("Executing CreateUserUseCase for name=%s, email=%s", cmd.name(), cmd.email());
    User user = User.create(cmd.name(), cmd.email());

    if (userRepository.existsByEmail(user.getEmail())) {
      LOG.warnf("Cannot create user, email already exists: %s", user.getEmail().getValue());
      throw new UserAlreadyExistsException(user.getEmail());
    }

    User saved = userRepository.save(user);

    LOG.infof("User created successfully with id=%s", saved.getId().getValue());
    return new CreateUserResult(
        saved.getId().getValue(), saved.getName(), saved.getEmail().getValue());
  }
}
