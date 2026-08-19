# 🏦 Banking System

A secure and robust **Banking System REST API** built using **Java and Spring Boot**.

This project provides core banking operations such as **account management, deposits, withdrawals, money transfers, and transaction history**. It uses **Spring Security** to secure protected endpoints and **PostgreSQL** for persistent data storage.

The APIs are documented and can be tested using **Swagger/OpenAPI**.

---

## 🚀 Features

### 👤 Account Management

- Create bank accounts
- Retrieve account details
- Retrieve account balance
- Manage account information

### 💰 Deposit

- Deposit money into an account
- Update account balance
- Record deposit transactions

### 💸 Withdrawal

- Withdraw money from an account
- Validate available balance
- Prevent withdrawal when sufficient balance is unavailable
- Record withdrawal transactions

### 🔄 Money Transfer

- Transfer money between accounts
- Validate sender and receiver accounts
- Check sender's available balance
- Update sender and receiver balances
- Record transfer transactions

### 📜 Transaction History

- Maintain transaction records
- Retrieve transaction history
- Track deposits, withdrawals, and transfers
- Store transaction amount, type, and timestamp

### 🔐 Security

- Spring Security integration
- Authentication and authorization
- Protected REST API endpoints
- Secure access to banking operations

### 📖 API Documentation

- Swagger/OpenAPI integration
- Interactive API documentation
- Test APIs directly from Swagger UI

### 🗄️ Database

- PostgreSQL database
- Spring Data JPA
- Hibernate ORM
- Persistent storage for accounts and transactions

### 🧪 Testing

- Unit testing
- Integration testing
- API testing
- Testing of core banking operations

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| Java | Programming Language |
| Spring Boot | Backend Framework |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | Database Access |
| Hibernate | ORM |
| PostgreSQL | Database |
| Maven | Build & Dependency Management |
| Swagger / OpenAPI | API Documentation |
| JUnit | Testing |
| Git & GitHub | Version Control |

---

## 🏗️ Architecture

The application follows a layered architecture:


                    Client
                      |
                      ↓
              REST Controllers
                      |
                      ↓
                Service Layer
                      |
                      ↓
              Repository Layer
                      |
                      ↓
               PostgreSQL DB
Controller Layer

Handles HTTP requests and responses.

Service Layer

Contains the core business logic of the banking system, including:

Deposits
Withdrawals
Money transfers
Balance validation
Transaction processing
Repository Layer

Uses Spring Data JPA to communicate with the PostgreSQL database.

Entity Layer

Represents the application's database entities.

Security Layer

Handles authentication, authorization, and protection of sensitive API endpoints.

📁 Project Structure
src/
├── main/
│   ├── java/
│   │   └── com.banking.Banking.System/
│   │       ├── Controller/
│   │       ├── Service/
│   │       ├── Repository/
│   │       ├── Entity/
│   │       ├── Config/
│   │       └── ...
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    └── java/
        └── com.banking.Banking.System/
⚙️ Getting Started
Prerequisites

Make sure you have the following installed:

Java 17 or later
Maven
PostgreSQL
Git

Check your Java and Maven versions:

java -version
mvn -version
📥 Clone the Repository
git clone https://github.com/<your-username>/banking-system-spring-boot.git

Navigate to the project directory:

cd banking-system-spring-boot
🗄️ Database Configuration

Create a PostgreSQL database:

CREATE DATABASE banking_system;

Configure the database connection in:

src/main/resources/application.properties

Example:

spring.datasource.url=jdbc:postgresql://localhost:5432/banking_system
spring.datasource.username=your_username
spring.datasource.password=your_password


spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

⚠️ Important: Never commit your actual database password or other sensitive credentials to GitHub.

For production environments, use environment variables or a secrets manager.

▶️ Running the Application

Build the project:

mvn clean install

Run the application:

mvn spring-boot:run

The application will start at:

http://localhost:8081
📖 Swagger API Documentation

Swagger/OpenAPI is integrated into the application for API documentation and testing.

After starting the application, open:

http://localhost:8081/swagger-ui/index.html

Swagger allows you to:

View all available API endpoints
View request and response structures
Test REST APIs
Test protected endpoints
View API parameters
Explore the banking system API
🔐 Security

The application uses Spring Security to protect sensitive banking operations.

Protected operations include:

Deposits
Withdrawals
Money transfers
Transaction history
Other secured account operations

Unauthorized users cannot access protected endpoints without proper authentication.

🔗 Core API Operations

The Banking System provides REST APIs for:

Operation	HTTP Method	Description
Create Account	POST	Create a new bank account
Get Account	GET	Retrieve account details
Get Balance	GET	Retrieve account balance
Deposit	POST	Deposit money into an account
Withdraw	POST	Withdraw money from an account
Transfer	POST	Transfer money between accounts
Transaction History	GET	Retrieve transaction history

The exact endpoint paths and request formats are available in Swagger UI.

💰 Deposit Flow

The deposit operation follows this flow:

Deposit Request
      ↓
Validate Account
      ↓
Validate Amount
      ↓
Update Account Balance
      ↓
Create Transaction
      ↓
Return Response
💸 Withdrawal Flow

The withdrawal operation follows this flow:

Withdrawal Request
       ↓
Validate Account
       ↓
Validate Amount
       ↓
Check Available Balance
       ↓
Update Account Balance
       ↓
Create Transaction
       ↓
Return Response
🔄 Money Transfer Flow

The transfer operation moves money from one account to another.

Transfer Request
       ↓
Validate Sender
       ↓
Validate Receiver
       ↓
Validate Amount
       ↓
Check Sender Balance
       ↓
Debit Sender
       ↓
Credit Receiver
       ↓
Record Transaction
       ↓
Return Response
📜 Transaction History

The system maintains transaction records for banking operations.

Transactions can include:

Deposit
Withdrawal
Transfer

Transaction information can include:

Transaction ID
Account information
Transaction type
Amount
Timestamp
Transaction status

This allows account holders to track their banking activities.

🧪 Testing

The project includes tests for backend functionality.

Run the complete test suite using:

mvn test

Testing covers important application functionality such as:

Account operations
Deposit functionality
Withdrawal functionality
Transfer functionality
Transaction operations
Security behavior
REST API functionality
🔄 Banking System Flow
                    Client
                      |
                      ↓
                Spring Security
                      |
                      ↓
               REST Controller
                      |
                      ↓
                Service Layer
                      |
          ┌───────────┴───────────┐
          ↓                       ↓
   Account Repository      Transaction Repository
          ↓                       ↓
          └───────────┬───────────┘
                      ↓
                 PostgreSQL
🎯 Learning Outcomes

This project helped strengthen practical knowledge of:

Java
Spring Boot
REST API development
Spring Security
Authentication & Authorization
Spring Data JPA
Hibernate
PostgreSQL
Layered Architecture
Exception Handling
Input Validation
Swagger/OpenAPI
Unit Testing
Integration Testing
Git & GitHub
🔮 Future Improvements

Possible future improvements include:

JWT-based authentication
Role-based access control
Admin dashboard
Pagination for transaction history
Transaction filtering and sorting
Email notifications
Bank account statements
Scheduled transactions
Docker support
Cloud deployment
React frontend
Improved logging and monitoring
👨‍💻 Author

Sayondeep Daripa

Computer Science Engineering
Siddaganga Institute of Technology, Tumkur

⭐ Project Highlights
✔ Secure Banking REST API
✔ Account Management
✔ Deposit & Withdrawal
✔ Money Transfer
✔ Transaction History
✔ Spring Security
✔ PostgreSQL
✔ JPA / Hibernate
✔ Swagger / OpenAPI
✔ Automated Testing
✔ Layered Backend Architecture
📄 License

This project is developed for educational and portfolio purposes.



**One thing before you push it:** replace `<your-username>` with your actual GitHub username, and make sure the README's endpoint names match your actual controllers.
