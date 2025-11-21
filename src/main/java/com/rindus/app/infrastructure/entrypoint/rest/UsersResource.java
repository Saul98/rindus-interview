package com.rindus.app.infrastructure.entrypoint.rest;

import com.rindus.app.application.dto.command.CreateUserCommand;
import com.rindus.app.application.dto.result.CreateUserResult;
import com.rindus.app.application.usecase.CreateUserUseCase;
import com.rindus.app.application.usecase.GetUserUseCase;
import com.rindus.app.infrastructure.entrypoint.rest.dto.request.CreateUserPayload;
import com.rindus.app.infrastructure.entrypoint.rest.dto.response.UserResponseDto;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.UUID;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * REST resource for managing Users.
 */
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Users", description = "Operations related to user management")
public class UsersResource {

  private final CreateUserUseCase createUserUseCase;

  private final GetUserUseCase getUserUseCase;

  @Inject
  public UsersResource(CreateUserUseCase createUserUseCase, GetUserUseCase getUserUseCase) {
    this.createUserUseCase = createUserUseCase;
    this.getUserUseCase = getUserUseCase;
  }

  @POST
  @Operation(summary = "Create a new user", description = "Creates a user using name and email.")
  public Response create(@Valid CreateUserPayload payload, @Context UriInfo uriInfo) {
    CreateUserCommand cmd = new CreateUserCommand(payload.name(), payload.email());
    CreateUserResult result = createUserUseCase.execute(cmd);

    URI location = uriInfo
        .getAbsolutePathBuilder()
        .path(result.id().toString())
        .build();

    return Response
        .created(location)
        .entity(UserResponseDto.from(result))
        .build();
  }

  @GET
  @Path("/{id}")
  @Operation(summary = "Get a user by ID",
      description = "Returns the user associated with the given UUID.")
  public Response get(@PathParam("id") UUID id) {
    return getUserUseCase.execute(id)
        .map(UserResponseDto::from)
        .map(Response::ok)
        .map(Response.ResponseBuilder::build)
        .orElseThrow(() -> new NotFoundException("User not found: " + id));
  }
}
