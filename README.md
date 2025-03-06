# Project0 Revature - Loan Management System

## Description
Project0 Revature is a Java-based banking application developed during the Revature training program. The application implements core banking functionalities including account management, loan processing, and user authentication.

## Features
- User registration and authentication
- Role-based access control (User/Manager)
- Loan application management
- Profile management
- Data persistence using PostgreSQL
- Session-based authentication
- Comprehensive logging system

## Technical Stack
- Java 17
- PostgreSQL
- JDBC
- Javalin (REST API)
- JUnit 5 (Testing)
- Logback (Logging)
- Maven

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── Application/
│   │       ├── Controller/
│   │       ├── Service/
│   │       ├── DAO/
│   │       ├── Model/
│   │       └── Util/
│   └── resources/
│       └── logback.xml
└── test/
    └── java/
        └── Application/
```

## Installation
1. Clone the repository:
```bash
git clone https://github.com/alexi/Project0_revature.git
```

2. Configure PostgreSQL:
   - Create a database
   - Run the schema scripts in `/Database Model/`
   - Update database credentials in `src/main/resources/application.properties`

3. Build the project:
```bash
mvn clean install
```

## Running the Application
```bash
mvn exec:java -Dexec.mainClass="Application.Application"
```

## Testing
Run the test suite:
```bash
mvn test
```

## Logging
The application uses Logback for logging with the following levels:
- INFO: Normal application flow (login, transactions)
- WARN: Suspicious activities (invalid attempts)
- ERROR: System errors and exceptions

Logs are written to:
- Console
- `logs/application.log`

Configuration can be found in `src/main/resources/logback.xml`

## API Endpoints
- POST `/auth/login` - User authentication
- POST `/auth/register` - New user registration
- GET `/accounts/{id}` - Get account details
- POST `/loans` - Create loan application
- GET `/loans` - View loans (all for managers, own for users)
- PUT `/loans/{id}/approve` - Approve loan (managers only)

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push to the branch
5. Create a Pull Request

## License
This project is licensed under the MIT License.
