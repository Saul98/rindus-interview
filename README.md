### Rindus Interview App (Quarkus, Hexagonal Architecture)

This is a minimal Quarkus application following a hexagonal (ports & adapters) architecture and DDD-inspired layering. It exposes two HTTP endpoints to create and retrieve a user.

- Build tool: Maven
- Java: 17
- Run: Quarkus dev mode or packaged JAR

Architecture layout:
- domain: entities and ports (interfaces)
- application: use cases (business logic)
- infrastructure: implementations of ports (PostgreSQL via JPA/Hibernate, and an optional in-memory adapter)
- adapters: web API (JAX-RS resource and DTOs)

Endpoints
- POST /users — Create a user
  - Request body (JSON): { "name": "John Doe", "email": "john@example.com" }
  - Response: 201 Created with JSON body and Location header
- GET /users/{id} — Retrieve a user by ID
  - Response: 200 OK with JSON body, or 404 if not found

Run locally
1) Dev mode (hot reload):
   mvn quarkus:dev

2) Package and run:
   mvn package
   java -jar target/quarkus-app/quarkus-run.jar

Curl examples
- Create user:
  curl -i -X POST \
    -H 'Content-Type: application/json' \
    -d '{"name":"Jane Doe","email":"jane@example.com"}' \
    http://localhost:8080/users

- Retrieve user:
  curl -i http://localhost:8080/users/<user-id>

Persistence
- Default adapter: PostgreSQL using Hibernate ORM (JPA). The in-memory adapter is available only in the 'inmem' build profile to avoid bean ambiguity and fit hexagonal architecture.

Run PostgreSQL locally with Docker (development)
1) Start container (builds an image with a simple init script):
   docker compose up -d postgres

   The database will be available at jdbc:postgresql://localhost:5432/appdb with app/app credentials.

2) Run the app in dev mode using the dev profile (already default):
   mvn quarkus:dev

   Quarkus config (src/main/resources/application.properties) points dev profile to the above DB and will auto-update the schema.

3) Optional: Run with in-memory repository instead of PostgreSQL:
   mvn -Dquarkus.profile=inmem quarkus:dev

Integration tests
- Tests use Quarkus Dev Services to automatically start a PostgreSQL Testcontainer on demand. See UsersResourceIT.
- To run tests:
  mvn test

Notes
- Input validation is handled in the use case with basic checks.
