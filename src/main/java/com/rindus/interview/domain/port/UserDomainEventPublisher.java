package com.rindus.interview.domain.port;

import com.rindus.interview.domain.event.UserDomainEvent;

/**
 * Domain port for publishing UserDomainEvents.
 */
public interface UserDomainEventPublisher {

  void publish(UserDomainEvent event);
}
