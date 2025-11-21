package com.rindus.app.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rindus.app.application.dto.command.CreateUserCommand;
import com.rindus.app.application.dto.result.CreateUserResult;
import com.rindus.app.domain.aggregate.User;
import com.rindus.app.domain.valueobject.UserId;
import com.rindus.app.infrastructure.repository.persistance.panache.PanacheUserRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Component test for: {@link CreateUserUseCase}.
 */
@QuarkusTest
class CreateUserUseCaseIT {

  @Inject
  CreateUserUseCase useCase;

  @Inject
  PanacheUserRepository userRepository;

  @Test
  @DisplayName("Given docker Postgres is running, when creating a new user then it persists and returns result; DB is clean per run")
  void createUser_againstDockerPostgres_happyPath() {
    CreateUserCommand cmd = new CreateUserCommand("Docker Jane", "docker.jane@example.com");

    CreateUserResult result = useCase.execute(cmd);

    assertNotNull(result.id(), "Result id should not be null");
    assertEquals("Docker Jane", result.name());
    assertEquals("docker.jane@example.com", result.email());

    UserId id = UserId.from(result.id());
    assertTrue(userRepository.findById(id).isPresent(), "User should be found in database");
    User saved = userRepository.findById(id).orElseThrow();
    assertEquals(result.name(), saved.getName());
    assertEquals(result.email(), saved.getEmail().getValue());
  }
}
