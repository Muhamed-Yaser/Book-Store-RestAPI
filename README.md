# 📚 Book Store REST API (Spring Boot 3)

A robust, production-grade RESTful API built with **Spring Boot 3**, designed following **Clean Architecture** principles, strict **DTO patterns**, and industry-standard validation.

---

## 🚀 Key Features

* **Clean Architecture & Separation of Concerns:** Decoupled Entity and DTO layers to protect domain models and optimize network payloads.
* **Robust Validation:** Jakarta Validation (`@Valid`, `@NotNull`, `@Size`, `@Min`) for secure request input sanitization.
* **Global Exception Handling:** Centralized error management returning clean, standardized responses for error codes (e.g., `400 Bad Request`, `404 Not Found`).
* **Advanced Pagination & Sorting:** Full support for `Pageable` in CRUD operations, allowing customizable page size, page numbering, and dynamic sorting.
* **Relational Database Mapping:** Comprehensive One-to-One, Many-to-One, and Many-to-Many entity relationships using JPA / Hibernate.
* **Interactive API Documentation:** Fully integrated **Swagger/OpenAPI 3** UI for seamless API testing and exploration.

---

## 🛠️ Tech Stack

* **Java 17+**
* **Spring Boot 3.x**
* **Spring Data JPA & Hibernate**
* **Jakarta Validation**
* **Lombok**
* **MySQL / PostgreSQL**
* **SpringDoc OpenAPI (Swagger UI)**
* **Maven**

---

## 📂 Project Structure

```text
src/main/java/com/
│
├── controller/        # REST Controllers (Book, Author, Category, BookDetails)
├── dto/               # Request and Response DTOs
├── exception/         # Custom Exceptions & Global Exception Handler
├── model/             # JPA Entities
└── repository/        # Spring Data JPA Repositories
