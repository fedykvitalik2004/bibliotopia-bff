# AI Agent Instructions: Project Architecture and Coding Standards

This document defines the architectural boundaries and development standards for the **Bibliotopia** project. AI agents must strictly adhere to these rules when generating or refactoring code. The project is built using **Spring Boot 4** and **Java 21**.

---

## 1. Modular Structure (Hexagonal Architecture)

The system is built on the principle of layer separation with dependency inversion. The direction of dependencies must always point toward the center (**domain**).

### domain
* **Purpose**: Contains core business models (entities, value objects), domain events, and pure business logic.
* **Rules**:
  * **Pure Java Only**: Complete isolation from external frameworks (Spring, Hibernate, Jackson).
  * **Zero Annotations**: Forbidden to use `@Entity`, `@Table`, or `@JsonProperty` here.
  * **Java 21**: Use `record` for immutable Value Objects.
  * **Isolation**: Forbidden to import classes from any other modules.

### application
* **Purpose**: Orchestrates business processes and implements Use Cases.
* **Ports**: Defines **Inbound** (Driver) and **Outbound** (Driven) interfaces (e.g., `BookRepositoryPort`).
* **Dependencies**: Depends exclusively on the **domain** module.
* **Rules**: No technical implementation details (no SQL, no HTTP client configs).

### infrastructure (Secondary Adapters)
* **Purpose**: Technical implementations for the ports defined in the application layer.
* **Contents**: JPA repositories, `@Entity` mappings, database configurations (PostgreSQL/Flyway), and external API clients.
* **Mapping**: Uses **MapStruct** to convert between Persistence Entities and Domain Models.
* **Dependencies**: Depends on **application** and **domain**.

### api-rest (Primary Adapters)
* **Purpose**: Entry point for external clients.
* **Contents**: `@RestController`, `@ControllerAdvice`, and DTOs.
* **Mapping**: Uses **MapStruct** to convert between DTOs and Domain Models.
* **Dependencies**: Depends on **application** and **domain**. **Forbidden** to access **infrastructure** directly.

---

## 2. API Design & Data Exchange

* **REST Semantics**: Strict adherence to HTTP methods (GET, POST, PUT, PATCH, DELETE).
* **Naming Convention**: All DTO classes **must** end with the `Dto` postfix (e.g., `AuthorResponseDto`).
* **Isolation**: Direct exposure of domain models or JPA entities in the API layer is **strictly forbidden**.
* **Status Codes**:
  * `201 Created` for successful POST.
  * `200 OK` for successful GET/PUT/PATCH.
  * `204 No Content` for successful DELETE.
* **Documentation**: Mandatory OpenAPI/Swagger annotations on all public endpoints.

---

## 3. Code Style and Technical Patterns

* **Dependency Injection**: **Constructor-based injection only**. Use of `@Autowired` is prohibited.
* **Jakarta EE**: Use `jakarta.*` namespace for all persistence and validation (no `javax.*`).
* **Persistence**: Use **Flyway** for all database migrations. SQL scripts must be stored in `src/main/resources/db/migration`.
* **Immutability**: Favor `final` fields and Java `record` types.
* **Error Handling**: Centralized logic in `api-rest` using `@ControllerAdvice`.
* **Patterns**:
  * **Builder**: Use Lombok `@Builder` for complex object construction.
  * **Mapper**: Use MapStruct for all cross-layer conversions.

---

## 4. Constraints and Prohibitions

1.  **Logic Placement**: Business logic must **never** reside in Controllers or Infrastructure adapters.
2.  **Transaction Management**: Transactions must be initiated in the **application** layer using `@Transactional` at the service level.
3.  **Cross-Module Access**: The `api-rest` module must never bypass the `application` layer to reach `infrastructure`.
4.  **Database**: No manual DDL; all changes must go through Flyway.
5.  **Clean Code**: Maintain low cognitive complexity. Follow SonarCloud-friendly practices.

---

## 5. Pre-Response Verification Checklist
*Before delivering code, verify:*
* [ ] Is the code compatible with **Spring Boot 4** and **Jakarta EE**?
* [ ] Are the **Domain** models free of framework annotations?
* [ ] Does the **Infrastructure** layer implement a **Port** from the **Application** layer?
* [ ] Are all DTOs properly suffixed with `Dto`?
* [ ] Is there any `@Autowired`? (If yes, refactor to Constructor).

---

**External Files Referenced:**
* `AGENTS.MD`
* `.github/instructions/`
* `Bibliotopia-specific-docs.md`