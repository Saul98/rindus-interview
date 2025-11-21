package com.rindus.interview.infrastructure.entrypoint.rest.exception;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.Instant;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

  @Context
  UriInfo uriInfo;

  @Override
  public Response toResponse(BadRequestException ex) {
    ApiError error = new ApiError(
        Instant.now(),
        Response.Status.BAD_REQUEST.getStatusCode(),
        "Bad Request",
        ex.getMessage(),
        uriInfo.getPath()
    );

    return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
  }
}
