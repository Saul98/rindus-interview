### Rindus Interview: Cloud‑Native Microservice (Quarkus, Hexagonal/DDD)

This repository contains a small cloud‑native microservice intended for a Senior Backend Developer interview exercise. It is implemented with Quarkus and follows a hexagonal (ports & adapters) architecture with DDD‑inspired layering. The service exposes simple user management capabilities to demonstrate design, testing, and production‑readiness practices.

Key capabilities
- Create a user
- Retrieve a user by ID

Tech stack
- Java 21
- Quarkus
- Maven
- JAX‑RS (REST), Bean Validation
- JPA/Hibernate with Panache
- MapStruct for compile-time mappings between domain and JPA entities
- PostgreSQL (dev), Dev Services PostgreSQL (tests)
- OpenAPI/Swagger UI, Health checks, Metrics

Architecture overview
- Hexagonal (Ports & Adapters) with clear separation:
  - Domain: aggregates and value objects; no framework dependencies
  - Application: use cases orchestrating domain logic
  - Infrastructure: adapters (REST, persistence via Panache/JPA)
- UserRepository is a domain port implemented by a Panache adapter
- DDD inspiration: simple User aggregate with basic invariants and validation
 - MapStruct generates the mapper to translate Domain <-> JPA entity (User <-> UserEntity)

Project layout (important packages)
- com.rindus.interview.domain: aggregate, events, and repository port
- com.rindus.interview.application: use cases, DTOs, and exceptions
- com.rindus.interview.infrastructure.entrypoint.rest: REST resource and DTOs
- com.rindus.interview.infrastructure.repository: JPA entity, mapper, Panache adapter

API
- Base URL: http://localhost:8080

1) Create user
  - POST /users
  - Request body:
    {
      "name": "Jane Doe",
      "email": "jane.doe@example.com"
    }
  - Responses:
    - 201 Created with Location header and body:
      { "id": "<uuid>", "name": "Jane Doe", "email": "jane.doe@example.com" }
    - 400 Bad Request for invalid input
    - 409 Conflict if user with the same email already exists

  Example curl:
    curl -i -X POST http://localhost:8080/users \
      -H 'Content-Type: application/json' \
      -d '{"name":"Jane Doe","email":"jane.doe@example.com"}'

2) Get user by ID
  - GET /users/{id}
  - Responses:
    - 200 OK: { "id": "<uuid>", "name": "...", "email": "..." }
    - 404 Not Found

  Example curl:
    curl -s http://localhost:8080/users/<uuid> | jq .

OpenAPI, health, and metrics
- Swagger UI: http://localhost:8080/q/swagger-ui
- OpenAPI spec: http://localhost:8080/q/openapi
- Health: http://localhost:8080/q/health
- Metrics (Micrometer/MP Metrics): http://localhost:8080/q/metrics

Quickstart
Prerequisites
- Java 21
- Maven 3.8+
- Docker (to run PostgreSQL locally)

1) Start PostgreSQL for development
  The dev profile connects to a local PostgreSQL instance using these defaults (see application.properties):
  - DB_URL: jdbc:postgresql://localhost:5432/usersdb
  - DB_USER: postgres
  - DB_PASSWORD: postgres

  Start a containerized Postgres:
    docker run --name usersdb -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=usersdb \
      -p 5432:5432 -d postgres:16

2) Run the application in dev mode (hot reload)
    mvn quarkus:dev

3) Try the API
  Use the curl examples above or open Swagger UI at /q/swagger-ui.

Configuration
- application.properties profiles:
  - %dev: uses local PostgreSQL; schema updated automatically (quarkus.hibernate-orm.database.generation=update)
  - %test: Dev Services ephemeral PostgreSQL; schema drop-and-create per test run
- Environment variables (override defaults):
  - DB_URL, DB_USER, DB_PASSWORD
  Export them before starting dev mode or pass with -Dquarkus.profile=dev.

Testing
- Integration tests are provided for the application use cases and run with Dev Services (no external DB needed):
    mvn test

Build and run as a JAR
- Package:
    mvn clean package -DskipTests
- Run:
    java -jar target/quarkus-app/quarkus-run.jar

Containerization (optional, not required to run tests)
- A minimal Dockerfile for the fast-jar runner could look like:
    FROM eclipse-temurin:21-jre
    WORKDIR /app
    COPY target/quarkus-app/ /app/
    ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"
    EXPOSE 8080
    CMD ["java","-jar","/app/quarkus-run.jar"]

Design and operational notes
- Input validation occurs in the domain/use case layers; REST layer uses Bean Validation for payloads
- Conflict detection on email uses repository existsByEmail
- Logging: domain/application actions are logged at appropriate levels
- Observability: health and metrics endpoints exposed via Quarkus management endpoints
- Persistence: Panache repository adapter maps domain <-> JPA entity

Future improvements (discussion points for the interview)
- Idempotency/eTag for create operations
- Pagination and listing endpoints
- Email uniqueness index at the DB level + error translation
- Flyway/Liquibase migrations for schema evolution
- CI/CD pipeline, container image build, and Kubernetes manifests
- Security (OIDC/JWT), request rate limiting, and structured logging

License
- For interview purposes only. Use as a reference implementation during the technical discussion.
