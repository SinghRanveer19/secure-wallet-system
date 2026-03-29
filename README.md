#  Secure Digital Wallet System (Fintech Backend)

 A production-grade fintech backend system built using Spring Boot, implementing secure transactions, fraud detection, and idempotent payment handling.

---

##  Features

 **JWT Authentication**

* Stateless authentication
* Secure API access

 **Wallet Management**

* Auto wallet creation
* Balance tracking

 **Money Transfer System**

* Peer-to-peer transfer
* Atomic transactions using @Transactional

 **Idempotency Handling**

* Prevents duplicate transactions
* Ensures consistency in retries

 **Fraud Detection System**

* High-value transaction detection
* Rapid transaction monitoring

---

##  Tech Stack

* Java 17
* Spring Boot
* Spring Security
* JWT (io.jsonwebtoken)
* MySQL
* JPA / Hibernate

---

##  Key Concepts Implemented

* Stateless Authentication (JWT)
* Transaction Management
* ACID Properties
* Idempotency in Payments
* Fraud Detection Logic
* Exception Handling
* Layered Architecture

---

##  Project Structure

controller/ → API layer
service/ → Business logic
repository/ → Data access
entity/ → Database models
security/ → JWT & filters
dto/ → Request/response models

---

##  API Endpoints

### Auth APIs

* POST /auth/register
* POST /auth/login

### Wallet APIs

* POST /wallet/add-money
* POST /wallet/transfer
* GET /wallet/balance/{userId}

---

##  Sample Request

### Transfer Money

```json
{
  "senderId": 1,
  "receiverId": 2,
  "amount": 500,
  "idempotencyKey": "txn-123"
}
```

---

##  How to Run

1. Clone repo
2. Configure MySQL in application.properties
3. Run Spring Boot app

---

##  Future Enhancements

* Payment Gateway Integration
* Microservices Architecture
* Kafka for async processing
* Redis for caching & rate limiting

---

##  Why This Project?

This project demonstrates real-world fintech challenges such as:

* Secure transactions
* Fraud prevention
* System consistency
* Scalable backend design

---

##  Author

Ranveer Kumar Singh
