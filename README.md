Book Store Application
📚 Overview

Book Store is a full-featured Spring Boot application that allows users to manage and browse books, handle categories, and perform user authentication and authorization using JWT.
It follows a layered architecture and demonstrates clean REST API design with Spring Security, JPA, and Liquibase integration.

🛠 Technologies Used
Java 17+
Spring Boot
Spring Data JPA
Hibernate
Spring Security (JWT authentication)
Liquibase (database migration management)
MapStruct (DTO mapping)
Lombok
MySQL
Docker
Mockito
Swagger
Maven

⚙️ Environment Configuration

This project uses environment variables to manage sensitive data and configuration.
You can either define them manually or use the provided .env file.

Example .env file:

MYSQLDB_USER=root
MYSQLDB_ROOT_PASSWORD=password
MYSQLDB_DATABASE=bookstore
MYSQLDB_LOCAL_PORT=3307
MYSQLDB_DOCKER_PORT=3306

SPRING_LOCAL_PORT=8088
SPRING_DOCKER_PORT=8080
DEBUG_PORT=5005

JWT_SECRET=supersecretkeysupersecretkeysupersecretkey
JWT_EXPIRATION=3600000


🧩 Project Structure
src/main/java/mate/academy/bookstore/
├── config/         -> Application configuration
├── controller/     -> REST controllers (Book, Category, User, Authentication)
├── dto/            -> Request and response DTOs
├── entity/         -> JPA entities mapped to database tables
├── exception/      -> Global exception handling
├── mapper/         -> MapStruct mappers
├── repository/     -> Spring Data JPA repositories
├── security/       -> JWT authentication and filters
├── service/        -> Business logic layer
└── liquibase/      -> Database changelogs

🚀 Running the Application
1. Clone the repository
   git clone https://github.com/KLICKICE/spring-boot-book-store-jpa-exceptions.git
   cd book-store

2. Build the project
   mvn clean install

3. Run with Docker (recommended)
   docker-compose up --build


This will start:

MySQL database

Spring Boot backend on port 8080

4. Or run locally
   mvn spring-boot:run


The app will be available at:

http://localhost:8080/api

📘 API Documentation

Swagger UI is available at:

http://localhost:8080/api/swagger-ui/index.html


You can test authentication, CRUD operations for books and categories, and user management here.

🧪 Testing

Run all unit and integration tests:

mvn test

🧰 Postman Collection

A Postman collection is included in /postman/BookStoreAPI.postman_collection.json.
You can import it to easily test all endpoints.

💡 Challenges & Learnings

During development, I learned how to:

-Integrate JWT authentication with Spring Security

-Configure Liquibase for automatic database migration

-Use Docker for local development

-Map entities and DTOs efficiently with MapStruct

-Implement clean REST architecture and proper exception handling


👤 Author

Developed by Oleksandr
📧 Email: ledmaskiight@gmail.com
💼 GitHub: https://github.com/KLICKICE