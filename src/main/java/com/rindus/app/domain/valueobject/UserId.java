package com.rindus.app.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value object representing a User's unique identifier.
 */
public final class UserId {

  private final UUID value;

  private UserId(UUID value) {
    this.value = Objects.requireNonNull(value, "UserId value is required");
  }

  public static UserId newId() {
    return new UserId(UUID.randomUUID());
  }

  public static UserId from(UUID value) {
    return new UserId(value);
  }

  public static UserId fromString(String value) {
    return new UserId(UUID.fromString(value));
  }

  public UUID getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserId userId)) {
      return false;
    }
    return value.equals(userId.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
