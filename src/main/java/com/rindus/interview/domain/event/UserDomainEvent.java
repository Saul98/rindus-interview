package com.rindus.interview.domain.event;

import java.time.Instant;

public interface UserDomainEvent {

  Instant occurredAt();
}
