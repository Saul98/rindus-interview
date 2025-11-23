package com.rindus.interview.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rindus.interview.application.dto.result.GetUserResult;
import com.rindus.interview.domain.aggregate.User;
import com.rindus.interview.infrastructure.repository.persistance.panache.PanacheUserRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Integration test for: {@link GetUserUseCase}. */
@QuarkusTest
@Transactional
class GetUserUseCaseIT {

  @Inject GetUserUseCase useCase;

  @Inject PanacheUserRepository userRepository;

  @BeforeEach
  void cleanDatabase() {
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("When getting an existing user then returns it")
  void whenGettingExistingUser_thenReturnsIt() {
    User u = User.create("Pedro Perez", "pedro.perez1192@example.com");
    userRepository.save(u);

    UUID id = u.getId().getValue();

    Optional<GetUserResult> maybe = useCase.execute(id);

    assertTrue(maybe.isPresent(), "User should be found by id");
    GetUserResult result = maybe.orElseThrow();
    assertEquals(id, result.id());
    assertEquals("Pedro Perez", result.name());
    assertEquals("pedro.perez1192@example.com", result.email());
  }
}
