package com.rindus.app.application.exception;

import com.rindus.app.domain.valueobject.Email;

/**
 * Exception indicating that a User with the given email already exists.
 */
public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(Email email) {
    super("User with email already exists: " + email);
  }
}
