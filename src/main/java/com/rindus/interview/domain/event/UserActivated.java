package com.rindus.interview.domain.event;

import com.rindus.interview.domain.valueobject.UserId;
import java.time.Instant;

/**
 * Event indicating that a User has been activated.
 *
 * @param userId
 * @param occurredAt
 */
public record UserActivated(UserId userId, Instant occurredAt) implements UserDomainEvent {

}
