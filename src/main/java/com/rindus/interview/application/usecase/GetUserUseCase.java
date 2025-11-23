package com.rindus.interview.application.usecase;

import com.rindus.interview.application.dto.result.GetUserResult;
import com.rindus.interview.domain.aggregate.User;
import com.rindus.interview.domain.port.UserRepository;
import com.rindus.interview.domain.valueobject.UserId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.UUID;
import org.jboss.logging.Logger;

/** Use case for retrieving a User by its id. */
@ApplicationScoped
public class GetUserUseCase {

  private static final Logger LOG = Logger.getLogger(GetUserUseCase.class);
  private final UserRepository userRepository;

  @Inject
  public GetUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<GetUserResult> execute(UUID id) {
    LOG.debugf("Executing GetUserUseCase for id=%s", id);
    UserId userId = UserId.from(id);

    Optional<User> userOpt = userRepository.findById(userId);
    if (userOpt.isPresent()) {
      LOG.debugf("User found for id=%s", id);
    } else {
      LOG.debugf("User not found for id=%s", id);
    }

    return userOpt.map(this::toResult);
  }

  private GetUserResult toResult(User user) {
    return new GetUserResult(user.getId().getValue(), user.getName(), user.getEmail().getValue());
  }
}
