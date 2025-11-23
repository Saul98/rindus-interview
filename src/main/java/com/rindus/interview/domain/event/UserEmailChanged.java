package com.rindus.interview.domain.event;

import com.rindus.interview.domain.valueobject.UserId;

import java.time.Instant;

/**
 * Event indicating that a User's email has been changed.
 *
 * @param userId
 * @param newEmail
 * @param occurredAt
 */
public record UserEmailChanged(UserId userId, String newEmail, Instant occurredAt)
  implements UserDomainEvent {
}
