package com.rindus.app.application.dto.result;

import java.util.UUID;

/**
 * Result of a successful User creation.
 *
 * @param id
 * @param name
 * @param email
 */
public record CreateUserResult(UUID id, String name, String email) {

}
