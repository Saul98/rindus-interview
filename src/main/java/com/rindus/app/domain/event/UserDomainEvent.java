package com.rindus.app.domain.event;

import java.time.Instant;

public interface UserDomainEvent {

  Instant occurredAt();
}
