---
applyTo: '**/*.java'
description: "Rules for integration tests"
---

# Integration Testing Standards (IT)

Integration tests must be written **only** for classes from `api-rest` and `infrastructure`. Business logic in `domain` and `application` should be covered by Unit Tests.

## 1. Execution Context
* **Location:** All IT tests must be written in the **boot** module necessarily.
* **Test Naming:** Use descriptive names following the pattern: `should_<ExpectedBehavior>_when_<Condition>`.
  * *Example:* `should_ThrowException_when_InputIsInvalid()`
* **Class Naming:** Mirror the target class name: `AuthorControllerIT`.
* **Spring Context:** Use `@SpringBootTest`, `@WebMvcTest`, `@DataJpaTest` or Spring Extension.

## 2. Infrastructure Rules
* **No Testcontainers:** Strictly **forbidden** to use Testcontainers. Use locally running PostgreSQL instance as configured in `application-test.yml`.
* **Large Files (100MB+):** Use streaming (`MockInputStream`). **Never** create actual massive files on disk during tests.

## 3. API & Persistence
* **API:** Use `MockMvc` for `api-rest` controllers. Verify:
    * HTTP Security (roles/access).
    * Bean Validation (JSR-303).
    * JSON mapping (Snake Case/Camel Case consistency).

## 4. Stability & Cleanliness
* **No Flaky Tests:** Use **Awaitility** for async operations. `Thread.sleep()` is prohibited.
* **Assertions:** Use `assertThat(actual).usingRecursiveComparison()` for deep verification of Records and Entities.

## 5. Database & Transaction Management
* **Data Isolation:** Every test must be independent. Use `@Transactional` to roll back changes or `@Sql` scripts to clean up tables after execution.

## 6. Anti-Patterns (FORBIDDEN)
* **Shared State:** Tests that depend on the execution order or data left by a previous test.
* **Hardcoded Environment:** Using absolute paths or environment-specific URLs. Use `@DynamicPropertySource` or `application-test.yml`.
* **Ignoring Logs:** Integration tests should not produce "noise" (stack traces in logs) unless they are specifically testing error scenarios.