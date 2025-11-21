package com.rindus.app.infrastructure.entrypoint.rest.exception;

import java.time.Instant;

/**
 * API Error DTO.
 *
 * @param timestamp
 * @param status
 * @param error
 * @param message
 * @param path
 */
public record ApiError(
    Instant timestamp,
    int status,
    String error,
    String message,
    String path
) {

}
