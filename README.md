# Employee Management System

A Spring Boot application to create, view, update and delete employees using MySQL, Spring Data JPA, Lombok, validation and Thymeleaf.

## Requirements

- JDK 25 (configured in pom.xml)
- MySQL running on port 3306

## How to run

1. Start MySQL and create the database:

   ```sql
   CREATE DATABASE IF NOT EXISTS employee_db;
   ```

2. Copy `secrets.properties.example` to `secrets.properties` in the project root. If the file already exists, keep it. Set your MySQL username and password in this file.

3. In `src/main/resources/application.properties`, remove any existing `spring.datasource.username` and `spring.datasource.password` lines and add:

   ```properties
   spring.config.import=optional:file:./secrets.properties
   ```

4. Open PowerShell in the project root and run:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

5. Open http://localhost:8081/employees. Stop the application with Ctrl+C.

## Verify CRUD

Create, view, edit and delete employees through the application. In phpMyAdmin, open `employee_db` and browse the `employees` table to check the changes.

`secrets.properties` is ignored by Git. Never commit real database passwords.
