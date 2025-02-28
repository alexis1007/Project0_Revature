# Project0 Revature

## Description
Project0 Revature is a Java-based banking application developed during the Revature training program. The application implements core banking functionalities including account management, transactions, and user authentication.

## Features
- User registration and authentication
- Account creation and management
- Deposit and withdrawal operations
- Transaction history tracking
- Administrative controls
- Data persistence using PostgreSQL
- JUnit testing implementation

## Technical Stack
- Java 8+
- PostgreSQL
- JDBC
- JUnit 5
- Log4j
- Maven

## Installation
To install and set up this project, follow these steps:
1. Clone the repository:
    ```bash
    git clone https://github.com/alexi/Project0_revature.git
    ```
2. Navigate to the project directory:
    ```bash
    cd Project0_revature
    ```
3. Ensure you have PostgreSQL installed and running
4. Configure database connection in `src/main/resources/application.properties`
5. Run Maven install:
    ```bash
    mvn clean install
    ```

## Usage
1. Start the application:
    ```bash
    mvn exec:java -Dexec.mainClass="com.revature.Main"
    ```
2. Follow the console prompts to:
   - Create a new account
   - Log in to existing account
   - Perform banking operations
   - View transaction history

## Testing
Run the test suite using:
```bash
mvn test
```

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── revature/
│   │           ├── models/
│   │           ├── dao/
│   │           ├── service/
│   │           └── util/
│   └── resources/
└── test/
    └── java/
        └── com/
            └── revature/
```

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
