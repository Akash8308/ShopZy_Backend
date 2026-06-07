# ShopZy Backend - Complete Project Blueprint

**Last Updated**: June 2026  
**Version**: 0.0.1-SNAPSHOT  
**Status**: Active Development

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Architecture & Design Principles](#architecture--design-principles)
4. [Project Structure](#project-structure)
5. [Domain Model & Aggregates](#domain-model--aggregates)
6. [Development Setup](#development-setup)
7. [Building & Running](#building--running)
8. [API Documentation](#api-documentation)
9. [Database Architecture](#database-architecture)
10. [Configuration Management](#configuration-management)
11. [Security & Authentication](#security--authentication)
12. [Testing Strategy](#testing-strategy)
13. [Development Guidelines](#development-guidelines)
14. [Deployment Guide](#deployment-guide)
15. [Common Tasks & Workflows](#common-tasks--workflows)
16. [Troubleshooting](#troubleshooting)
17. [Future Enhancements & Roadmap](#future-enhancements--roadmap)

---

## Project Overview

### What is ShopZy?

ShopZy is a **Domain-Driven Design (DDD) based e-commerce backend platform** designed to manage products, users, shopping carts, and orders. The application follows modern microservices-ready architecture principles, allowing independent scaling and future extraction of bounded contexts into microservices.

### Key Objectives

- ✅ **Scalable Architecture**: Built with DDD bounded contexts to enable horizontal scaling
- ✅ **Clean Code**: Following SOLID principles and DDD patterns
- ✅ **Production-Ready**: Implements security, error handling, and data persistence
- ✅ **Maintainable**: Clear separation of concerns across domains
- ✅ **Testable**: Architecture supports unit and integration testing
- ✅ **Future-Proof**: Ready for microservices extraction and event-driven architecture

### Project Goals

1. Provide a robust REST API for e-commerce operations
2. Manage user accounts, products, shopping carts, and orders
3. Implement secure authentication using JWT and OAuth2
4. Maintain data integrity across multiple domains
5. Enable independent scaling of business domains

---

## Technology Stack

### Core Framework

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Spring Boot** | 3.5.14 | Application framework and dependency injection |
| **Java** | 21 | Programming language (LTS version) |
| **Spring Web** | 3.5.14 | REST API and HTTP request handling |
| **Spring Data JPA** | 3.5.14 | ORM and database abstraction |
| **Spring Security** | 3.5.14 | Authentication and authorization |

### Database

| Technology | Version | Purpose |
|-----------|---------|---------|
| **PostgreSQL** | 12+ | Primary relational database |
| **Hibernate** | 6.x (via Spring Data JPA) | ORM framework |
| **JDBC Driver** | PostgreSQL JDBC 42.x | Database connectivity |

### Development & Utilities

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Lombok** | Latest | Code generation (getters, setters, constructors) |
| **Maven** | 3.9.x | Build and dependency management |
| **Spring Security OAuth2 Client** | 3.5.14 | OAuth2 authentication support |

### Testing

| Technology | Version | Purpose |
|-----------|---------|---------|
| **JUnit 5** | 5.x (via Spring Boot) | Unit testing framework |
| **Spring Test** | 3.5.14 | Spring integration testing |
| **Mockito** | Latest (via Spring Boot) | Mocking framework |

---

## Architecture & Design Principles

### Design Pattern: Domain-Driven Design (DDD)

The entire project is organized around **Domain-Driven Design** principles, which emphasize modeling business logic around distinct domains and their interactions.

#### Key DDD Concepts Implemented

| Concept | Implementation | Benefit |
|---------|----------------|---------|
| **Bounded Context** | Separate `domains/{domain}` package for each business area | Clear boundaries and responsibility ownership |
| **Aggregate Root** | User, Product, Category, Cart, Order | Single entry point for domain operations |
| **Entities** | Address, CartItem, OrderItem | Part of aggregate hierarchy |
| **Value Objects** | OrderStatus, Enums | Immutable domain concepts |
| **Repository** | Domain-specific repository interfaces | Data persistence abstraction |
| **Service** | Domain service layer | Business logic and use cases |
| **Controller** | REST endpoints per domain | API exposure |

### Layered Architecture

```
┌─────────────────────────────────────────┐
│         API Layer (Controllers)         │
│  - REST endpoints per domain            │
│  - Request/Response handling            │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│      Service Layer (Business Logic)     │
│  - Domain operations                    │
│  - Inter-domain communication           │
│  - Transaction management               │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│      Repository Layer (Data Access)     │
│  - Database queries                     │
│  - Entity persistence                   │
│  - ORM integration                      │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│       Domain Model Layer (Entities)     │
│  - Domain objects                       │
│  - Business rules                       │
│  - Validation logic                     │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│    Infrastructure (Database/Security)   │
│  - PostgreSQL database                  │
│  - JWT/OAuth2 authentication            │
└─────────────────────────────────────────┘
```

### SOLID Principles Applied

- **Single Responsibility**: Each class has one reason to change
- **Open/Closed**: Open for extension, closed for modification
- **Liskov Substitution**: Service implementations follow contracts
- **Interface Segregation**: Domain-specific repositories
- **Dependency Inversion**: Depend on abstractions (repositories, services)

---

## Project Structure

### Directory Hierarchy

```
ShopZy_Backend/
│
├── src/
│   ├── main/
│   │   ├── java/com/shopzy/
│   │   │   ├── ShopzyApplication.java          [Spring Boot main entry point]
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   └── BaseController.java         [Common controller utilities]
│   │   │   │
│   │   │   ├── domains/                        [Bounded Contexts]
│   │   │   │   ├── user/                       [User Domain]
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   ├── UserController.java
│   │   │   │   │   │   └── AddressController.java
│   │   │   │   │   ├── service/
│   │   │   │   │   │   ├── UserService.java    [Interface]
│   │   │   │   │   │   ├── AddressService.java [Interface]
│   │   │   │   │   │   └── impl/
│   │   │   │   │   │       ├── UserServiceImpl.java
│   │   │   │   │   │       └── AddressServiceImpl.java
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   │   └── AddressRepository.java
│   │   │   │   │   └── model/
│   │   │   │   │       ├── User.java           [Aggregate Root]
│   │   │   │   │       └── Address.java        [Entity]
│   │   │   │   │
│   │   │   │   ├── catalog/                    [Catalog Domain]
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   ├── ProductController.java
│   │   │   │   │   │   └── CategoryController.java
│   │   │   │   │   ├── service/
│   │   │   │   │   │   ├── ProductService.java [Interface]
│   │   │   │   │   │   ├── CategoryService.java [Interface]
│   │   │   │   │   │   └── impl/
│   │   │   │   │   │       ├── ProductServiceImpl.java
│   │   │   │   │   │       └── CategoryServiceImpl.java
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   ├── ProductRepository.java
│   │   │   │   │   │   └── CategoryRepository.java
│   │   │   │   │   └── model/
│   │   │   │   │       ├── Product.java        [Aggregate Root]
│   │   │   │   │       └── Category.java       [Aggregate Root]
│   │   │   │   │
│   │   │   │   ├── cart/                       [Cart Domain]
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   ├── CartController.java
│   │   │   │   │   │   └── CartItemController.java
│   │   │   │   │   ├── service/
│   │   │   │   │   │   ├── CartService.java    [Interface]
│   │   │   │   │   │   ├── CartItemService.java [Interface]
│   │   │   │   │   │   └── impl/
│   │   │   │   │   │       ├── CartServiceImpl.java
│   │   │   │   │   │       └── CartItemServiceImpl.java
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   ├── CartRepository.java
│   │   │   │   │   │   └── CartItemRepository.java
│   │   │   │   │   └── model/
│   │   │   │   │       ├── Cart.java           [Aggregate Root]
│   │   │   │   │       └── CartItem.java       [Entity]
│   │   │   │   │
│   │   │   │   ├── order/                      [Order Domain]
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   ├── OrderController.java
│   │   │   │   │   │   └── OrderItemController.java
│   │   │   │   │   ├── service/
│   │   │   │   │   │   ├── OrderService.java   [Interface]
│   │   │   │   │   │   ├── OrderItemService.java [Interface]
│   │   │   │   │   │   └── impl/
│   │   │   │   │   │       ├── OrderServiceImpl.java
│   │   │   │   │   │       └── OrderItemServiceImpl.java
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   ├── OrderRepository.java
│   │   │   │   │   │   └── OrderItemRepository.java
│   │   │   │   │   └── model/
│   │   │   │   │       ├── Order.java          [Aggregate Root]
│   │   │   │   │       └── OrderItem.java      [Entity]
│   │   │   │   │
│   │   │   │   └── shared/                     [Cross-Cutting Concerns]
│   │   │   │       ├── exception/              [Global exceptions]
│   │   │   │       │   └── [Exception classes]
│   │   │   │       ├── security/
│   │   │   │       │   ├── JwtUtil.java        [JWT utilities]
│   │   │   │       │   └── SecurityConfig.java [Spring Security config]
│   │   │   │       ├── util/                   [Common utilities]
│   │   │   │       └── valueobject/
│   │   │   │           └── OrderStatus.java    [Value Object Enum]
│   │   │   │
│   │   │   └── [Shared resources]
│   │   │
│   │   └── resources/
│   │       ├── application.properties          [Main configuration]
│   │       ├── application-secrets.properties  [Secrets (environment variables)]
│   │       ├── static/                         [Static assets (CSS, JS, images)]
│   │       └── templates/                      [HTML templates if using Thymeleaf]
│   │
│   └── test/
│       └── java/com/shopzy/
│           ├── ShopzyApplicationTests.java
│           └── [Domain-specific test packages]
│
├── target/                                     [Build output (generated)]
│   ├── classes/
│   ├── generated-sources/
│   └── generated-test-sources/
│
├── pom.xml                                     [Maven build configuration]
├── mvnw                                        [Maven Wrapper (Linux/Mac)]
├── mvnw.cmd                                    [Maven Wrapper (Windows)]
├── .mvn/                                       [Maven Wrapper configuration]
├── Dockerfile                                  [Container configuration]
├── README.md                                   [Quick start guide]
├── HELP.md                                     [Help documentation]
├── DDD_STRUCTURE.md                            [DDD structure explanation]
└── PROJECT_BLUEPRINT.md                        [This file - Complete blueprint]
```

### File Naming Conventions

| Component | Naming Convention | Example |
|-----------|-------------------|---------|
| **Entity Classes** | `<EntityName>.java` | `User.java`, `Product.java` |
| **Service Interface** | `<DomainName>Service.java` | `UserService.java` |
| **Service Implementation** | `<DomainName>ServiceImpl.java` | `UserServiceImpl.java` |
| **Repository Interface** | `<EntityName>Repository.java` | `UserRepository.java` |
| **Controller Class** | `<DomainName>Controller.java` | `UserController.java` |
| **Configuration Classes** | `<Name>Config.java` | `SecurityConfig.java` |
| **Utility Classes** | `<Name>Util.java` | `JwtUtil.java` |
| **Exception Classes** | `<Name>Exception.java` | `UserNotFoundException.java` |
| **Test Classes** | `<ClassName>Tests.java` | `UserServiceTests.java` |

---

## Domain Model & Aggregates

### 1. User Domain (User Bounded Context)

**Aggregate Root**: `User`

#### Entities
- **User** - Represents a user account with authentication credentials
  - Fields: id, email, password, firstName, lastName, createdAt, updatedAt
  - Relationships: One-to-Many with Address, One-to-One with Cart, One-to-Many with Order

- **Address** - Represents a user's address (entity within User aggregate)
  - Fields: id, street, city, state, zipCode, country, userId
  - Relationships: Many-to-One with User

#### Responsibilities
- User registration and account management
- User authentication (JWT, OAuth2)
- Address management (add, update, delete)
- User profile management

#### API Endpoints
```
POST   /users                    - Create new user
GET    /users/{id}              - Get user by ID
GET    /users                   - List all users
PUT    /users/{id}              - Update user
DELETE /users/{id}              - Delete user

POST   /addresses               - Add address
GET    /addresses/{id}          - Get address
PUT    /addresses/{id}          - Update address
DELETE /addresses/{id}          - Delete address
```

---

### 2. Catalog Domain (Catalog Bounded Context)

**Aggregate Roots**: `Product`, `Category`

#### Entities
- **Product** - Represents a product in the catalog
  - Fields: id, name, description, price, stock, categoryId, createdAt, updatedAt
  - Relationships: Many-to-One with Category

- **Category** - Represents a product category
  - Fields: id, name, description, createdAt, updatedAt
  - Relationships: One-to-Many with Product

#### Responsibilities
- Product catalog management (CRUD)
- Category management
- Inventory tracking
- Product search and filtering

#### API Endpoints
```
POST   /products                - Create product
GET    /products/{id}           - Get product by ID
GET    /products                - List products (with filtering)
PUT    /products/{id}           - Update product
DELETE /products/{id}           - Delete product

POST   /categories              - Create category
GET    /categories/{id}         - Get category by ID
GET    /categories              - List all categories
PUT    /categories/{id}         - Update category
DELETE /categories/{id}         - Delete category
```

---

### 3. Cart Domain (Cart Bounded Context)

**Aggregate Root**: `Cart`

#### Entities
- **Cart** - Represents a shopping cart
  - Fields: id, userId, totalPrice, createdAt, updatedAt
  - Relationships: One-to-One with User, One-to-Many with CartItem

- **CartItem** - Represents an item in the cart (entity within Cart aggregate)
  - Fields: id, cartId, productId, quantity, unitPrice, createdAt, updatedAt
  - Relationships: Many-to-One with Cart, Many-to-One with Product

#### Responsibilities
- Shopping cart management
- Cart item operations (add, update, remove)
- Price calculation and total management
- Cart persistence and retrieval

#### API Endpoints
```
POST   /api/cart-items          - Add item to cart
GET    /api/cart-items          - Get cart items
PUT    /api/cart-items/{id}     - Update cart item
DELETE /api/cart-items/{id}     - Remove item from cart

GET    /carts/{id}              - Get cart by user ID
DELETE /carts/{id}              - Clear cart
```

#### Inter-Domain Dependencies
- **Depends on User Domain**: Cart is owned by a User
- **Depends on Catalog Domain**: CartItem references Product

---

### 4. Order Domain (Order Bounded Context)

**Aggregate Root**: `Order`

#### Entities
- **Order** - Represents a customer order
  - Fields: id, userId, totalAmount, status, createdAt, updatedAt
  - Relationships: Many-to-One with User, One-to-Many with OrderItem

- **OrderItem** - Represents an item in an order (entity within Order aggregate)
  - Fields: id, orderId, productId, quantity, unitPrice, createdAt, updatedAt
  - Relationships: Many-to-One with Order, Many-to-One with Product

#### Responsibilities
- Order creation from cart items
- Order management (CRUD)
- Order status tracking
- Order history and retrieval

#### API Endpoints
```
POST   /orders                  - Create order (from cart)
GET    /orders/{id}             - Get order by ID
GET    /orders                  - List user's orders
PUT    /orders/{id}             - Update order
DELETE /orders/{id}             - Cancel order

POST   /order-items             - Add item to order
GET    /order-items/{id}        - Get order item
PUT    /order-items/{id}        - Update order item
DELETE /order-items/{id}        - Remove order item
```

#### Inter-Domain Dependencies
- **Depends on User Domain**: Order is owned by a User
- **Depends on Catalog Domain**: OrderItem references Product

---

### 5. Shared Resources (Cross-Cutting Concerns)

#### Exception Handling
- Custom exception classes for domain-specific errors
- Global exception handler (if implemented)
- Standard HTTP error responses

#### Security
- **JwtUtil.java** - JWT token generation and validation
- **SecurityConfig.java** - Spring Security configuration
- OAuth2 client configuration
- Authentication filters

#### Value Objects
- **OrderStatus** - Enumeration for order statuses
  - Values: PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
  - Immutable and shared across domains

#### Common Utilities
- Date/time utilities
- String formatting utilities
- Validation helpers

---

## Development Setup

### Prerequisites

#### System Requirements
- **Operating System**: Windows, macOS, or Linux
- **Java Development Kit (JDK)**: Java 21 (LTS)
- **Apache Maven**: 3.9.x or higher
- **PostgreSQL**: 12 or higher
- **Git**: Latest version

#### IDE & Tools
- **IDE**: IntelliJ IDEA, VS Code, or Eclipse
- **Build Tool**: Maven (bundled via Maven Wrapper)
- **Database Client**: DBeaver, pgAdmin, or psql CLI

### Step 1: Clone Repository

```bash
git clone https://github.com/your-org/ShopZy_Backend.git
cd ShopZy_Backend
```

### Step 2: Install Java 21

#### Windows
```bash
# Using chocolatey
choco install openjdk21

# Or download from https://jdk.java.net/21
```

#### macOS
```bash
brew install openjdk@21
```

#### Linux
```bash
sudo apt-get install openjdk-21-jdk
```

Verify installation:
```bash
java -version
```

### Step 3: Install PostgreSQL

#### Windows
1. Download from https://www.postgresql.org/download/windows/
2. Run installer and follow setup wizard
3. Remember the superuser password

#### macOS
```bash
brew install postgresql@15
brew services start postgresql@15
```

#### Linux
```bash
sudo apt-get install postgresql postgresql-contrib
sudo systemctl start postgresql
```

### Step 4: Create Database

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE shopzy;
CREATE USER shopzy_user WITH PASSWORD 'your_secure_password';
ALTER ROLE shopzy_user SET client_encoding TO 'utf8';
ALTER ROLE shopzy_user SET default_transaction_isolation TO 'read committed';
ALTER ROLE shopzy_user SET default_transaction_deferrable TO on;
ALTER ROLE shopzy_user SET timezone TO 'UTC';
GRANT ALL PRIVILEGES ON DATABASE shopzy TO shopzy_user;
```

### Step 5: Configure Environment Variables

Create `.env` file or set environment variables:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/shopzy
SPRING_DATASOURCE_USERNAME=shopzy_user
SPRING_DATASOURCE_PASSWORD=your_secure_password
JWT_SECRET=your_jwt_secret_key_here
```

For Windows (PowerShell):
```powershell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/shopzy"
$env:SPRING_DATASOURCE_USERNAME="shopzy_user"
$env:SPRING_DATASOURCE_PASSWORD="your_secure_password"
```

### Step 6: Install Dependencies

```bash
# Using Maven Wrapper (recommended)
./mvnw clean install

# Or using installed Maven
mvn clean install
```

### Step 7: Verify Setup

```bash
# Run tests
./mvnw test

# Check Spring Boot version
./mvnw --version
```

---

## Building & Running

### Build Commands

#### Clean Build
```bash
./mvnw clean package
```

#### Build Without Tests
```bash
./mvnw clean package -DskipTests
```

#### Build Specific Module (Multi-module projects)
```bash
./mvnw clean package -pl module-name
```

### Running Application

#### Using Maven
```bash
./mvnw spring-boot:run
```

#### Using compiled JAR
```bash
# First, package the application
./mvnw clean package

# Then run the JAR
java -jar target/shopzy-0.0.1-SNAPSHOT.jar
```

#### Using IDE
- IntelliJ: Run → Run 'ShopzyApplication'
- VS Code: Click "Run" button or use Ctrl+F5

### Application Startup Verification

When application starts successfully, you should see:
```
Started ShopzyApplication in X seconds (JVM running for Y seconds)
```

API will be available at: `http://localhost:8080`

### Default Application Properties

| Property | Value | Purpose |
|----------|-------|---------|
| `server.port` | 8080 | HTTP server port |
| `spring.jpa.hibernate.ddl-auto` | update | Auto-create/update database tables |
| `spring.jpa.database-platform` | PostgreSQLDialect | Database dialect |
| `spring.jpa.show-sql` | false | Log SQL queries |

---

## API Documentation

### Authentication

All endpoints (except login/registration) require a valid JWT token in the Authorization header.

```
Authorization: Bearer {jwt_token}
```

### Standard Response Format

#### Success Response (2xx)
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    // Response data
  }
}
```

#### Error Response (4xx, 5xx)
```json
{
  "status": 400,
  "message": "Error description",
  "errors": [
    {
      "field": "fieldName",
      "message": "Validation error"
    }
  ]
}
```

### Base URL

```
http://localhost:8080/api/v1
```

### Domain Endpoints

#### User Domain

```
POST   /users/register              - User registration
POST   /users/login                 - User login (returns JWT)
GET    /users/{id}                  - Get user profile
PUT    /users/{id}                  - Update profile
GET    /users/{id}/addresses        - Get user addresses
POST   /users/{id}/addresses        - Add address
```

#### Catalog Domain

```
GET    /products                    - List all products
GET    /products/{id}               - Get product details
POST   /products                    - Create product (admin)
PUT    /products/{id}               - Update product (admin)
DELETE /products/{id}               - Delete product (admin)
GET    /products/category/{catId}   - Get products by category

GET    /categories                  - List all categories
GET    /categories/{id}             - Get category details
POST   /categories                  - Create category (admin)
```

#### Cart Domain

```
GET    /cart                        - Get current user's cart
POST   /cart/items                  - Add item to cart
PUT    /cart/items/{itemId}         - Update cart item quantity
DELETE /cart/items/{itemId}         - Remove item from cart
DELETE /cart                        - Clear entire cart
```

#### Order Domain

```
POST   /orders                      - Create order (from cart)
GET    /orders                      - Get user's orders
GET    /orders/{id}                 - Get order details
PUT    /orders/{id}/status          - Update order status (admin)
DELETE /orders/{id}                 - Cancel order
```

---

## Database Architecture

### Entity Relationship Diagram (Conceptual)

```
┌─────────────┐
│    User     │ (Aggregate Root)
│─────────────│
│ id (PK)     │
│ email       │
│ password    │
│ firstName   │
│ lastName    │
│ createdAt   │
│ updatedAt   │
└──────┬──────┘
       │ 1..N
       │
   ┌───┴──────┐
   │           │
   │      ┌────▼──────────┐
   │      │ Address       │
   │      │─────────────  │
   │      │ id (PK)       │
   │      │ street        │
   │      │ city          │
   │      │ userId (FK)   │
   │      └───────────────┘
   │
   │ 1..N
   │
┌──┴──────────┐
│ Order       │ (Aggregate Root)
│─────────────│
│ id (PK)     │
│ userId (FK) │
│ status      │
│ total       │
│ createdAt   │
│ updatedAt   │
└──────┬──────┘
       │ 1..N
       │
       └─────────────┐
                     │
              ┌──────▼──────────┐
              │ OrderItem       │
              │─────────────────│
              │ id (PK)         │
              │ orderId (FK)    │
              │ productId (FK)  │
              │ quantity        │
              │ unitPrice       │
              └─────────────────┘

┌────────────┐
│ Cart       │ (Aggregate Root)
│────────────│
│ id (PK)    │
│ userId(FK) │
│ totalPrice │
│ createdAt  │
│ updatedAt  │
└──────┬─────┘
       │ 1..N
       │
       └─────────────┐
                     │
              ┌──────▼──────────┐
              │ CartItem        │
              │─────────────────│
              │ id (PK)         │
              │ cartId (FK)     │
              │ productId (FK)  │
              │ quantity        │
              │ unitPrice       │
              └─────────────────┘

┌──────────────┐
│ Category     │ (Aggregate Root)
│──────────────│
│ id (PK)      │
│ name         │
│ description  │
│ createdAt    │
│ updatedAt    │
└──────┬───────┘
       │ 1..N
       │
       └─────────────┐
                     │
              ┌──────▼──────────┐
              │ Product         │
              │─────────────────│
              │ id (PK)         │
              │ name            │
              │ description     │
              │ price           │
              │ stock           │
              │ categoryId (FK) │
              │ createdAt       │
              │ updatedAt       │
              └─────────────────┘
```

### Table Schemas

#### users
```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### addresses
```sql
CREATE TABLE addresses (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    street VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),
    country VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### categories
```sql
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### products
```sql
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(19, 2) NOT NULL,
    stock INTEGER DEFAULT 0,
    category_id INTEGER REFERENCES categories(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### carts
```sql
CREATE TABLE carts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    total_price DECIMAL(19, 2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### cart_items
```sql
CREATE TABLE cart_items (
    id SERIAL PRIMARY KEY,
    cart_id INTEGER NOT NULL REFERENCES carts(id) ON DELETE CASCADE,
    product_id INTEGER NOT NULL REFERENCES products(id),
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(19, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### orders
```sql
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id),
    total_amount DECIMAL(19, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### order_items
```sql
CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id INTEGER NOT NULL REFERENCES products(id),
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(19, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Database Indexes

Recommended indexes for performance:

```sql
-- User lookups
CREATE INDEX idx_users_email ON users(email);

-- Product searches
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_name ON products(name);

-- Order queries
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);

-- Cart lookups
CREATE INDEX idx_carts_user_id ON carts(user_id);
CREATE INDEX idx_cart_items_cart_id ON cart_items(cart_id);

-- Order item searches
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
```

---

## Configuration Management

### Configuration Files

#### application.properties
Main configuration file for Spring Boot settings:

```properties
# Server
server.port=8080
server.servlet.context-path=/

# Spring Data JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.root=INFO
logging.level.com.shopzy=DEBUG
logging.level.org.springframework.security=DEBUG

# Jackson (JSON serialization)
spring.jackson.serialization.write-dates-as-timestamps=false
spring.jackson.time-zone=UTC
```

#### application-secrets.properties
Sensitive configuration (environment-specific):

```properties
# Database connection
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/shopzy}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:shopzy_user}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:password}
spring.datasource.driver-class-name=org.postgresql.Driver

# JWT Configuration
jwt.secret=${JWT_SECRET:your-secret-key-here}
jwt.expiration=${JWT_EXPIRATION:86400000}

# OAuth2 Configuration
spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID:}
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET:}
```

### Environment Variables

Set these in your system or IDE:

```bash
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/shopzy
SPRING_DATASOURCE_USERNAME=shopzy_user
SPRING_DATASOURCE_PASSWORD=your_password

# JWT
JWT_SECRET=your_super_secret_jwt_key_minimum_256_bits
JWT_EXPIRATION=86400000  # 24 hours in milliseconds

# OAuth2 (optional)
GOOGLE_CLIENT_ID=your_client_id
GOOGLE_CLIENT_SECRET=your_client_secret
```

### Profile-Specific Configurations

Create application files for different environments:

```
application-dev.properties      # Development
application-test.properties     # Testing
application-prod.properties     # Production
```

Activate profile via:
```bash
java -jar app.jar --spring.profiles.active=dev
```

---

## Security & Authentication

### Authentication Flow

```
User Request
    ↓
[1] Login Endpoint (POST /users/login)
    ├─ Username/Email & Password
    ├─ Validate credentials
    └─ Generate JWT Token
    ↓
[2] Client Stores JWT Token
    ↓
[3] Subsequent Requests Include Token
    ├─ Authorization: Bearer {jwt_token}
    ↓
[4] JWT Filter (JwtAuthenticationFilter)
    ├─ Extract token from header
    ├─ Validate signature
    ├─ Check expiration
    ├─ Extract user claims
    └─ Create Authentication object
    ↓
[5] Spring Security Authorization
    ├─ Check user roles/permissions
    ├─ Validate request access
    └─ Allow/Deny request
    ↓
[6] Controller handles request
```

### JWT Token Structure

```
Header.Payload.Signature

Header:     {
              "alg": "HS256",
              "typ": "JWT"
            }

Payload:    {
              "sub": "user_id",
              "email": "user@example.com",
              "iat": 1234567890,
              "exp": 1234571490,
              "roles": ["ROLE_USER"]
            }

Signature:  HMACSHA256(Header.Payload, secret)
```

### Security Configuration

Spring Security is configured in `SecurityConfig.java`:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // CORS configuration
    // JWT filter registration
    // Authorization rules
    // Password encoding
    // OAuth2 client setup
}
```

### Password Security

- Passwords are hashed using **BCryptPasswordEncoder**
- Never store plaintext passwords
- Minimum password requirements (implement as needed):
  - Minimum 8 characters
  - Mix of uppercase, lowercase, numbers, special characters

### OAuth2 Integration

The application supports OAuth2 client authentication (e.g., Google, GitHub):

```yaml
spring.security.oauth2.client.registration.google:
  client-id: ${GOOGLE_CLIENT_ID}
  client-secret: ${GOOGLE_CLIENT_SECRET}
  scope: profile,email
  redirect-uri: http://localhost:8080/login/oauth2/code/google
```

### Common Security Vulnerabilities to Avoid

| Vulnerability | Prevention |
|---|---|
| **SQL Injection** | Use JPA queries and parameterized statements |
| **XSS** | Sanitize user input, use Spring Security's CSRF protection |
| **CSRF** | Enable Spring Security's CSRF token protection |
| **Weak Passwords** | Enforce password policies, use bcrypt |
| **JWT Key Exposure** | Store secrets in environment variables, never in code |
| **Unauthorized Access** | Implement proper authorization checks |
| **Insecure Direct Object References** | Validate user owns requested resources |

---

## Testing Strategy

### Testing Pyramid

```
        /\
       /  \      Unit Tests (60%)
      /    \     Narrow, fast, in-memory
     /──────\
    /        \    Integration Tests (30%)
   /          \   Spring context, repositories
  /────────────\
 /              \  E2E/API Tests (10%)
/________________\ Full workflow testing
```

### Unit Testing

Test individual services and components in isolation:

```java
@SpringBootTest
class UserServiceTests {
    
    @InjectMocks
    private UserService userService;
    
    @Mock
    private UserRepository userRepository;
    
    @Test
    void testUserCreation() {
        // Arrange
        User user = new User();
        when(userRepository.save(any())).thenReturn(user);
        
        // Act
        User result = userService.createUser(user);
        
        // Assert
        assertNotNull(result);
        verify(userRepository).save(user);
    }
}
```

### Integration Testing

Test service layer with actual database and Spring context:

```java
@SpringBootTest
@Transactional
class UserServiceIntegrationTests {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Test
    void testCreateAndRetrieveUser() {
        // Create user
        User user = userService.createUser(new User(...));
        
        // Verify persistence
        Optional<User> retrieved = userRepository.findById(user.getId());
        assertTrue(retrieved.isPresent());
    }
}
```

### Running Tests

```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=UserServiceTests

# Run specific test method
./mvnw test -Dtest=UserServiceTests#testUserCreation

# Skip tests during build
./mvnw clean package -DskipTests

# Generate test coverage report
./mvnw test jacoco:report
```

### Test Coverage

Aim for:
- **Core business logic**: >80% coverage
- **Controllers**: >70% coverage
- **Utilities**: >60% coverage
- **Overall project**: >70% coverage

---

## Development Guidelines

### Code Style & Formatting

#### Java Conventions

1. **Class Names**: PascalCase
   ```java
   public class UserService { }
   public class CartItemService { }
   ```

2. **Method Names**: camelCase
   ```java
   public User createUser() { }
   public void updateUserProfile() { }
   ```

3. **Variable Names**: camelCase (lowercase start)
   ```java
   private String firstName;
   private int cartItemCount;
   ```

4. **Constants**: UPPER_SNAKE_CASE
   ```java
   public static final int MAX_CART_ITEMS = 100;
   public static final String JWT_HEADER = "Authorization";
   ```

5. **Package Names**: lowercase, reverse domain notation
   ```java
   com.shopzy.domains.user
   com.shopzy.shared.exception
   ```

#### Code Formatting

Use **Google Java Style Guide** or similar standard:
- 4 spaces for indentation
- 120 character line length limit
- One statement per line
- Consistent brace placement (Egyptian style)

#### IDE Configuration

Configure IntelliJ IDEA or VS Code to match:
1. Settings → Code Style → Java
2. Enable "Reformat Code on Save"
3. Import Google Code Style XML

### Git Workflow

#### Branch Naming Convention

```
feature/<feature-name>           # New feature
bugfix/<bug-name>                # Bug fixes
hotfix/<issue-name>              # Production hotfixes
refactor/<area-name>             # Code refactoring
test/<test-description>          # Test additions
```

Examples:
```
feature/user-authentication
bugfix/cart-calculation-error
hotfix/jwt-expiration-issue
refactor/service-layer-optimization
```

#### Commit Message Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`

Examples:
```
feat(user): add JWT authentication support

- Implement JwtUtil for token generation
- Add SecurityConfig for Spring Security
- Create AuthenticationFilter

Closes #123
```

```
fix(cart): correct total price calculation

Cart total was not updating when item quantity changed.
Updated CartService.updateCartTotal() method.

Fixes #456
```

### Pull Request Process

1. Create feature branch: `git checkout -b feature/your-feature`
2. Make changes and commit frequently
3. Push to remote: `git push origin feature/your-feature`
4. Create Pull Request with description
5. Request code review from team
6. Address review comments
7. Merge to main after approval
8. Delete feature branch

### Code Review Checklist

When reviewing code, check for:

- [ ] Follows coding conventions and style guide
- [ ] No obvious bugs or logic errors
- [ ] Proper error handling and logging
- [ ] No hardcoded values or secrets
- [ ] Unit tests written and passing
- [ ] Documentation updated if needed
- [ ] No performance regressions
- [ ] Security best practices followed
- [ ] Database migrations handled properly

### Documentation Standards

#### Code Comments

```java
/**
 * Transfers money between two accounts.
 *
 * @param fromAccountId the source account ID
 * @param toAccountId   the destination account ID
 * @param amount        the transfer amount (must be positive)
 * @return true if transfer successful, false otherwise
 * @throws AccountNotFoundException if account not found
 * @throws InsufficientFundsException if balance insufficient
 */
public boolean transferMoney(Long fromAccountId, Long toAccountId, BigDecimal amount) {
    // Implementation
}
```

#### README for New Modules

Include for any new domain:
- Module overview and responsibilities
- Key classes and their purposes
- Configuration requirements
- Database schema changes
- API endpoints summary

### Logging Standards

```java
// Use appropriate log levels
logger.debug("Processing user request: {}", userId);      // Development details
logger.info("User {} logged in successfully", username);  // Important events
logger.warn("Cart item quantity exceeded limit", cartId); // Potential issues
logger.error("Database connection failed", exception);    // Error conditions
```

### Exception Handling

```java
// Create domain-specific exceptions
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("User not found with ID: " + userId);
    }
}

// Use in service
public User getUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
}
```

---

## Deployment Guide

### Prerequisites for Production

- Production PostgreSQL database with backups configured
- SSL/TLS certificate for HTTPS
- Environment secrets managed securely
- Monitoring and logging infrastructure
- CDN for static assets (optional)

### Build Artifact

Create executable JAR:

```bash
./mvnw clean package -Pprod
```

Output: `target/shopzy-0.0.1-SNAPSHOT.jar`

### Docker Deployment

#### Dockerfile

The project includes a Dockerfile for containerization:

```dockerfile
FROM openjdk:21-jdk-slim
COPY target/shopzy-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Build Docker Image

```bash
docker build -t shopzy-backend:latest .
```

#### Run Docker Container

```bash
docker run -d \
  --name shopzy-api \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/shopzy \
  -e SPRING_DATASOURCE_USERNAME=shopzy_user \
  -e SPRING_DATASOURCE_PASSWORD=secure_password \
  -e JWT_SECRET=production_jwt_secret \
  shopzy-backend:latest
```

### Docker Compose Setup

Create `docker-compose.yml`:

```yaml
version: '3.8'
services:
  db:
    image: postgres:15-alpine
    environment:
      POSTGRES_DB: shopzy
      POSTGRES_USER: shopzy_user
      POSTGRES_PASSWORD: secure_password
    volumes:
      - postgres_data:/var/lib/postgresql/data
    ports:
      - "5432:5432"

  api:
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/shopzy
      SPRING_DATASOURCE_USERNAME: shopzy_user
      SPRING_DATASOURCE_PASSWORD: secure_password
      JWT_SECRET: ${JWT_SECRET}
    depends_on:
      - db
    restart: unless-stopped

volumes:
  postgres_data:
```

Run with:
```bash
docker-compose up -d
```

### Cloud Deployment (Azure)

#### 1. Create Azure App Service

```bash
az webapp create --resource-group myResourceGroup \
  --plan myAppServicePlan \
  --name shopzy-api \
  --runtime "JAVA|21"
```

#### 2. Configure Database Connection

```bash
az webapp config appsettings set \
  --name shopzy-api \
  --resource-group myResourceGroup \
  --settings \
    SPRING_DATASOURCE_URL="jdbc:postgresql://server.postgres.database.azure.com:5432/shopzy" \
    SPRING_DATASOURCE_USERNAME="user@server" \
    SPRING_DATASOURCE_PASSWORD="password" \
    JWT_SECRET="production_secret"
```

#### 3. Deploy JAR

```bash
az webapp deployment source config-zip \
  --resource-group myResourceGroup \
  --name shopzy-api \
  --src target/shopzy-0.0.1-SNAPSHOT.jar
```

### Health Check Endpoint

Add a health check endpoint (optional):

```java
@RestController
@RequestMapping("/health")
public class HealthController {
    
    @GetMapping
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "version", "0.0.1-SNAPSHOT"
        ));
    }
}
```

Test with:
```bash
curl http://localhost:8080/health
```

### Backup Strategy

#### Database Backups

```bash
# Backup PostgreSQL
pg_dump -U shopzy_user -d shopzy > backup.sql

# Restore from backup
psql -U shopzy_user -d shopzy < backup.sql
```

#### Automated Backups

Set up cron job for regular backups:

```bash
0 2 * * * pg_dump -U shopzy_user shopzy | gzip > /backups/shopzy_$(date +\%Y\%m\%d).sql.gz
```

---

## Common Tasks & Workflows

### Adding a New Domain (Bounded Context)

1. **Create Domain Directory**
   ```
   domains/new-domain/
   ├── controller/
   ├── service/
   ├── repository/
   └── model/
   ```

2. **Create Entity Classes** in `model/`
   ```java
   @Entity
   @Table(name = "new_entities")
   public class NewEntity {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
       // Fields and relationships
   }
   ```

3. **Create Repository** in `repository/`
   ```java
   @Repository
   public interface NewEntityRepository extends JpaRepository<NewEntity, Long> {
       // Custom queries
   }
   ```

4. **Create Service Interface** in `service/`
   ```java
   public interface NewEntityService {
       NewEntity create(NewEntity entity);
       NewEntity getById(Long id);
       // Service methods
   }
   ```

5. **Create Service Implementation** in `service/impl/`
   ```java
   @Service
   public class NewEntityServiceImpl implements NewEntityService {
       // Implementation
   }
   ```

6. **Create Controller** in `controller/`
   ```java
   @RestController
   @RequestMapping("/new-entities")
   public class NewEntityController {
       // REST endpoints
   }
   ```

7. **Add Database Migrations** if using Flyway/Liquibase
   ```sql
   CREATE TABLE new_entities (...);
   ```

### Adding a Feature to Existing Domain

1. Identify the domain
2. Update Entity model with new fields
3. Create database migration
4. Update Repository with new queries
5. Add Service methods
6. Add/update Controller endpoints
7. Write tests
8. Update API documentation

### Debugging Common Issues

#### Issue: Database Connection Failed
```
Solution:
1. Check SPRING_DATASOURCE_URL is correct
2. Verify PostgreSQL is running
3. Check username/password
4. Ensure database exists: psql -l
```

#### Issue: JWT Token Expired
```
Solution:
1. Increase JWT_EXPIRATION value
2. Generate new token
3. Check client clock synchronization
```

#### Issue: Lombok Not Working
```
Solution:
1. Install Lombok plugin in IDE
2. Enable annotation processing: Settings → Build → Compiler → Annotation Processors → Enable
3. Clean and rebuild project
```

#### Issue: Port 8080 Already in Use
```
Solution:
# Change port in application.properties
server.port=8081

# Or kill process using the port (Windows)
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Or (Linux/Mac)
lsof -i :8080
kill -9 <PID>
```

---

## Troubleshooting

### Build Issues

#### Maven Clean Not Working
```bash
# Force clean cache
./mvnw clean -DskipTests -U

# Delete Maven cache manually
rm -rf ~/.m2/repository
```

#### Dependency Conflicts
```bash
# Check dependency tree
./mvnw dependency:tree

# Exclude conflicting dependency
<dependency>
    <groupId>org.package</groupId>
    <artifactId>artifact</artifactId>
    <exclusions>
        <exclusion>
            <groupId>conflicting.group</groupId>
            <artifactId>conflicting-artifact</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

### Runtime Issues

#### High Memory Usage
```bash
# Increase heap size
java -Xmx2G -Xms1G -jar target/shopzy-0.0.1-SNAPSHOT.jar
```

#### Slow Database Queries
```
1. Check indexes are created
2. Enable query logging: spring.jpa.show-sql=true
3. Use @Query with optimized JQL
4. Consider pagination for large result sets
```

#### Connection Pool Exhaustion
```properties
# Increase connection pool size in application.properties
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
```

### Security Issues

#### Password Reset Flow
1. Generate secure reset token
2. Send email with reset link
3. Validate token before allowing reset
4. Update password with hash

#### Token Blacklist (for logout)
```java
// Implement token blacklist on logout
@Service
public class TokenBlacklistService {
    private Set<String> blacklist = Collections.synchronizedSet(new HashSet<>());
    
    public void blacklistToken(String token) {
        blacklist.add(token);
    }
    
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
```

---

## Future Enhancements & Roadmap

### Phase 1: Foundation (Current)
- ✅ DDD restructuring
- ✅ Basic CRUD operations
- ✅ JWT authentication
- ✅ PostgreSQL integration

### Phase 2: Advanced Features (Next 3 months)
- [ ] **Event-Driven Architecture**
  - Implement Domain Events
  - Event Bus/Message Queue (RabbitMQ/Kafka)
  - Event Sourcing

- [ ] **Advanced Security**
  - Two-Factor Authentication (2FA)
  - Role-Based Access Control (RBAC)
  - OAuth2 social login expansion
  - API Key management

- [ ] **Payment Integration**
  - Stripe/PayPal integration
  - Payment processing service
  - Invoice generation

- [ ] **Notification System**
  - Email notifications
  - SMS notifications
  - In-app notifications
  - Webhook support

### Phase 3: Scalability (3-6 months)
- [ ] **Microservices Extraction**
  - Convert domains to microservices
  - Service-to-service communication
  - API Gateway (Kong/AWS API Gateway)

- [ ] **Caching Layer**
  - Redis implementation
  - Cache invalidation strategies
  - Distributed caching

- [ ] **Search Enhancement**
  - Elasticsearch integration
  - Advanced product search
  - Faceted search

- [ ] **Analytics & Reporting**
  - User behavior analytics
  - Sales dashboards
  - Business intelligence

### Phase 4: DevOps & Observability (6-9 months)
- [ ] **Containerization**
  - Kubernetes deployment
  - Container orchestration
  - Service mesh (Istio)

- [ ] **Monitoring & Logging**
  - ELK Stack (Elasticsearch, Logstash, Kibana)
  - Prometheus metrics
  - Grafana dashboards
  - Distributed tracing (Jaeger/Zipkin)

- [ ] **CI/CD Pipeline**
  - GitHub Actions
  - Automated testing
  - Automated deployment
  - Blue-green deployments

- [ ] **Infrastructure as Code**
  - Terraform configurations
  - Docker Compose enhancements
  - Kubernetes manifests

### Phase 5: ML & Intelligence (9-12 months)
- [ ] **Recommendation Engine**
  - Collaborative filtering
  - Content-based recommendations
  - ML model integration

- [ ] **Predictive Analytics**
  - Churn prediction
  - Sales forecasting
  - Fraud detection

- [ ] **Natural Language Processing**
  - Product search NLP
  - Chatbot support
  - Review sentiment analysis

### Technology Roadmap

```
Current (Java 21, Spring Boot 3.5.14)
        ↓
Q3 2026: Spring Boot 3.6 upgrade
        ↓
Q4 2026: Java 22/23 evaluation
        ↓
2027: Java 25 LTS consideration (if released)
        ↓
Continued modern Java framework adoption
```

### Integration Opportunities

- **Payment**: Stripe, PayPal, Square
- **Messaging**: RabbitMQ, Apache Kafka
- **Caching**: Redis, Memcached
- **Search**: Elasticsearch, Solr
- **Monitoring**: Prometheus, ELK Stack
- **Container**: Docker, Kubernetes
- **Cloud**: AWS, Azure, GCP

---

## Quick Reference

### Essential Commands

```bash
# Build
./mvnw clean package

# Run
./mvnw spring-boot:run

# Test
./mvnw test

# Database operations
psql -U postgres -d shopzy
```

### Useful Endpoints

```
http://localhost:8080/swagger-ui.html      (if Swagger added)
http://localhost:8080/health               (health check)
http://localhost:8080/actuator             (actuator endpoints)
```

### Key Files

| File | Purpose |
|------|---------|
| `pom.xml` | Maven dependencies and build configuration |
| `application.properties` | Application settings |
| `application-secrets.properties` | Sensitive configuration |
| `Dockerfile` | Container configuration |
| `SecurityConfig.java` | Spring Security setup |
| `JwtUtil.java` | JWT utilities |

---

## Support & Contact

For questions, issues, or contributions:

1. **Check README.md** for quick start
2. **Review HELP.md** for common issues
3. **Check existing issues** on GitHub
4. **Create new issue** with clear description
5. **Submit pull request** with improvements

---

## Version History

| Version | Date | Changes |
|---------|------|---------|
| 0.0.1-SNAPSHOT | June 2026 | Initial project setup with DDD structure |

---

**Last Updated**: June 7, 2026  
**Maintained By**: ShopZy Development Team  
**License**: [Your License Here]
