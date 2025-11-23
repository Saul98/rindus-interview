package com.rindus.interview.domain.valueobject;

import com.rindus.interview.domain.exception.UserParameterIsRequired;
import java.util.Objects;
import java.util.regex.Pattern;

/** Value object representing a User's email address. */
public class Email {

  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

  private final String value;

  private Email(String value) {
    this.value = value;
  }

  public static Email of(String rawEmail) {
    if (rawEmail == null) {
      throw new UserParameterIsRequired("Email is required");
    }

    String normalized = rawEmail.trim().toLowerCase();
    if (!EMAIL_PATTERN.matcher(normalized).matches()) {
      throw new UserParameterIsRequired(rawEmail, "Invalid email address");
    }

    return new Email(normalized);
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Email email)) {
      return false;
    }
    return value.equals(email.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
