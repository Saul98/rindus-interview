package com.rindus.interview.domain.event;

import com.rindus.interview.domain.valueobject.UserId;

/** Event indicating that a User has been activated. */
public class UserActivated extends UserDomainEvent {

  private final UserId userId;

  public UserActivated(UserId userId) {
    this.userId = userId;
  }

  public UserId getUserId() {
    return userId;
  }
}
