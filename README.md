# Marketplace API

Marketplace API is a RESTful web service for managing advertisements and users. This project is built using Spring Boot and provides endpoints for creating, retrieving, and managing advertisements and users.

## Table of Contents

- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Usage](#usage)
- [License](#license)

## Getting Started

These instructions will help you set up and run the project on your local machine for development and testing purposes.

### Prerequisites

Ensure you have the following installed on your system:

- Java 11 or higher
- Maven 3.6.0 or higher
- MySQL (or any other database of your choice)

### Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/INYAT-gif/Marketplace.git
    cd Marketplace
    ```

2. **Configure the database:**

    Update the `application.properties` file located in `src/main/resources` with your database configuration:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/marketplace
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto = create
    spring.jpa.show-sql = true
    spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
    ```

3. **Install dependencies and build the project:**

    ```bash
    mvn clean install
    ```

### Running the Application

Run the Spring Boot application using the following command:

```bash
mvn spring-boot:run

### API Documentation
http://localhost:8080/swagger-ui/index.html

### Usage
GET /api/advertisements

POST /api/advertisements
Content-Type: application/json

{
  "title": "Sample Advertisement",
  "description": "This is a sample advertisement.",
  "expirationDate": "2024-12-31",
  "email": "user@example.com",
  "username": "sampleuser",
  "password": "samplepassword"
}

GET /api/users

POST /api/users/authenticate
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "samplepassword"
}

### Licence


### Explanation
- **Getting Started**: Provides an introduction and basic setup steps.
- **Prerequisites**: Lists the software requirements.
- **Installation**: Includes steps to clone the repository, configure the database, and build the project.
- **Running the Application**: Shows how to run the Spring Boot application.
- **API Documentation**: Explains how to access the Swagger UI for API documentation.
- **Usage**: Provides examples of how to use the API endpoints.
- **License**: Mentions the licensing information for the project.
