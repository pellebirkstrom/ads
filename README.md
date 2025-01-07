# ads

Ads service

This service is built using Kotlin and Spring Boot 3 configured for building with java 17.

Technologies used:

- Postgresql as database
- [Flyway](https://github.com/flyway/flyway) for database migrations
- Spring Data JPA for database access
- Testcontainers for integration testing

## How-to build and test the service

Build requirements:

- Java 17
- Docker for running the database in integration tests

The ```mvnw``` script is included in the project to make it easy to build and run the service locally.

```bash
./mvnw verify
```

## How-to run the service locally

```bash
# Start and empty postgres in docker
docker compose -f postgres/docker-compose.yml up
```

```bash
# Run the service
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

## How-to run a specific test case

To run a specific test case from the command line, use the following command:

```bash
./mvnw -Dtest=com.example.ads.AdRepositoryIT#shouldReadAndWriteAdEntity
```

## Interact with the API

```bash
# Create an ad
curl -X POST http://localhost:8080/ads \
     -H "Content-Type: application/json" \
     -
