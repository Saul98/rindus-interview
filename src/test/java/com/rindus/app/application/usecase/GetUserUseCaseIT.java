package com.rindus.app.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rindus.app.application.dto.result.GetUserResult;
import com.rindus.app.domain.aggregate.User;
import com.rindus.app.infrastructure.repository.persistance.panache.PanacheUserRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Integration test for: {@link GetUserUseCase}.
 */
@QuarkusTest
class GetUserUseCaseIT {

  @Inject
  GetUserUseCase useCase;

  @Inject
  PanacheUserRepository userRepository;

  @Test
  @DisplayName("Given docker Postgres is running, when getting an existing user then returns it; DB is clean per run")
  void getUser_againstDockerPostgres_happyPath() {
    User u = User.create("Docker John", "docker.john@example.com");
    userRepository.save(u);

    UUID id = u.getId().getValue();

    Optional<GetUserResult> maybe = useCase.execute(id);
    
    assertTrue(maybe.isPresent(), "User should be found by id");
    GetUserResult result = maybe.orElseThrow();
    assertEquals(id, result.id());
    assertEquals("Docker John", result.name());
    assertEquals("docker.john@example.com", result.email());
  }
}
