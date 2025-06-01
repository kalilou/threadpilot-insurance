```diff
                      _____ _                        _       _ _       _
                      |_   _| |__  _ __ ___  __ _  __| |_ __ (_) | ___ | |_
                        | | | '_ \| '__/ _ \/ _` |/ _` | '_ \| | |/ _ \| __|
                        | | | | | | | |  __/ (_| | (_| | |_) | | | (_) | |_
                        |_| |_| |_|_|  \___|\__,_|\__,_| .__/|_|_|\___/ \__|
                                          _     _     |_|
                                __   _____| |__ (_) ___| | ___
                                \ \ / / _ \ '_ \| |/ __| |/ _ \
                                \ V /  __/ | | | | (__| |  __/
                                  \_/ \___|_| |_|_|\___|_|\___|
```

# threadpilot-vehicle

A Spring Boot microservice providing vehicle information for users, with RESTful endpoints, database integration, and contract testing.

## Features

- REST API to fetch vehicle details by registration number or by owner.
- Integration with PostgreSQL for persistent vehicle data.
- Database migrations managed by Liquibase.
- OpenAPI documentation via Springdoc.
- Feature toggling via Togglz.
- Contract testing with Spring Cloud Contract.
- Sonar for static code analysis.
- Continuous Integration and Deployment using GitHub workflows.
- Containerized with Docker and ready for orchestration with Docker Compose.

## API

### Get Vehicle by Registration Number

```
GET /api/vehicles/{registrationNumber}
```

**Path Parameter:**
- `registrationNumber` — The vehicle's registration number.

**Response:**
- Vehicle details for the given registration number.

### Get Vehicles by Owner

```
GET /api/vehicles/owner/{registrationNumber}
```

**Path Parameter:**
- `registrationNumber` — The owner's personal identification number.

**Response:**
- List of vehicles owned by the user.

**Example:**
```json
[
  {
    "registrationNumber": "ABC123",
    "fuelType": "Diesel",
    "model": "XC60",
    "make": "Volvo",
    "year": 2022,
    "color": "Black",
    "ownerPersonalNumber": "199001011001",
    "mileage": 12000
  }
]
```

## Configuration

Configuration is managed via `application-dev.yml` and environment variables. Key settings:

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/vehicledb
    username: threadpilot
    password: threadpilot
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
  liquibase:
    change-log: classpath:db/changelog/db.changelog-master.yaml
```

## Database

- Uses PostgreSQL.
- Schema managed by Liquibase migrations.
- Main table: `vehicle`.
- Example changelogs: `001-initial-create-tables.yaml`, `002-add-vehicle-20250528.yml`.

## Running Locally

## Running Locally
### Prerequisites
- Postgres Database up and running

```bash
docker run --name threadpilot -e POSTGRES_DB=vehicledb -e POSTGRES_PASSWORD=threadpilot -e POSTGRES_USER=threadpilot -p 5433:5432 -d postgres
```

### Start Service

```sh
./gradlew bootRun --args='--spring.profiles.active=dev'
```

This will start:
- `vehicle-service` (on port 8080)

Please refer to [README on how to start the service using docker compose](../README.md#running-locally)

### API Documentation

Once running, access the OpenAPI UI at:
```
http://localhost:8081/swagger-ui/index.html
```

## Testing

- Contract and unit tests are provided (see `ContractVerifierBase.java`).
- Run tests with:
  ```sh
  ./gradlew test
  ```

## Development
- Main source: `src/main/java/com/threadpilot/vehicle`
- Configuration: `src/main/resources/config`
- Database migrations: `src/main/resources/db/changelog`
- Testing: `src/tests/`

