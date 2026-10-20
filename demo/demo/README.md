# Demo REST API

A RESTful API built with Spring Boot for managing users, roles, categories, and products. This project demonstrates layered architecture, database migrations, request validation, API documentation, integration testing, and CI/CD pipeline configuration.

## Tech Stack

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- MySQL
- Flyway Database Migration
- Jakarta Bean Validation
- Springdoc OpenAPI / Swagger UI
- Maven Wrapper
- Lombok
- GitHub Actions
- Jenkins Pipeline

Dependency versions are defined in `pom.xml`.

## Features

- CRUD operations for Categories and Products
- Read endpoints for Users and Roles
- Request validation using Jakarta Bean Validation
- Centralized exception handling
- Product-to-Category relationship
- Database-managed `createdAt` and `updatedAt` timestamps
- Versioned database migrations using Flyway
- Interactive API documentation with Swagger UI
- Category API integration tests
- Automated build and test workflow configuration

## Prerequisites

- JDK 17
- MySQL Server 8.x
- PowerShell on Windows, or a compatible terminal
- Git (for repository operations)

Maven does not need to be installed globally because the project includes Maven Wrapper.

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd javaspringboot-company-standard-crud
````

Replace `<repository-url>` with the actual GitHub repository URL.

### 2. Create the database

Execute the following SQL in MySQL:

```
CREATE DATABASE IF NOT EXISTS database_testing;
```

### 3. Configure the database connection

Configure the datasource in `src/main/resources/application.yaml`.

For local development, the connection settings should match your MySQL installation:

- Database: `database_testing`
- Host: `localhost`
- Port: `3306`
- Username and password: your local MySQL credentials

For public repositories, use environment variables for database credentials instead of committing secrets.

The GitHub Actions workflow also supplies datasource environment variables. Ensure `application.yaml` supports `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, and `SPRING_DATASOURCE_PASSWORD` so the application can connect to the CI database.

### 4. Run the application

Windows PowerShell:

```
.\mvnw.cmd spring-boot:run
```

Linux/macOS:

```
./mvnw spring-boot:run
```

Flyway applies pending database migrations during application startup.

### 5. Build and test

Windows PowerShell:

```
.\mvnw.cmd clean verify
```

Linux/macOS:

```
./mvnw clean verify
```

To run tests without the full verification lifecycle:

```
.\mvnw.cmd test
```

## API Reference

Base URL: `http://localhost:8080`

### Users

| Method | Endpoint          | Description           |
| ------ | ----------------- | --------------------- |
| GET    | `/api/users`      | Retrieve all users    |
| GET    | `/api/users/{id}` | Retrieve a user by ID |

### Roles

| Method | Endpoint             | Description              |
| ------ | -------------------- | ------------------------ |
| GET    | `/api/roles`         | Retrieve all roles       |
| GET    | `/api/roles/{alias}` | Retrieve a role by alias |

### Categories

| Method | Endpoint               | Description               |
| ------ | ---------------------- | ------------------------- |
| GET    | `/api/categories`      | Retrieve all categories   |
| GET    | `/api/categories/{id}` | Retrieve a category by ID |
| POST   | `/api/categories`      | Create a category         |
| PUT    | `/api/categories/{id}` | Update a category         |
| DELETE | `/api/categories/{id}` | Delete a category         |

### Products

| Method | Endpoint             | Description              |
| ------ | -------------------- | ------------------------ |
| GET    | `/api/products`      | Retrieve all products    |
| GET    | `/api/products/{id}` | Retrieve a product by ID |
| POST   | `/api/products`      | Create a product         |
| PUT    | `/api/products/{id}` | Update a product         |
| DELETE | `/api/products/{id}` | Delete a product         |

## Example Requests

### Create a Category

Request

```
POST /api/categories
Content-Type: application/json
```

Body

```
{
  "name": "Electronics",
  "slug": "electronics",
  "description": "Electronic products"
}
```

### Create a Product

Request

```
POST /api/products
Content-Type: application/json
```

Body

```
{
  "name": "Wireless Mouse",
  "slug": "wireless-mouse",
  "description": "Wireless mouse",
  "price": 150000,
  "status": "published",
  "categoryId": 1
}
```

The `categoryId` must reference an existing category. Request bodies must conform to the application's DTO validation rules.

## Request Validation

### Category

| Field         | Validation                         |
| ------------- | ---------------------------------- |
| `name`        | Required, maximum 120 characters   |
| `slug`        | Required, maximum 140 characters   |
| `description` | Optional, maximum 5,000 characters |

### Product

| Field         | Validation                                    |
| ------------- | --------------------------------------------- |
| `name`        | Required, maximum 180 characters              |
| `slug`        | Required, maximum 200 characters              |
| `description` | Optional, maximum 10,000 characters           |
| `price`       | Required, minimum `0`                         |
| `status`      | Required: `draft`, `published`, or `sold`     |
| `categoryId`  | Required, must reference an existing category |

## HTTP Status Codes

| Status            | Description                                                                                             |
| ----------------- | ------------------------------------------------------------------------------------------------------- |
| `200 OK`          | Read or update operation succeeded                                                                      |
| `201 Created`     | Resource created successfully                                                                           |
| `204 No Content`  | Resource deleted successfully                                                                           |
| `400 Bad Request` | Invalid request or validation failure                                                                   |
| `404 Not Found`   | Requested resource or required category not found                                                       |
| `409 Conflict`    | Duplicate category name/slug, duplicate product slug, or category deletion blocked by existing products |

Actual responses depend on the endpoint and request conditions.

## API Documentation

Start the application before accessing the following endpoints.

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs?)

## Database Migrations

Flyway migration scripts are located in:

`src/main/resources/db/migration/`

The migrations manage the database schema for users, roles, categories, and products.

Create a new migration for subsequent schema changes rather than modifying migrations that have already been applied to a shared database.

## Project Structure

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── config/          # Exception handling and application configuration
│   │   ├── controller/      # REST API controllers
│   │   ├── dto/             # Request DTOs and validation rules
│   │   ├── model/           # JPA entities
│   │   ├── repositories/    # Data access layer
│   │   └── services/        # Service interfaces and implementations
│   └── resources/
│       ├── application.yaml
│       └── db/migration/    # Flyway migration scripts
└── test/
    └── java/com/example/demo/
        ├── DemoApplicationTests.java
        └── CategoryApiIntegrationTest.java
```

## CI/CD Pipeline

The repository includes pipeline definitions for GitHub Actions and Jenkins.

### GitHub Actions

Workflow file: `.github/workflows/ci.yml`

The workflow runs on pushes and pull requests targeting the `main` or `master` branches.

Pipeline stages:

1. Check out the repository.
2. Set up Java 17.
3. Start a MySQL 8.4 service for integration tests.
4. Run `./mvnw -B clean verify`.

The workflow uses the Maven Wrapper to build the application and execute the test suite.

### Jenkins

Pipeline file: `Jenkinsfile`

Stages:

1. Checkout — retrieve the repository source.
2. Build & Test — run `clean verify` using Maven Wrapper.
3. Archive Artifacts — archive generated JAR files when available.

The Jenkins pipeline supports Windows and Linux agents. Configure a JDK installation named `JDK17` in Jenkins before running the pipeline.

### Run the CI build locally

Windows PowerShell:

```
.\mvnw.cmd -B clean verify
```

Linux/macOS:

```
./mvnw -B clean verify
```

## Current Scope

- Users and Roles: read endpoints
- Categories and Products: CRUD endpoints
- Authentication and authorization: not implemented
- Production deployment configuration: not included

## License

No license specified.

