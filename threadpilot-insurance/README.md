```diff
 _____ _                        _       _ _       _
|_   _| |__  _ __ ___  __ _  __| |_ __ (_) | ___ | |_
  | | | '_ \| '__/ _ \/ _` |/ _` | '_ \| | |/ _ \| __|
  | | | | | | | |  __/ (_| | (_| | |_) | | | (_) | |_
  |_|_|_| |_|_|  \___|\__,_|\__,_| .__/|_|_|\___/ \__|
    |_ _|_ __  ___ _   _ _ __ __ |_| __   ___ ___
     | || '_ \/ __| | | | '__/ _` | '_ \ / __/ _ \
     | || | | \__ \ |_| | | | (_| | | | | (_|  __/
    |___|_| |_|___/\__,_|_|  \__,_|_| |_|\___\___|
```

# threadpilot-insurance

A Spring Boot microservice providing insurance information and promotions for users, with dynamic business logic and integration with the vehicle microservice.

## Features

- REST API to fetch insurance details for a user.
- Dynamic insurance discounts and promotions based on user city and feature toggles.
- Integration with the vehicle microservice to enrich insurance responses with vehicle data.
- Feature toggling via Togglz (Stockholm, Goteborg, Black Friday, and general discounts).
- Database migrations managed by Liquibase.
- OpenAPI documentation via Springdoc.
- Sonar for static code analysis
- Continuous Integration and Deployment using GitHub workflows
- Containerized with Docker and ready for orchestration with Docker Compose.

## API

### Get Insurances for a User

```
GET /api/insurances/{personalId}
```

**Path Parameter:**
- `personalId` — The user's personal identification number.

**Response:**
- List of insurance policies for the user, including any applicable discounts, promotions, and (if available) associated vehicles.

**Example:**
```json
[
  {
    "insuranceOwnerNumber": "199001011001",
    "insurances": [
      {
        "insuranceNumber": "123456",
        "insuranceType": "Car",
        "insurancePrice": 90.0,
        "discount": 10.0,
        "insuranceStartDate": "2024-01-01",
        "insuranceEndDate": "2025-01-01",
        "insuranceStatus": "ACTIVE",
        "insuranceOwnerNumber": "199001011001"
      }
    ],
    "vehicles": [
      {
        "registrationNumber": "ABC123",
        "make": "Volvo",
        "model": "XC60",
        "year": 2022
      }
    ],
    "promotion": "Stockholm City Promotion - 15% off, valid until 2025-12-15",
    "source": "@vehicle-service"
  }
]
```

## Business Logic

- Discounts and promotions are applied based on feature toggles:
  - **Stockholm/Goteborg City Promotions:** 5% discount for users in these cities, with special promotion info.
  - **Black Friday Promotion:** Special discount and promotion info if enabled.
  - **General Discount:** 10% discount if enabled.
- Vehicle data is fetched from the `threadpilot-vehicle` microservice and included in the response if available.

## Configuration

Configuration is managed via `application-dev.yml` and environment variables. Key settings:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/insurancedb
    username: threadpilot
    password: threadpilot
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
  liquibase:
    change-log: classpath:db/changelog/db.changelog-master.yaml

togglz:
  console:
    enabled: true
    path: /togglz-console
    secured: false
  feature-enums:
    - com.threadpilot.insurance.config.Features

vehicle:
  service:
    base-url: http://localhost:8081/api/vehicles/owner/
```

## Database
- Uses PostgreSQL.
- Schema managed by Liquibase migrations.
- Main tables: `insurance`, `customer`, `promotion`.
- Example changelogs: `001-initial-create-tables.yaml`, `003-create-person-20250529.yaml`, `006-create-promotion-20250529.yaml`.

## Running Locally

### Prerequisites

- Docker and Docker Compose installed.

### Start All Services

```sh
docker-compose up --build
```

This will start:
- `insurance-service` (on port 8082)
- `vehicle-service` (on port 8081)
- Dedicated PostgreSQL databases for each service

### API Documentation

Once running, access the OpenAPI UI at:
```
http://localhost:8082/swagger-ui.html
```

### Feature Toggles

Togglz console is available at:
```
http://localhost:8082/togglz-console
```

## Testing

- Unit and integration tests are provided (see `InsuranceServiceTest.java`).
- Run tests with:
  ```sh
  ./gradlew test
  ```

## Development

- Main source: `src/main/java/com/threadpilot/insurance`
- Configuration: `src/main/resources/config`
- Database migrations: `src/main/resources/db/changelog`

## License

MIT (or your chosen license)
