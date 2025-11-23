package com.rindus.interview.infrastructure.entrypoint.rest.exception;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "ApiError")
public record ErrorResponseSchema(
  @Schema(description = "Timestamp of the error") Instant timestamp,
  @Schema(description = "HTTP status code") int status,
  @Schema(description = "Short error type") String error,
  @Schema(description = "Detailed message") String message,
  @Schema(description = "Path of the request that caused the error") String path) {
}
