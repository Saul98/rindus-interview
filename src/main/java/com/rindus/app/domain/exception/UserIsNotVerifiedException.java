package com.rindus.app.domain.exception;

import com.rindus.app.domain.valueobject.UserId;

/**
 * Exception indicating that a User is not verified yet.
 */
public class UserIsNotVerifiedException extends DomainException {

  public UserIsNotVerifiedException(UserId id) {
    super("The user with id " + id + " is not verified yet.");
  }
}
