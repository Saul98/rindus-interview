package com.rindus.app.infrastructure.entrypoint.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Payload for creating a new User.
 *
 * @param name
 * @param email
 */
public record CreateUserPayload(@NotBlank(message = "Name is required") String name,
                                @NotBlank @Email(message = "Email must be valid") String email) {

}
