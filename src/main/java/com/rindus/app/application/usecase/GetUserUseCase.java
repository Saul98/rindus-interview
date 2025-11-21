package com.rindus.app.application.usecase;

import com.rindus.app.application.dto.result.GetUserResult;
import com.rindus.app.domain.aggregate.User;
import com.rindus.app.domain.ports.UserRepository;
import com.rindus.app.domain.valueobject.UserId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.UUID;

/**
 * Use case for retrieving a User by its id.
 */
@ApplicationScoped
public class GetUserUseCase {

  private final UserRepository userRepository;

  @Inject
  public GetUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<GetUserResult> execute(UUID id) {
    UserId userId = UserId.from(id);

    return userRepository
        .findById(userId)
        .map(this::toResult);
  }

  private GetUserResult toResult(User user) {
    return new GetUserResult(
        user.getId().getValue(),
        user.getName(),
        user.getEmail().getValue()
    );
  }
}
