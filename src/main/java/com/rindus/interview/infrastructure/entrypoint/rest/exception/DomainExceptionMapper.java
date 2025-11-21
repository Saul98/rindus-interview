package com.rindus.interview.infrastructure.entrypoint.rest.exception;

import com.rindus.interview.domain.exception.DomainException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.Instant;

@Provider
public class DomainExceptionMapper implements ExceptionMapper<DomainException> {

  @Context
  UriInfo uriInfo;

  @Override
  public Response toResponse(DomainException ex) {
    ApiError error = new ApiError(
        Instant.now(),
        Response.Status.BAD_REQUEST.getStatusCode(),
        "Domain Error",
        ex.getMessage(),
        uriInfo.getPath()
    );

    return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
  }
}
