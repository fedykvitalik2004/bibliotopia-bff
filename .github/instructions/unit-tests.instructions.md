---
applyTo: '**/*.java'
description: 'Rules for unit tests'
---

# Unit Testing Standards (Java 21 & Spring Boot 4)

## 1. General Principles

* **Frameworks:** JUnit 5 (Jupiter), AssertJ, Mockito.
* **Structure:** Follow the **Given-When-Then** (AAA) pattern for every test.
* **Assertions:** Mandatory `assertThat(actual).usingRecursiveComparison()` for **Java 21 Records** and Domain Models to ensure deep state verification.
* **Test Naming:** Use descriptive names following the pattern: `should_<ExpectedBehavior>_when_<Condition>`.
  * *Example:* `should_ThrowException_when_InputIsInvalid()`
* **Class Naming:** Mirror the target class name: `CreateUserUseCaseImplTest`.

## 2. Layer-Specific Rules

* **Domain Layer:** Pure JUnit 5 only. **Strictly forbidden** to use Spring Context, `@MockBean`, or any external dependencies. Focus on business invariants, state transitions, and Value Objects.
* **Application Layer:** Mock all Outbound Ports (Repositories, External Clients). Test orchestration logic and mapping to/from Domain models. Use `@ExtendWith(MockitoExtension.class)` if needed.
* **Mappers:** Mandatory Unit Tests for all **MapStruct** mappers (DTO <-> Domain <-> Entity). Focus on edge cases and null-safety.

## 3. Technical Constraints & Performance

* **Fast Execution:** Tests must not touch the file system, network, or real databases.
* **Determinism:** **Strictly forbidden** to use `LocalDate.now()` or `Instant.now()`.
* **No Spring in Unit Tests:** Do not use `@SpringBootTest` or `@Autowired`. Use constructor injection or `@Mock`.
* **Mocks:** Must be "dumb" (static returns). No logic (if/else) allowed inside `given(...)`.

## 4. Anti-Patterns (FORBIDDEN)

* **Multiple behaviors in one test:** Each test must verify exactly one logical outcome or state change.
* **Testing implementation details:** Test the **public API/contract**. If you refactor internal methods, the test should still pass.
* **Testing private methods:** If a private method needs a separate test, extract it to a new component.
* **Over-mocking:** Do not mock standard Java collections, simple DTOs, or internal utility classes. Mock only architectural boundaries (Ports).
* **Equality Rules:** Avoid `equals()` for Entities; always prefer `usingRecursiveComparison()` to prevent false positives from ID-only equality.
* **Concurrency Flakiness:** Never use `Thread.sleep()`. Use **Awaitility** for testing asynchronous logic or Virtual Threads.