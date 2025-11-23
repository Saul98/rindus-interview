package com.rindus.interview.infrastructure.entrypoint.rest.exception;

import com.rindus.interview.application.exception.UserAlreadyExistsException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;

@Provider
public class UserAlreadyExistsExceptionMapper
  implements ExceptionMapper<UserAlreadyExistsException> {

  @Context
  UriInfo uriInfo;

  @Override
  public Response toResponse(UserAlreadyExistsException ex) {
    ApiError error =
      new ApiError(
        Instant.now(),
        Response.Status.CONFLICT.getStatusCode(),
        "Conflict",
        ex.getMessage(),
        uriInfo.getPath());

    return Response.status(Response.Status.CONFLICT).entity(error).build();
  }
}
