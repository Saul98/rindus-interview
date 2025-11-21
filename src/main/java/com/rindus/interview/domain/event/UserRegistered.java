package com.rindus.interview.domain.event;

import com.rindus.interview.domain.valueobject.UserId;
import java.time.Instant;

/**
 * Event indicating that a User has been registered.
 *
 * @param userId
 * @param email
 * @param occurredAt
 */
public record UserRegistered(UserId userId, String email, Instant occurredAt) implements UserDomainEvent {

}
