package com.rindus.app.domain.exception;

import com.rindus.app.domain.valueobject.UserId;

/**
 * Exception indicating that a User is blocked.
 */
public class UserIsBlockedException extends DomainException {

  public UserIsBlockedException(UserId id) {
    super("The user with id " + id + " is blocked.");
  }
}
