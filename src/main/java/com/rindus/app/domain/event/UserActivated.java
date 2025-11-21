package com.rindus.app.domain.event;

import com.rindus.app.domain.valueobject.UserId;
import java.time.Instant;

/**
 * Event indicating that a User has been activated.
 *
 * @param userId
 * @param occurredAt
 */
public record UserActivated(UserId userId, Instant occurredAt) implements UserDomainEvent {

}
