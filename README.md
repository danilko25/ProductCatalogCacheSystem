# Product Management System

This is a Spring Boot application for managing products and their categories. It provides RESTful APIs to perform CRUD operations on products and categories, with caching, security, and database migration features.

## Features

- **RESTful APIs**: Manage products and categories via endpoints for creating, reading, updating, and deleting records.
- **Database**: Uses H2 database with Flyway for schema migrations and initial data population.
- **Caching**: Redis (running on port 6379 in a Docker container) is used to cache product and category queries.
- **Security**: Basic Authentication for POST, PUT, and DELETE endpoints with credentials (`admin:root`) configured in `application.properties`.
- **API Documentation**: Swagger/OpenAPI integration for interactive API documentation.
- **Validation**: Input validation for product and category data using Spring Boot Validation.
- **Pagination**: Supports paginated response for retrieving lists of products.

## Technologies Used

- **Spring Boot**: Framework for building the application.
- **Spring Data JPA**: For database operations with H2.
- **Redis**: For caching query results.
- **Spring Security**: For Basic Authentication on protected endpoints.
- **Flyway**: For database migrations and initial data setup.
- **Swagger/OpenAPI**: For API documentation.
- **Lombok**: To reduce boilerplate code.
- **Jackson**: For handling Java 8 date/time types.

### Technology Versions
| Dependency                            | Version                     |
|---------------------------------------|-----------------------------|
| Spring Boot Starter Parent            | 3.5.3                       |
| Java                                  | 17                          |
| Flyway Core                           | 11.10.1                     |
| Springdoc OpenAPI (Swagger UI)        | 2.8.9                       |
| Redis                                 | 8.2-rc1-bookworm or higher  |
| H2 Database                           | (Inherited from Spring Boot) |
| Lombok                                | (Inherited from Spring Boot) |
| Jackson Datatype JSR310               | (Inherited from Spring Boot) |
| Spring Boot Starter Cache             | (Inherited from Spring Boot) |
| Spring Boot Starter Web               | (Inherited from Spring Boot) |
| Spring Boot Starter Data JPA          | (Inherited from Spring Boot) |
| Spring Boot Starter Validation        | (Inherited from Spring Boot) |
| Spring Boot Starter Data Redis        | (Inherited from Spring Boot) |
| Spring Boot Starter Security          | (Inherited from Spring Boot) |
| Spring Boot Starter Test              | (Inherited from Spring Boot) |

**Note**: Dependencies marked as "(Inherited from Spring Boot)" use versions managed by the Spring Boot Starter Parent (3.5.3). Check the `pom.xml` file for details.

## Prerequisites

- **Java**: Version 17
- **Maven**: Version 3.8.0 or higher, for dependency management and building the project
- **Docker**: Optional, to run Redis (if not running locally)
- **H2 Database**: Included as a runtime dependency
- **Redis**: Running on port 6379 (default)

## Installation

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd <repository-directory>

2. **Set up Redis** (if not already running):
   Run Redis in a Docker container:
   ```bash
   docker run -d --name my-redis -p 6379:6379 redis:8.2-rc1-bookworm

3. **Configure application properties:**
   Update src/main/resources/application.properties with the necessary configurations, including:
   ```properties
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   spring.redis.host=localhost
   spring.redis.port=6379
   spring.security.user.name=admin
   spring.security.user.password=root

4. **Build and run the application:**
   ```bash
   mvn clean install
   mvn spring-boot:run

6. **Access Swagger UI:**
   ```
   http://localhost:8080/swagger-ui.html

## API Endpoints

### Product Controller
The Product Controller provides endpoints for managing products in the system.

| Method | Endpoint                                    | Description                              | Authentication                     |
|--------|---------------------------------------------|------------------------------------------|------------------------------------|
| GET    | `/api/v1/products/{id}`                     | Retrieve a product by its ID              | None                               |
| PUT    | `/api/v1/products/{id}`                     | Update a product                         | Basic Auth (`admin:root`)          |
| DELETE | `/api/v1/products/{id}`                     | Delete a product                         | Basic Auth (`admin:root`)          |
| GET    | `/api/v1/products`                          | Retrieve all products (supports pagination) | None                             |
| POST   | `/api/v1/products`                          | Create a new product                     | Basic Auth (`admin:root`)          |
| GET    | `/api/v1/products/category/{category_id}`   | Retrieve products by category ID         | None                               |

### Category Controller
The Category Controller provides endpoints for managing categories in the system.

| Method | Endpoint                                    | Description                              | Authentication                     |
|--------|---------------------------------------------|------------------------------------------|------------------------------------|
| GET    | `/api/v1/categories/{id}`                   | Retrieve a category by its ID            | None                               |
| PUT    | `/api/v1/categories/{id}`                   | Update a category                        | Basic Auth (`admin:root`)          |
| DELETE | `/api/v1/categories/{id}`                   | Delete a category                        | Basic Auth (`admin:root`)          |
| GET    | `/api/v1/categories`                        | Retrieve all categories (supports pagination) | None                           |
| POST   | `/api/v1/categories`                        | Create a new category                    | Basic Auth (`admin:root`)          |

## Data Models

### Product
Represents a product entity in the system.

- **id**: `Long` (auto-generated)
- **name**: `String` (required, 2-100 characters)
- **description**: `String` (optional, 0-300 characters)
- **price**: `BigDecimal` (required, ≥ 0)
- **category**: `Category` (linked via `category_id`)
- **stock**: `Integer` (≥ 0)
- **creationDate**: `LocalDateTime`
- **lastUpdateDate**: `LocalDateTime`

### Category
Represents a category entity in the system.

- **id**: `Long` (auto-generated)
- **name**: `String` (required, 2-100 characters)

## DTOs

### ProductCreateEditDto
Used for creating or updating products.

- **productName**: `String` (2-100 characters)
- **productDescription**: `String` (0-300 characters)
- **price**: `Number` (≥ 0)
- **categoryId**: `Long`
- **stock**: `Integer` (≥ 0)



### CategoryCreateEditDto
Used for creating or updating categories.

- **categoryName**: `String` (2-100 characters)

### CategoryReadDto
Returned when retrieving category details.

- **id**: `Long`
- **categoryName**: `String`

## Database Migrations
Flyway is used to manage database schema migrations and populate initial data.

- **Location**: Migration scripts are stored in `src/main/resources/db/migration`.
- **Purpose**: Ensures consistent database schema setup and initial data population.

## Data Caching with Redis
Utilize Redis for caching query results, significantly enhancing our application's performance.

### How Caching Works

When lists or individual objects (like categories or products) are retrieved, they are cached for faster access in subsequent requests.

**Category Cache Management**
- Adding a New Category: When a new category is created, the cache for the list of all categories is completely cleared. This ensures that the next request for all categories will return an up-to-date list, including the new entry.
- Updating a Category: If a category is updated, the cache for the list of all categories is also invalidated. Additionally, the specific cache entry for that individual category is updated to reflect the changes.
- Deleting a Category: When a category is deleted, the cache for the list of all categories is fully cleared, and the specific cache entry for that category is removed as well.

**Product Cache Management**
- Adding a New Product: Upon adding a new product, the cache for the list of products belonging to its specific category is cleared. This ensures the product list for that category is current.
- Updating a Product: When a product is updated, the cache for the list of products in its category is invalidated. Concurrently, the specific cache entry for that individual product is updated.
- Deleting a Product: If a product is deleted, the cache for all product lists across all categories is entirely cleared, and the specific cache entry for that product is also removed.
  
## Security
Basic Authentication is implemented for protected endpoints.

- **Protected Endpoints**: `POST`, `PUT`, and `DELETE` operations for both products and categories.
- **Credentials**:
  - **Username**: `admin`
  - **Password**: `root`
- **Configuration**: Credentials are defined in `application.properties`.
