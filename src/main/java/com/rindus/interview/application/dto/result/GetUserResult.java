package com.rindus.interview.application.dto.result;

import java.util.UUID;

/**
 * Result of a successful User retrieval.
 *
 * @param id
 * @param name
 * @param email
 */
public record GetUserResult(UUID id, String name, String email) {
}
