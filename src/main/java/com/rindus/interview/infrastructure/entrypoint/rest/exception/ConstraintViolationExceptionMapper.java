package com.rindus.interview.infrastructure.entrypoint.rest.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.Instant;

@Provider
public class ConstraintViolationExceptionMapper
    implements ExceptionMapper<ConstraintViolationException> {

  @Context UriInfo uriInfo;

  @Override
  public Response toResponse(ConstraintViolationException ex) {
    String message =
        ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .findFirst()
            .orElse("Validation failed");

    ApiError error =
        new ApiError(
            Instant.now(),
            Response.Status.BAD_REQUEST.getStatusCode(),
            "Validation Error",
            message,
            uriInfo.getPath());

    return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
  }
}
