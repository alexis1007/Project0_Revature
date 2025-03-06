# Project0 Revature - Loan Management System

## Description
Project0 Revature is a Java-based loan management system that implements secure user authentication, role-based access control, and comprehensive loan application processing. The system allows users to apply for loans while managers can review and process these applications.

## Features
- User authentication with BCrypt password hashing
- Role-based access control (Admin/Manager/User)
- Loan application management
- User profile management
- Address management
- Session-based authentication
- Comprehensive logging system
- Database persistence with PostgreSQL
- RESTful API endpoints

## Technical Stack
- Java 17
- PostgreSQL 14+
- JDBC
- Javalin 5.0.1
- JUnit 5.9.2
- Logback 1.4.7
- BCrypt 0.4
- Maven
- Jackson 2.15.2

## Project Structure
```plaintext
Project0_revature/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── Application/
│   │   │       ├── Controller/    # HTTP request handlers
│   │   │       ├── Service/       # Business logic
│   │   │       ├── DAO/          # Database access
│   │   │       ├── Model/        # Data models
│   │   │       ├── DTO/          # Data transfer objects
│   │   │       └── Util/         # Utilities
│   │   └── resources/
│   │       └── logback.xml       # Logging configuration
│   └── test/
│       └── java/
│           └── Application/       # Unit tests
├── Database Model/               # SQL scripts
│   ├── Postgres-script.sql      # Schema definition
│   └── data.sql                 # Test data
└── README.md
```

## Installation

### 1. Prerequisites
- Java 17 or higher
- PostgreSQL 14+
- Maven 3.8+

### 2. Clone the repository
```bash
git clone https://github.com/yourusername/Project0_revature.git
cd Project0_revature
```

### 3. Configure PostgreSQL
```sql
-- Create database
CREATE DATABASE loandb;
```

Run the schema scripts:
```bash
psql -U postgres -d loandb -f "Database Model/Postgres-script.sql"
psql -U postgres -d loandb -f "Database Model/data.sql"
```

### 4. Build the project
```bash
mvn clean install
```

## API Documentation

### Authentication
```plaintext
POST /auth/register    - Register new user
POST /auth/login      - User login
```

### Users
```plaintext
GET  /users/{user_id} - Get user details
GET  /users          - List all users (admin only)
PUT  /users/{user_id} - Update user
```

### Profiles
```plaintext
GET    /profiles     - List all profiles
GET    /profiles/{id} - Get profile details
POST   /profiles     - Create profile
PUT    /profiles/{id} - Update profile
DELETE /profiles/{id} - Delete profile
```

### Loans
```plaintext
GET  /loans          - List all/user loans
GET  /loans/{id}     - Get loan details
POST /loans          - Create loan application
PUT  /loans/{id}/status - Update loan status (manager only)
```

## Testing
Run the test suite:
```bash
mvn test
```

## Logging Configuration
```xml
// filepath: /src/main/resources/logback.xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs/application.log</file>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

## Security Features
- Password hashing using BCrypt
- Session-based authentication
- Role-based access control
- Input validation
- SQL injection protection
- CORS enabled

## License
This project is licensed under the MIT License. See [LICENSE](LICENSE) file for details.

## Contributing
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
````
