# Nerdysoft Task App

This is a Spring Boot-based backend application designed for task management. It leverages PostgreSQL for persistence and Redis for caching. The application follows modern best practices with Docker-based containerization and is ready for deployment and testing.

Below you will find instructions on how to build and run the project, as well as a description of its structure and exposed endpoints.

---

## Table of Contents

1. [Project Description](#project-description)
2. [Build & Run with Docker Compose](#build--run-with-docker-compose)
3. [Running Tests](#running-tests)
4. [File Tree Overview](#file-tree-overview)
5. [API Endpoints](#api-endpoints)
6. [Key Features & Technologies](#key-features--technologies)

---

## Project Description

The **Nerdysoft Task** project allows you to:
- Manage a collection of books and members via a RESTful API.
- Store and retrieve data using a PostgreSQL database with Spring Data JPA and JDBC.
- Cache frequently accessed data using Redis via Spring Data Redis and Spring Cache.
- Handle object mapping cleanly using MapStruct.
- Manage the database schema using Flyway migrations.
- Load configuration variables securely with spring-dotenv support.
- Provide comprehensive API documentation via OpenAPI/Swagger.

The application includes an extensive test suite powered by Spring Boot Test, Testcontainers (for PostgreSQL and Redis), and Rest Assured for fluent API testing.

---

## Build & Run with Docker Compose

### Prerequisites

- **Docker** installed and running
- **Docker Compose** installed
- **Git** installed

### Running the Application

1. **Clone the repository** (or download the project):
   ```bash
   git clone https://github.com/w4t3rcs/nerdysoft-task.git
   cd nerdysoft-task
   ```

2. **Start the containers**:
   ```bash
   docker-compose up -d
   ```
   This command will:
    - Launch a PostgreSQL container named `nerdysoft-postgres`.
    - Launch a Redis container named `nerdysoft-redis`.
    - Build and start the `nerdysoft-task-app` container which runs the Spring Boot application.

3. **Verify if the application works**:
    - Check container logs if needed:
   ```bash
   docker-compose logs -f app
   ```

4. If you want to stop the application:
   ```bash
   docker-compose down
   ```

---

## Running Tests

1. **Build the project** with tests (you don't need to follow the previous instructions to test the app, but Docker must be running):
   ```bash
   ./mvnw clean test
   ```
   This will run:
    - Unit and integrated tests (backed by Testcontainers for PostgreSQL & Redis)
    - Rest Assured tests for API endpoints

> *Note:* You do **not** need to have local Postgres or Redis running. Testcontainers will automatically spin up ephemeral containers for tests.

---

## File Tree Overview

Below is a simplified overview of the main project structure:

```
nerdysoft-task
├── src
│   ├── main
│   │   ├── java
│   │   │   └── io
│   │   │       └── w4t3rcs
│   │   │           └── task
│   │   │               ├── config
│   │   │               │   ├── CommonConfig.java
│   │   │               │   ├── MapStructConfig.java
│   │   │               │   └── OpenApiConfig.java
│   │   │               ├── controller
│   │   │               │   ├── BookController.java
│   │   │               │   └── MemberController.java
│   │   │               ├── dto
│   │   │               │   ├── BookNamesAmountsResponse.java
│   │   │               │   ├── BookRequest.java
│   │   │               │   ├── BookResponse.java
│   │   │               │   ├── MemberRequest.java
│   │   │               │   └── MemberResponse.java
│   │   │               ├── entity
│   │   │               │   ├── Book.java
│   │   │               │   └── Member.java
│   │   │               ├── exception
│   │   │               │   ├── NotFoundException.java
│   │   │               │   └── RestExceptionHandler.java
│   │   │               ├── mapper
│   │   │               │   ├── BookMapper.java
│   │   │               │   └── MemberMapper.java
│   │   │               ├── repository
│   │   │               │   ├── BookRepository.java
│   │   │               │   └── MemberRepository.java
│   │   │               ├── service
│   │   │               │   ├── BookService.java
│   │   │               │   ├── MemberService.java
│   │   │               │   └── impl
│   │   │               │       ├── BookServiceImpl.java
│   │   │               │       └── BookServiceImpl.java
│   │   │               ├── service
│   │   │               │   ├── FullName.java
│   │   │               │   └── FullNameValidator.java
│   │   │               └── Application.java
│   │   └── resources
│   │       ├── db
│   │       │   └── migration
│   │       │       └── V1__CREATE_TABLES.sql
│   │       └── application.yaml
│   └── test
│       ├── java
│       │   └── io
│       │       └── w4t3rcs
│       │           └── task
│       │               ├── BookControllerTests.java
│       │               ├── ContainerConfiguration.java
│       │               └── MemberControllerTests.java
│       └── resources
│           └── db
│               └── migration
│                   └── V2__CREATE_TEST_DATA.sql
│
├── .env
├── docker-compose.yaml
├── Dockerfile
├── pom.xml
└── README.md                // You are here :)
```

---

## API Endpoints

Below is a high-level overview of some key endpoints.

| HTTP Method | Endpoint                               | Description                                                        |
|-------------|----------------------------------------|--------------------------------------------------------------------|
| **POST**    | `/api/v1/books`                        | Creating a book and saving it to the database                      |
| **GET**     | `/api/v1/books`                        | Retrieving books from the database                                 |
| **GET**     | `/api/v1/books?memberName={name}`      | Retrieving borrowed books from the database by the member name     |
| **GET**     | `/api/v1/books/borrowed-names`         | Retrieving borrowed book names from the database                   |
| **GET**     | `/api/v1/books/borrowed-names-amounts` | Retrieving borrowed book names and their amounts from the database |
| **GET**     | `/api/v1/books/{id}`                   | Retrieving a single book by its ID                                 |
| **PUT**     | `/api/v1/books/{id}`                   | Updating a book and saving it to the database                      |
| **DELETE**  | `/api/v1/books/{id}`                   | Deleting a book from the database                                  |
| **POST**    | `/api/v1/members`                      | Creating a member and saving them to the database                  |
| **GET**     | `/api/v1/members`                      | Retrieving members from the database                               |
| **GET**     | `/api/v1/members/{id}`                 | Retrieving a single member by their ID                             |
| **PUT**     | `/api/v1/members/{id}`                 | Updating a member and saving them to the database                  |
| **DELETE**  | `/api/v1/members/{id}`                 | Deleting a member from the database                                |

> *Note:* If you need more info about the endpoints, check out the OpenAPI documentation at [http://localhost:8080/v1/docs](http://localhost:8080/v1/docs).

---

## Key Features & Technologies

- **Spring Boot** – Rapid application development and dependency management.
- **Spring Data JPA** – Simplified database interactions for Postgres.
- **Spring Cache** – Easy caching abstraction to boost performance.
- **Spring Data Redis** – Caching via Redis.
- **Spring Validation** – Declarative request validation with annotation support.
- **Flyway** – Database migrations.
- **MapStruct** – Compile-time Java bean mapping.
- **Swagger** – Automatically generated and interactive API documentation.
- **Docker Compose** – Orchestrates multi-container deployments (PostgreSQL, Redis, and the app).
- **Testcontainers** – Provides ephemeral containers for integration testing.
- **Rest Assured** – Simplifies HTTP API testing.