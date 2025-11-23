package com.rindus.interview.infrastructure.entrypoint.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.rindus.interview.infrastructure.repository.persistence.panache.PanacheUserRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Contract test for: {@link UsersResource}. */
@QuarkusTest
@Transactional
class UsersResourceContractIT {

  @Inject PanacheUserRepository userRepository;

  @BeforeEach
  void cleanDatabase() {
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("POST /users - when valid payload then 201 Created with Location and body")
  void postUsers_validPayload_returns201WithLocationAndBody() {
    String name = "Alice Smith";
    String email = "alice.smith@example.com";

    given()
        .contentType(ContentType.JSON)
        .body(Map.of("name", name, "email", email))
        .when()
        .post("/users")
        .then()
        .statusCode(201)
        .contentType(containsString("application/json"))
        .header(
            "Location",
            allOf(containsString("/users/"), matchesPattern(".*/users/[0-9a-fA-F-]{36}$")))
        .body("id", matchesPattern("[0-9a-fA-F-]{36}"))
        .body("name", equalTo(name))
        .body("email", equalTo(email));
  }

  @Test
  @DisplayName("POST /users - when name is missing then 400 with validation error schema")
  void postUsers_missingName_returns400ValidationError() {
    given()
        .contentType(ContentType.JSON)
        .body(Map.of("email", "someone@example.com"))
        .when()
        .post("/users")
        .then()
        .statusCode(400)
        .contentType(containsString("application/json"))
        .body("status", equalTo(400))
        .body("error", equalTo("Validation Error"))
        .body("message", equalTo("Name is required"))
        .body("path", startsWith("/users"))
        .body("timestamp", notNullValue());
  }

  @Test
  @DisplayName("POST /users - when email invalid then 400 with validation error schema")
  void postUsers_invalidEmail_returns400ValidationError() {
    given()
        .contentType(ContentType.JSON)
        .body(Map.of("name", "Bob", "email", "not-an-email"))
        .when()
        .post("/users")
        .then()
        .statusCode(400)
        .contentType(containsString("application/json"))
        .body("status", equalTo(400))
        .body("error", equalTo("Validation Error"))
        .body("message", equalTo("Email must be valid"))
        .body("path", startsWith("/users"))
        .body("timestamp", notNullValue());
  }

  @Test
  @DisplayName("POST /users - when email missing then 400 with validation error schema")
  void postUsers_missingEmail_returns400ValidationError() {
    given()
        .contentType(ContentType.JSON)
        .body(Map.of("name", "Charlie"))
        .when()
        .post("/users")
        .then()
        .statusCode(400)
        .contentType(containsString("application/json"))
        .body("status", equalTo(400))
        .body("error", equalTo("Validation Error"))
        // Default message from @NotBlank
        .body("message", containsString("blank"))
        .body("path", startsWith("/users"))
        .body("timestamp", notNullValue());
  }

  @Test
  @DisplayName("POST /users - when email already exists then 409 Conflict with error schema")
  void postUsers_duplicateEmail_returns409Conflict() {
    String email = "dup@example.com";
    String id = createUser("Duke", email);
    Assertions.assertNotNull(id);

    given()
        .contentType(ContentType.JSON)
        .body(Map.of("name", "Another Name", "email", email))
        .when()
        .post("/users")
        .then()
        .statusCode(409)
        .contentType(containsString("application/json"))
        .body("status", equalTo(409))
        .body("error", equalTo("Conflict"))
        .body("message", containsString("User with email already exists"))
        .body("path", startsWith("/users"))
        .body("timestamp", notNullValue());
  }

  @Test
  @DisplayName("GET /users/{id} - when user exists then 200 with expected body")
  void getUser_existing_returns200() {
    String email = "getme@example.com";
    String name = "Get Me";
    String id = createUser(name, email);

    given()
        .when()
        .get("/users/{id}", id)
        .then()
        .statusCode(200)
        .contentType(containsString("application/json"))
        .body("id", equalTo(id))
        .body("name", equalTo(name))
        .body("email", equalTo(email));
  }

  @Test
  @DisplayName("GET /users/{id} - when user not found then 404 with error schema")
  void getUser_notFound_returns404() {
    String id = UUID.randomUUID().toString();

    given()
        .when()
        .get("/users/{id}", id)
        .then()
        .statusCode(404)
        .contentType(containsString("application/json"))
        .body("status", equalTo(404))
        .body("error", equalTo("Not Found"))
        .body("message", allOf(startsWith("User not found"), containsString(id)))
        .body("path", startsWith("/users/"))
        .body("timestamp", notNullValue());
  }

  @Test
  @DisplayName("GET /users/{id} - when id is not a UUID then 404 Bad Request with error schema")
  void getUser_invalidUuid_returns400() {
    given()
        .when()
        .get("/users/{id}", "not-a-uuid")
        .then()
        .statusCode(404)
        .contentType(containsString("application/json"))
        .body("status", equalTo(404))
        .body("error", equalTo("Not Found"))
        .body("message", not(emptyOrNullString()))
        .body("path", startsWith("/users/"))
        .body("timestamp", notNullValue());
  }

  private String createUser(String name, String email) {
    return given()
        .contentType(ContentType.JSON)
        .body(Map.of("name", name, "email", email))
        .when()
        .post("/users")
        .then()
        .statusCode(201)
        .extract()
        .path("id");
  }
}
