package com.rindus.interview.domain.event;

import com.rindus.interview.domain.valueobject.Email;
import com.rindus.interview.domain.valueobject.UserId;

/** Event indicating that a User has been registered. */
public class UserRegistered extends UserDomainEvent {

  private final UserId userId;

  private final Email email;

  public UserRegistered(UserId userId, Email email) {
    this.userId = userId;
    this.email = email;
  }

  public UserId getUserId() {
    return userId;
  }

  public Email getEmail() {
    return email;
  }
}
