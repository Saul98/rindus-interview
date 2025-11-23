package com.rindus.interview.domain.aggregate;

import com.rindus.interview.domain.enums.UserStatus;
import com.rindus.interview.domain.exception.UserIsBlockedException;
import com.rindus.interview.domain.exception.UserIsNotVerifiedException;
import com.rindus.interview.domain.exception.UserParameterIsRequired;
import com.rindus.interview.domain.valueobject.Email;
import com.rindus.interview.domain.valueobject.UserId;

import java.time.Instant;
import java.util.Objects;

/**
 * Aggregate root representing a User in the domain.
 */
public class User {

  private final UserId id;
  private final Instant createdAt;
  private String name;
  private Email email;
  private UserStatus status;
  private boolean emailVerified;
  private Instant emailVerifiedAt;
  private Instant updatedAt;

  protected User(
    UserId id,
    String name,
    Email email,
    UserStatus status,
    boolean emailVerified,
    Instant emailVerifiedAt,
    Instant createdAt,
    Instant updatedAt) {
    this.id = Objects.requireNonNull(id, "id is required");
    this.name = normalizeAndValidateName(name);
    this.email = Objects.requireNonNull(email, "email is required");
    this.status = Objects.requireNonNullElse(status, UserStatus.PENDING_VERIFICATION);
    this.emailVerified = emailVerified;
    this.emailVerifiedAt = emailVerifiedAt;

    this.createdAt = Objects.requireNonNull(createdAt, "createdAt is required");
    this.updatedAt = updatedAt;
  }

  /**
   * Factory method used to reconstitute a User aggregate from persistence.
   */
  public static User fromPersistence(
    UserId id,
    String name,
    Email email,
    UserStatus status,
    boolean emailVerified,
    Instant emailVerifiedAt,
    Instant createdAt,
    Instant updatedAt) {
    return new User(id, name, email, status, emailVerified, emailVerifiedAt, createdAt, updatedAt);
  }

  public static User create(String name, String email) {
    return new User(
      UserId.newId(),
      name,
      Email.of(email),
      UserStatus.PENDING_VERIFICATION,
      false,
      null,
      Instant.now(),
      null);
  }

  public void rename(String newName) {
    this.name = normalizeAndValidateName(newName);
    touch();
  }

  public void changeEmail(String newEmail) {
    this.email = Email.of(newEmail);
    this.emailVerified = false;
    this.emailVerifiedAt = null;
    this.status = UserStatus.PENDING_VERIFICATION;
    touch();
  }

  public void markEmailVerified(Instant when) {
    if (emailVerified) {
      return;
    }
    this.emailVerified = true;
    this.emailVerifiedAt = Objects.requireNonNull(when, "verification time is required");
    if (this.status == UserStatus.PENDING_VERIFICATION) {
      this.status = UserStatus.ACTIVE;
    }
    touch();
  }

  public boolean isActive() {
    return status == UserStatus.ACTIVE;
  }

  public boolean isPendingVerification() {
    return status == UserStatus.PENDING_VERIFICATION;
  }

  public boolean isBlocked() {
    return status == UserStatus.BLOCKED;
  }

  public void activate() {
    if (!emailVerified) {
      throw new UserIsNotVerifiedException(getId());
    }
    if (isBlocked()) {
      throw new UserIsBlockedException(getId());
    }
    this.status = UserStatus.ACTIVE;
    touch();
  }

  public void block() {
    if (isBlocked()) {
      return;
    }
    this.status = UserStatus.BLOCKED;
    touch();
  }

  private void touch() {
    this.updatedAt = Instant.now();
  }

  private String normalizeAndValidateName(String rawName) {
    if (rawName == null) {
      throw new UserParameterIsRequired(getId(), "Name is required");
    }

    String normalizedName = rawName.trim();
    if (normalizedName.isBlank()) {
      throw new UserParameterIsRequired(getId(), "Name cannot be blank");
    }

    return normalizedName;
  }

  public UserId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public UserStatus getStatus() {
    return status;
  }

  public boolean isEmailVerified() {
    return emailVerified;
  }

  public Instant getEmailVerifiedAt() {
    return emailVerifiedAt;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }
}
