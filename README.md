# Rindus Interview: Cloud‑Native Microservice (Quarkus, Hexagonal/DDD)

This repository contains a small **cloud-native microservice** for a Senior Backend Developer interview exercise. It demonstrates a **Hexagonal architecture** with **DDD-inspired layering** using Quarkus. The service exposes **user management capabilities** while showcasing **design, testing, and production-ready practices**.

---

## Key Capabilities

* **Create a user**
* **Retrieve a user by ID**

---

## Tech Stack

* Java 21
* Quarkus
* Maven
* JAX-RS (REST), Bean Validation
* JPA/Hibernate with Panache
* MapStruct (compile-time domain ↔ JPA mapping)
* PostgreSQL (dev), Dev Services PostgreSQL (tests)
* OpenAPI/Swagger UI, Health checks, Metrics

---

## Architecture Overview

* **Hexagonal (Ports & Adapters)** with a clear separation of concerns:

  * **Domain:** aggregates, value objects, invariants (framework-free)
  * **Application:** use cases orchestrating domain logic
  * **Infrastructure:** adapters (REST endpoints, persistence via Panache/JPA)
* **UserRepository** is a domain port implemented by a Panache adapter
* **DDD inspiration:** User aggregate with invariants and validation
* **MapStruct** translates Domain ↔ JPA entity (`User <-> UserEntity`)

---

## Project Layout (important packages)

* `com.rindus.interview.domain` – aggregate, events, repository port
* `com.rindus.interview.application` – use cases, DTOs, exceptions
* `com.rindus.interview.infrastructure.entrypoint.rest` – REST resource, DTOs
* `com.rindus.interview.infrastructure.repository` – JPA entity, mapper, Panache adapter

---

## API

**Base URL:** `http://localhost:8080`

### 1. Create User

* **POST** `/users`
* **Request Body:**

```json
{
  "name": "Jane Doe",
  "email": "jane.doe@example.com"
}
```

* **Responses:**

  * `201 Created` – returns created user with ID
  * `400 Bad Request` – invalid input
  * `409 Conflict` – user with email already exists

**Example:**

```bash
curl -i -X POST http://localhost:8080/users \
  -H 'Content-Type: application/json' \
  -d '{"name":"Jane Doe","email":"jane.doe@example.com"}'
```

### 2. Get User by ID

* **GET** `/users/{id}`
* **Responses:**

  * `200 OK` – returns user JSON
  * `404 Not Found` – if user not found

**Example:**

```bash
curl -s http://localhost:8080/users/<uuid> | jq .
```

---

## OpenAPI, Health, and Metrics

* **Swagger UI:** `http://localhost:8080/q/swagger-ui`
* **OpenAPI spec:** `http://localhost:8080/q/openapi`
* **Health:** `http://localhost:8080/q/health`

---

## Quickstart

### Prerequisites

* Java 21
* Maven 3.8+
* Docker (for local PostgreSQL)

### 1. Start PostgreSQL

**Dev defaults:**

* URL: `jdbc:postgresql://localhost:5432/usersdb`
* User: `postgres`
* Password: `postgres`

**Docker command:**

```bash
docker run --name usersdb \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=usersdb \
  -p 5432:5432 -d postgres:16
```

### 2. Run Application in Dev Mode

```bash
mvn quarkus:dev
```

### 3. Use API

* Test with **curl** or **Swagger UI** (`/q/swagger-ui`)

---

## Configuration

* **Profiles:**

  * `%dev` – local PostgreSQL; schema updated automatically
  * `%test` – Dev Services ephemeral DB; schema drop-and-create per test
* **Environment Variables:** override defaults: `DB_URL`, `DB_USER`, `DB_PASSWORD`

---

## Testing

* **Integration tests** use Dev Services (no external DB required):

```bash
mvn test
```

---

## Design and Operational Notes

* **Validation:** Domain/use-case layer; REST layer uses Bean Validation
* **Conflict detection:** Email uniqueness checked via repository
* **Logging:** Domain/application actions logged appropriately
* **Observability:** Health endpoint exposed
* **Persistence:** Panache adapter maps domain ↔ JPA entity

---

## Future Improvements

* Idempotency / eTag support for create operations.
* Pagination / list endpoints.
* DB-level uniqueness + error translation.
* Schema migrations: Flyway / Liquibase.
* CI/CD, container image, Kubernetes manifests (production environment).
* Security: OIDC/JWT, rate limiting, structured logging.
* Split user domain in more value objects, add a state pattern for the user status.
* Test Data Builder pattern for test data standardization.
* DB indexes for performance (unique email, status, partial indexes).
* Open for adding some event-drive approach, maybe using Kafka, AWS SQS, MQTT, etc.

---

## License

* For **interview purposes only**. Reference implementation for technical discussion.

---
