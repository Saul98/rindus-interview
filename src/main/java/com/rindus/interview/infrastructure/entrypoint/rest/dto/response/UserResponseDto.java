package com.rindus.interview.infrastructure.entrypoint.rest.dto.response;

import com.rindus.interview.application.dto.result.CreateUserResult;
import com.rindus.interview.application.dto.result.GetUserResult;

/**
 * Response DTO for a User.
 *
 * @param id
 * @param name
 * @param email
 */
public record UserResponseDto(String id, String name, String email) {

  public static UserResponseDto from(CreateUserResult result) {
    return new UserResponseDto(result.id().toString(), result.name(), result.email());
  }

  public static UserResponseDto from(GetUserResult result) {
    return new UserResponseDto(result.id().toString(), result.name(), result.email());
  }
}
