# ShopZy Backend - Domain Driven Design (DDD) Restructuring

## Overview
The ShopZy Backend has been restructured to follow **Domain Driven Design** principles. The project is now organized into clear bounded contexts (domains), each with its own controller, service, repository, and model layers.

## Project Structure

```
src/main/java/com/shopzy/
├── domains/
│   ├── user/                          (User Bounded Context - Aggregate Root: User)
│   │   ├── controller/
│   │   │   ├── UserController.java
│   │   │   └── AddressController.java
│   │   ├── service/
│   │   │   ├── UserService.java
│   │   │   ├── AddressService.java
│   │   │   └── impl/
│   │   │       ├── UserServiceImpl.java
│   │   │       └── AddressServiceImpl.java
│   │   ├── repository/
│   │   │   ├── UserRepository.java
│   │   │   └── AddressRepository.java
│   │   └── model/
│   │       ├── User.java (Aggregate Root)
│   │       └── Address.java (Entity)
│   │
│   ├── catalog/                       (Catalog Bounded Context - Aggregate Root: Product, Category)
│   │   ├── controller/
│   │   │   ├── ProductController.java
│   │   │   └── CategoryController.java
│   │   ├── service/
│   │   │   ├── ProductService.java
│   │   │   ├── CategoryService.java
│   │   │   └── impl/
│   │   │       ├── ProductServiceImpl.java
│   │   │       └── CategoryServiceImpl.java
│   │   ├── repository/
│   │   │   ├── ProductRepository.java
│   │   │   └── CategoryRepository.java
│   │   └── model/
│   │       ├── Product.java (Aggregate Root)
│   │       └── Category.java (Aggregate Root)
│   │
│   ├── cart/                          (Cart Bounded Context - Aggregate Root: Cart)
│   │   ├── controller/
│   │   │   ├── CartController.java
│   │   │   └── CartItemController.java
│   │   ├── service/
│   │   │   ├── CartService.java
│   │   │   ├── CartItemService.java
│   │   │   └── impl/
│   │   │       ├── CartServiceImpl.java
│   │   │       └── CartItemServiceImpl.java
│   │   ├── repository/
│   │   │   ├── CartRepository.java
│   │   │   └── CartItemRepository.java
│   │   └── model/
│   │       ├── Cart.java (Aggregate Root)
│   │       └── CartItem.java (Entity)
│   │
│   ├── order/                         (Order Bounded Context - Aggregate Root: Order)
│   │   ├── controller/
│   │   │   ├── OrderController.java
│   │   │   └── OrderItemController.java
│   │   ├── service/
│   │   │   ├── OrderService.java
│   │   │   ├── OrderItemService.java
│   │   │   └── impl/
│   │   │       ├── OrderServiceImpl.java
│   │   │       └── OrderItemServiceImpl.java
│   │   ├── repository/
│   │   │   ├── OrderRepository.java
│   │   │   └── OrderItemRepository.java
│   │   └── model/
│   │       ├── Order.java (Aggregate Root)
│   │       └── OrderItem.java (Entity)
│   │
│   └── (Shared cross-cutting concerns - see below)
│
├── shared/                            (Shared/Common Resources)
│   ├── security/
│   │   └── JwtUtil.java              (JWT authentication utilities)
│   ├── exception/                    (Application-wide exceptions)
│   ├── util/                         (Common utilities)
│   └── valueobject/
│       └── OrderStatus.java          (Immutable value object for order status)
│
├── controller/
│   └── BaseController.java           (Root controller for common endpoints)
│
└── ShopzyApplication.java            (Spring Boot main application class)
```

## Bounded Contexts & Aggregates

### 1. **User Domain** (User Bounded Context)
- **Aggregate Root**: `User`
- **Entities**: `Address`
- **Responsibility**: Manage user accounts, authentication, and addresses
- **API Endpoints**:
  - `/users` - User CRUD operations
  - `/addresses` - Address CRUD operations

### 2. **Catalog Domain** (Catalog Bounded Context)
- **Aggregate Roots**: `Product`, `Category`
- **Responsibility**: Manage product catalog and categories
- **API Endpoints**:
  - `/products` - Product CRUD and search
  - `/categories` - Category CRUD operations

### 3. **Cart Domain** (Cart Bounded Context)
- **Aggregate Root**: `Cart`
- **Entities**: `CartItem`
- **Responsibility**: Manage shopping carts and cart items
- **Dependencies**: Depends on User (owns cart) and Product (cart items)
- **API Endpoints**:
  - `/api/cart-items` - Cart operations
  - `/cart-items` - CartItem CRUD operations

### 4. **Order Domain** (Order Bounded Context)
- **Aggregate Root**: `Order`
- **Entities**: `OrderItem`
- **Responsibility**: Manage orders and order items
- **Dependencies**: Depends on User (order owner) and Product (ordered items)
- **API Endpoints**:
  - `/orders` - Order CRUD operations
  - `/order-items` - OrderItem CRUD operations

### 5. **Shared Resources**
- **Security**: JWT utilities for authentication across domains
- **Value Objects**: `OrderStatus` enum (immutable, shared across domains)
- **Common Exceptions**: Shared exception classes (if added)

## Key DDD Concepts Applied

### Aggregate Roots
- **User** - Owns Address entities
- **Product** - Standalone entity
- **Category** - Standalone entity
- **Cart** - Owns CartItem entities
- **Order** - Owns OrderItem entities

### Bounded Contexts
Each domain is a separate package (`domains/{domain-name}`) with its own:
- Models (Entities & Value Objects)
- Repositories
- Services (Business Logic)
- Controllers (API Endpoints)

### Inter-Domain Communication
- **User → Cart**: Cart has a one-to-one reference to User
- **User → Order**: Order has a many-to-one reference to User
- **Cart → Product**: CartItem references Product
- **Order → Product**: OrderItem references Product
- **Direct Service Calls**: Domains communicate via direct service dependencies

### Shared Resources
- `OrderStatus` - Value object shared across domains
- `JwtUtil` - Security utility shared across domains
- Common exceptions and utilities in `/shared`

## Design Patterns Used

1. **Repository Pattern**: Data access abstraction
2. **Service Pattern**: Business logic encapsulation
3. **Factory Pattern**: (Potential) Service implementations
4. **Value Object Pattern**: OrderStatus enum
5. **Bounded Contexts**: Domain separation

## Migration Benefits

✅ **Clear Separation of Concerns**: Each domain owns its logic
✅ **Scalability**: Domains can scale independently
✅ **Maintainability**: Easier to locate and modify domain-specific code
✅ **Testability**: Each domain can be tested in isolation
✅ **Future Microservices**: Easy to extract domains into separate microservices
✅ **Team Organization**: Different teams can work on different domains

## Building & Running

```bash
# Build the project
mvnw clean compile

# Run tests (if added)
mvnw test

# Run the application
mvnw spring-boot:run
```

## Adding New Features

When adding new features:
1. **Identify the Bounded Context** - Which domain should this belong to?
2. **Add Model Classes** - Create entities/value objects in `domains/{domain}/model`
3. **Create Repository** - Define data access in `domains/{domain}/repository`
4. **Implement Service** - Add business logic in `domains/{domain}/service`
5. **Create Controller** - Expose API in `domains/{domain}/controller`
6. **Cross-Domain Dependencies** - If needed, reference other domain models

## Future Enhancements

- Add Domain Events for inter-domain communication (instead of direct calls)
- Implement Specification Pattern for complex queries
- Add Application Services layer
- Create DTOs for API requests/responses to decouple from domain models
- Implement Event Sourcing for audit trails
- Extract domains into separate microservices

---

**Project Status**: ✅ Restructured to DDD | ✅ Build Successful | 🔄 Ready for Feature Development
