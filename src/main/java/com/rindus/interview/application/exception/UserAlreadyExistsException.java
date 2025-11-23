package com.rindus.interview.application.exception;

import com.rindus.interview.domain.valueobject.Email;

/** Exception indicating that a User with the given email already exists. */
public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(Email email) {
    super("User with email already exists: " + email);
  }
}
