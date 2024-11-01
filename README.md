# Food Order Service

This is a Food Order Service application built with Java and Spring Boot. It provides functionalities for managing roles, users, food items, orders, and more. The application is designed to facilitate the process of ordering food from various restaurants, managing user roles, and handling authentication and authorization.

## Table of Contents

- [Technologies](#technologies)
- [Architecture](#architecture)
- [Setup](#setup)
- [Usage](#usage)
- [Endpoints](#endpoints)

## Technologies

- Java 17
- Spring Boot
- Lombok
- MySQL
- JWT
- Slf4j
- Maven


## Architecture

The application follows a layered architecture with the following main components:

- **Controller Layer**: Handles HTTP requests and responses. It includes controllers for roles, authentication, food items, orders, and more.
- **Service Layer**: Contains business logic and interacts with the repository layer.
- **Repository Layer**: Manages data persistence and retrieval using Spring Data JPA.
- **DTOs (Data Transfer Objects)**: Used to transfer data between the layers.
- **Entities**: Represent the database tables.

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/aydnorcn/food_order.git
    cd food_order
    ```

2. Install the dependencies:
    ```sh
    mvn clean install
    ```

3. Configure the database:
    - Update the `application.properties` file with your MySQL database credentials.


4. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Usage

To use the application, you can send HTTP requests to the endpoints defined in the controllers. You can use tools like Postman or cURL to interact with the API.

### Authentication

Before accessing protected endpoints, you need to authenticate and obtain a JWT token.

1. **Register a new user**:
    ```sh
    POST /api/auth/register
    ```

2. **Login**:
    ```sh
    POST /api/auth/login
    ```

Use the obtained JWT token in the `Authorization` header for subsequent requests.

## Endpoints

Here are some of the main endpoints available in the application:

- **Roles**
    - `GET /api/roles` - Get all roles
    - `GET /api/roles/{id}` - Get role by ID
    - `POST /api/roles` - Create a new role
    - `PUT /api/roles/{id}` - Update a role
    - `DELETE /api/roles/{id}` - Delete a role

- **Auth**
    - `POST /api/auth/login` - Login with email and password
    - `POST /api/auth/register` - Register a new user

- **Food Items**
    - `GET /api/foods` - Get all food items
    - `GET /api/foods/{id}` - Get food item by ID
    - `POST /api/foods` - Create a new food item
    - `PUT /api/foods/{id}` - Update a food item
    - `DELETE /api/foods/{id}` - Delete a food item

- **Orders**
    - `GET /api/orders` - Get all orders
    - `GET /api/orders/{id}` - Get order by ID
    - `POST /api/orders` - Create a new order
    - `PUT /api/orders/{id}` - Update an order
    - `DELETE /api/orders/{id}` - Delete an order
