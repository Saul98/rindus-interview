package com.rindus.interview.domain.exception;

import com.rindus.interview.domain.valueobject.UserId;

/**
 * Exception indicating that a User is blocked.
 */
public class UserIsBlockedException extends DomainException {

  public UserIsBlockedException(UserId id) {
    super("The user with id " + id + " is blocked.");
  }
}
