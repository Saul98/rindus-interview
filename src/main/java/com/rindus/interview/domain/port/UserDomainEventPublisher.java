package com.rindus.interview.domain.port;

import com.rindus.interview.domain.event.UserDomainEvent;

public interface UserDomainEventPublisher {

  void publish(UserDomainEvent event);
}
