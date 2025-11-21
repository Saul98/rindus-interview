package com.rindus.interview.application.dto.command;

/**
 * Command for creating a new User.
 *
 * @param name
 * @param email
 */
public record CreateUserCommand(String name, String email) {

}
