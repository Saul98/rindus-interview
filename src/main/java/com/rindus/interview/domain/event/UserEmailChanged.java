package com.rindus.interview.domain.event;

import com.rindus.interview.domain.valueobject.Email;
import com.rindus.interview.domain.valueobject.UserId;

/** Event indicating that a User's email has been changed. */
public class UserEmailChanged extends UserDomainEvent {

  private final UserId userId;

  private final Email newEmail;

  public UserEmailChanged(UserId userId, Email newEmail) {
    this.userId = userId;
    this.newEmail = newEmail;
  }

  public UserId getUserId() {
    return userId;
  }

  public Email getNewEmail() {
    return newEmail;
  }
}
