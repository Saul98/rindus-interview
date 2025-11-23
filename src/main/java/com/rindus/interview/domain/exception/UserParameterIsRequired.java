package com.rindus.interview.domain.exception;

import com.rindus.interview.domain.valueobject.UserId;

/** Exception indicating that a required parameter is missing. */
public class UserParameterIsRequired extends DomainException {

  public UserParameterIsRequired(UserId id, String message) {
    super("For user with id " + id + ". " + message);
  }

  public UserParameterIsRequired(String rawEmail, String message) {
    super("For user with email " + rawEmail + ". " + message);
  }

  public UserParameterIsRequired(String message) {
    super(message);
  }
}
