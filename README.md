# 🏦 Banking System

A secure and robust **Banking System REST API** built using **Java and Spring Boot**.

This project provides core banking operations such as account management, deposits, withdrawals, money transfers, and transaction history. It uses **Spring Security** to secure protected endpoints and **PostgreSQL** for persistent data storage. APIs are documented and testable via **Swagger/OpenAPI**.

---

## 📑 Table of Contents

- [Features](#-features)
- [Tech Stack](#️-tech-stack)
- [Architecture](#️-architecture)
- [Project Structure](#-project-structure)
- [Getting Started](#️-getting-started)
- [Database Configuration](#️-database-configuration)
- [Running the Application](#️-running-the-application)
- [API Documentation](#-api-documentation)
- [Security](#-security)
- [Core API Operations](#-core-api-operations)
- [Operation Flows](#-operation-flows)
- [Testing](#-testing)
- [Learning Outcomes](#-learning-outcomes)
- [Future Improvements](#-future-improvements)
- [Author](#-author)
- [License](#-license)

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
|---|---|
| Java | Programming language |
| Spring Boot | Backend framework |
| Spring Security | Authentication & authorization |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| PostgreSQL | Database |
| Maven | Build & dependency management |
| Swagger / OpenAPI | API documentation |
| JUnit | Testing |
| Git & GitHub | Version control |

---

## 🏗️ Architecture

The application follows a layered architecture:

```
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
```

| Layer | Responsibility |
|---|---|
| **Controller** | Handles HTTP requests and responses |
| **Service** | Core business logic — deposits, withdrawals, transfers, balance validation, transaction processing |
| **Repository** | Uses Spring Data JPA to communicate with the PostgreSQL database |
| **Entity** | Represents the application's database entities |
| **Security** | Handles authentication, authorization, and protection of sensitive API endpoints |

### End-to-End Request Flow

```
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
```

---

## 📁 Project Structure

```
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
```

---

## ⚙️ Getting Started

### Prerequisites

Make sure you have the following installed:

- Java 17 or later
- Maven
- PostgreSQL
- Git

Check your Java and Maven versions:

```bash
java -version
mvn -version
```

### 📥 Clone the Repository

```bash
git clone https://github.com/<your-username>/banking-system-spring-boot.git
cd banking-system-spring-boot
```

---

## 🗄️ Database Configuration

Create a PostgreSQL database:

```sql
CREATE DATABASE banking_system;
```

Configure the database connection in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/banking_system
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> ⚠️ **Important:** Never commit your actual database password or other sensitive credentials to GitHub. For production environments, use environment variables or a secrets manager.

---

## ▶️ Running the Application

Build the project:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

The application will start at:

```
http://localhost:8081
```

---

## 📖 API Documentation

Swagger/OpenAPI is integrated into the application for API documentation and testing.

After starting the application, open:

```
http://localhost:8081/swagger-ui/index.html
```

Swagger allows you to:

- View all available API endpoints
- View request and response structures
- Test REST APIs, including protected endpoints
- View API parameters
- Explore the banking system API

---

## 🔐 Security

The application uses Spring Security to protect sensitive banking operations, including:

- Deposits
- Withdrawals
- Money transfers
- Transaction history
- Other secured account operations

Unauthorized users cannot access protected endpoints without proper authentication.

---

## 🔗 Core API Operations

| Operation | HTTP Method | Description |
|---|---|---|
| Create Account | `POST` | Create a new bank account |
| Get Account | `GET` | Retrieve account details |
| Get Balance | `GET` | Retrieve account balance |
| Deposit | `POST` | Deposit money into an account |
| Withdraw | `POST` | Withdraw money from an account |
| Transfer | `POST` | Transfer money between accounts |
| Transaction History | `GET` | Retrieve transaction history |

> The exact endpoint paths and request formats are available in Swagger UI.

---

## 🔄 Operation Flows

### 💰 Deposit Flow

```
Deposit Request → Validate Account → Validate Amount →
Update Account Balance → Create Transaction → Return Response
```

### 💸 Withdrawal Flow

```
Withdrawal Request → Validate Account → Validate Amount →
Check Available Balance → Update Account Balance →
Create Transaction → Return Response
```

### 🔄 Money Transfer Flow

```
Transfer Request → Validate Sender → Validate Receiver →
Validate Amount → Check Sender Balance → Debit Sender →
Credit Receiver → Record Transaction → Return Response
```

### 📜 Transaction History

The system maintains transaction records for banking operations, including:

- Deposits
- Withdrawals
- Transfers

Each transaction record can include:

- Transaction ID
- Account information
- Transaction type
- Amount
- Timestamp
- Transaction status

This allows account holders to track their banking activities.

---

## 🧪 Testing

The project includes tests for backend functionality.

Run the complete test suite using:

```bash
mvn test
```

Testing covers:

- Account operations
- Deposit functionality
- Withdrawal functionality
- Transfer functionality
- Transaction operations
- Security behavior
- REST API functionality

---

## 🎯 Learning Outcomes

This project helped strengthen practical knowledge of:

- Java & Spring Boot
- REST API development
- Spring Security (Authentication & Authorization)
- Spring Data JPA & Hibernate
- PostgreSQL
- Layered architecture
- Exception handling & input validation
- Swagger/OpenAPI
- Unit & integration testing
- Git & GitHub

---

## 🔮 Future Improvements

- JWT-based authentication
- Role-based access control
- Admin dashboard
- Pagination for transaction history
- Transaction filtering and sorting
- Email notifications
- Bank account statements
- Scheduled transactions
- Docker support
- Cloud deployment
- React frontend
- Improved logging and monitoring

---

## 👨‍💻 Author

**Sayondeep Daripa**


---

## ⭐ Project Highlights

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

---

## 📄 License

This project is developed for educational and portfolio purposes.

---

> **Before pushing:** replace `<your-username>` in the clone URL with your actual GitHub username, and make sure the endpoint names above match your actual controllers.
