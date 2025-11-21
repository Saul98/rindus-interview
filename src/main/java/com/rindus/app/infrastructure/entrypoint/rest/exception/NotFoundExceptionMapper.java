package com.rindus.app.infrastructure.entrypoint.rest.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.Instant;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

  @Context
  UriInfo uriInfo;

  @Override
  public Response toResponse(NotFoundException ex) {
    ApiError error = new ApiError(
        Instant.now(),
        Response.Status.NOT_FOUND.getStatusCode(),
        "Not Found",
        ex.getMessage(),
        uriInfo.getPath()
    );

    return Response.status(Response.Status.NOT_FOUND).entity(error).build();
  }
}
