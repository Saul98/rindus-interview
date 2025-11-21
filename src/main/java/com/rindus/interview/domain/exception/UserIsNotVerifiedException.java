package com.rindus.interview.domain.exception;

import com.rindus.interview.domain.valueobject.UserId;

/**
 * Exception indicating that a User is not verified yet.
 */
public class UserIsNotVerifiedException extends DomainException {

  public UserIsNotVerifiedException(UserId id) {
    super("The user with id " + id + " is not verified yet.");
  }
}
