package com.rindus.interview.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rindus.interview.application.dto.command.CreateUserCommand;
import com.rindus.interview.application.dto.result.CreateUserResult;
import com.rindus.interview.domain.aggregate.User;
import com.rindus.interview.domain.valueobject.UserId;
import com.rindus.interview.infrastructure.repository.persistance.panache.PanacheUserRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
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

  @BeforeEach
  void cleanDatabase() {
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("When creating a new user then it persists and returns result")
  void whenCreatingNewUser_thenPersistsAndReturnsResult() {
    CreateUserCommand cmd = new CreateUserCommand("Peter Jane", "peter.jane@example.com");

    CreateUserResult result = useCase.execute(cmd);

    assertNotNull(result.id(), "Result id should not be null");
    assertEquals("Peter Jane", result.name());
    assertEquals("peter.jane@example.com", result.email());

    UserId id = UserId.from(result.id());
    assertTrue(userRepository.findById(id).isPresent(), "User should be found in database");
    User saved = userRepository.findById(id).orElseThrow();
    assertEquals(result.name(), saved.getName());
    assertEquals(result.email(), saved.getEmail().getValue());
  }
}
