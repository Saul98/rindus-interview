package com.rindus.interview.domain.aggregate;

import static org.junit.jupiter.api.Assertions.*;

import com.rindus.interview.domain.enums.UserStatus;
import com.rindus.interview.domain.valueobject.Email;
import com.rindus.interview.domain.valueobject.UserId;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User aggregate fromPersistence behavior")
class UserTest {

  @Test
  @DisplayName("When fromPersistence given valid parameters then creates a user")
  void whenFromPersistenceGivenValidParameters_thenCreatesUser() {
    UserId userId = UserId.from(UUID.randomUUID());
    String name = "John Doe";
    Email email = Email.of("john.doe@example.com");
    UserStatus status = UserStatus.ACTIVE;
    boolean emailVerified = true;
    Instant emailVerifiedAt = Instant.now();
    Instant createdAt = Instant.parse("2022-01-01T10:00:00Z");
    Instant updatedAt = Instant.parse("2022-01-01T11:00:00Z");
    User user =
        User.fromPersistence(
            userId, name, email, status, emailVerified, emailVerifiedAt, createdAt, updatedAt);
    assertEquals(userId, user.getId());
    assertEquals(name, user.getName());
    assertEquals(email, user.getEmail());
    assertEquals(status, user.getStatus());
    assertTrue(user.isEmailVerified());
    assertEquals(emailVerifiedAt, user.getEmailVerifiedAt());
    assertEquals(createdAt, user.getCreatedAt());
    assertEquals(updatedAt, user.getUpdatedAt());
  }

  @Test
  @DisplayName("When fromPersistence given null userId then throws exception")
  void whenFromPersistenceGivenNullUserId_thenThrowsException() {
    String name = "John Doe";
    Email email = Email.of("john.doe@example.com");
    UserStatus status = UserStatus.PENDING_VERIFICATION;
    boolean emailVerified = false;
    Instant emailVerifiedAt = null;
    Instant createdAt = Instant.now();
    Instant updatedAt = Instant.now();
    assertThrows(
        NullPointerException.class,
        () ->
            User.fromPersistence(
                null, name, email, status, emailVerified, emailVerifiedAt, createdAt, updatedAt));
  }

  @Test
  @DisplayName("When fromPersistence given null email then throws exception")
  void whenFromPersistenceGivenNullEmail_thenThrowsException() {
    UserId userId = UserId.from(UUID.randomUUID());
    String name = "John Doe";
    UserStatus status = UserStatus.PENDING_VERIFICATION;
    boolean emailVerified = false;
    Instant emailVerifiedAt = null;
    Instant createdAt = Instant.now();
    Instant updatedAt = Instant.now();
    assertThrows(
        NullPointerException.class,
        () ->
            User.fromPersistence(
                userId, name, null, status, emailVerified, emailVerifiedAt, createdAt, updatedAt));
  }

  @Test
  @DisplayName("When fromPersistence given null status then sets default status")
  void whenFromPersistenceGivenNullStatus_thenSetsDefaultStatus() {
    UserId userId = UserId.from(UUID.randomUUID());
    String name = "John Doe";
    Email email = Email.of("john.doe@example.com");
    UserStatus status = null;
    boolean emailVerified = false;
    Instant emailVerifiedAt = null;
    Instant createdAt = Instant.now();
    Instant updatedAt = Instant.now();
    User user =
        User.fromPersistence(
            userId, name, email, status, emailVerified, emailVerifiedAt, createdAt, updatedAt);
    assertEquals(UserStatus.PENDING_VERIFICATION, user.getStatus());
  }

  @Test
  @DisplayName("When fromPersistence given null createdAt then throws exception")
  void whenFromPersistenceGivenNullCreatedAt_thenThrowsException() {
    UserId userId = UserId.from(UUID.randomUUID());
    String name = "John Doe";
    Email email = Email.of("john.doe@example.com");
    UserStatus status = UserStatus.PENDING_VERIFICATION;
    boolean emailVerified = false;
    Instant emailVerifiedAt = null;
    Instant updatedAt = Instant.now();
    assertThrows(
        NullPointerException.class,
        () ->
            User.fromPersistence(
                userId, name, email, status, emailVerified, emailVerifiedAt, null, updatedAt));
  }
}
