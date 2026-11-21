# Bibliotopia BFF

## Running tests

Before running the tests, start the test PostgreSQL container from the `code/` directory:

```bash
docker compose -f docker-compose.yml -f docker-compose.test.yml up -d --wait
```

Apply the database schema and test data from the `code/` directory. Schema migrations are in `boot/src/main/resources/db/migration`; test data is in `boot/src/test/resources/db/testdata`.

```bash
./mvnw flyway:migrate -pl boot \
  -Dflyway.url=jdbc:postgresql://localhost:5432/bibliotopia \
  -Dflyway.user=user \
  -Dflyway.password=user-pwd \
  -Dflyway.locations=filesystem:boot/src/main/resources/db/migration,filesystem:boot/src/test/resources/db/testdata
```

Finally, run the unit and integration tests:

```bash
./mvnw clean install
```

Run the migration command only against the test database. On its first run, the test database schema must be empty. If an existing Docker volume contains tables created by the former Docker init scripts, reset that test volume before migrating.
