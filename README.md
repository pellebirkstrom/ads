# ads
Ads service

This service is built using Spring Boot 3 configured for building with java 17.
It using:


- Postgresql as database
- [Flyway](https://github.com/flyway/flyway) for database migrations
- Spring Data JPA for database access

The ```mvnw``` script is included in the project to make it easy to build and run the service locally.

## How-to build and test the service

```bash
./mvnw verify
```


## How-to run the service locally (without docker)

```bash
# Start and empty postgres in docker
docker compose -f postgres/docker-compose.yml up
```

```bash
# Run the service
./mvnw spring-boot:run -Dspring.profiles.active=dev
```
## How-to run the service locally with docker

TODO: Add instructions

## Interact with the API

```bash
# Create an ad
curl -X POST http://localhost:8080/ads \
     -H "Content-Type: application/json" \
     -d '{
           "subject":"This is a subject",
           "body":"This is a body",
           "price":100,
           "email":"foo@bar.com"
         }'
```
```bash
# Get one ad by id
curl -X GET http://localhost:8080/ads/423bb38b-bf8e-45d8-85d8-2ffb7678aba7
```
```bash
# Get list of ads with default order and page size
curl -X GET http://localhost:8080/ads
```
```bash
# Get list of ads with set ordering, direction and page size
curl -X GET http://localhost:8080/ads?sortBy=createdAt&direction=desc&size=100
```
