package com.rindus.interview.domain.event;

import java.time.Instant;

public abstract class UserDomainEvent {

  protected final Instant occurredOn;

  UserDomainEvent(Instant occurredOn) {
    this.occurredOn = occurredOn;
  }

  UserDomainEvent() {
    this(Instant.now());
  }

  public Instant getOccurredOn() {
    return occurredOn;
  }
}
