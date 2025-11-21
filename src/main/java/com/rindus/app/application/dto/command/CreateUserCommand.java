package com.rindus.app.application.dto.command;

/**
 * Command for creating a new User.
 *
 * @param name
 * @param email
 */
public record CreateUserCommand(String name, String email) {

}
