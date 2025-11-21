package com.rindus.app.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

@QuarkusTest
class UsersResourceIT {

  @Test
  void create_and_get_user_persists_in_postgres() {
    // Create user
    String location =
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"Alice\",\"email\":\"alice@example.com\"}")
            .when()
            .post("/users")
            .then()
            .statusCode(201)
            .header("Location", notNullValue())
            .body("id", notNullValue())
            .body("name", equalTo("Alice"))
            .body("email", equalTo("alice@example.com"))
            .extract().header("Location");

    // Follow the Location to fetch the user
    given()
        .accept(ContentType.JSON)
        .when()
        .get(location)
        .then()
        .statusCode(200)
        .body("name", equalTo("Alice"))
        .body("email", equalTo("alice@example.com"));
  }
}
