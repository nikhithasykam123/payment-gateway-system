# 💳 Payment Gateway System – Microservices-based Payment Processing

### 📖 Overview
**Payment Gateway System** is a **Spring Boot microservices project** that simulates a real-world **digital payment platform**.  
It allows users to register, create orders, make payments, track transactions, and receive real-time notifications — similar to how modern wallets and payment processors (like **Paytm, Razorpay**).

---

## 🔄 Functionality Flow

### 1. User Service
- User registration & login with **JWT authentication**  
- Wallet balance management

### 2. Order Service
- Create orders with a specified amount  
- Orders stored as `PENDING`, updated after payment  

### 3. Payment Service
- Validates user & wallet balance  
- Deducts balance on successful payment  
- Updates order status → `COMPLETED`

### 4. Transaction Service
- Records all payment transactions  
- Provides **transaction history API**

### 5. Notification Service
- Sends real-time alerts using **Redis Pub/Sub**  
- Example: *“Your payment of ₹500 for Order #123 is successful.”*

### 6. Service Discovery & Gateway
- All services registered in **Eureka Server**  
- **API Gateway** routes client requests securely  

---

## 🛠 Tech Stack
- **Backend:** Java 17, Spring Boot (Web, Data JPA, Security, Cloud)  
- **Authentication:** JWT (JSON Web Token)  
- **Databases:** MySQL (persistent), Redis (real-time notifications)  
- **Messaging:** Apache Kafka (event-driven communication)  
- **Service Discovery:** Eureka Server  
- **API Gateway:** Spring Cloud Gateway  
- **Logging & Monitoring:** SLF4J, Logback  
- **Deployment:** Docker, Docker Compose  
- **Build & VCS:** Maven, Git, GitHub
  

📌 Sample Flow

Register/Login → get JWT Token

Create an Order (amount ₹1000)

Make a Payment → balance deducted, order status updated

Transaction Recorded → “₹1000 paid for Order #123”

Notification Sent → via Redis Pub/Sub

📷 Architecture Diagram
                  +-------------------+
                  |   Client (UI)     |
                  +---------+---------+
                            |
                            v
                  +-------------------+
                  |   API Gateway     |
                  +---------+---------+
                            |
          ----------------------------------------
          |            |            |            |
          v            v            v            v
   +-------------+  +-------------+  +-------------+
   | USER-SERVICE|  | ORDER-SERVICE|  | PAYMENT-SERVICE|
   +------+------+  +------+-------+  +-------+-------+
          |                  |                  |
          |                  v                  v
          |          +-------------+    +-------------+
          |          |TRANSACTION  |    |NOTIFICATION |
          |          |   SERVICE   |    |   SERVICE   |
          |          +-------------+    +-------------+
          |                     |                |
          v                     |                v
   +-------------+         +-------------+   +-------------+
   |   MySQL     |         |   MySQL     |   |   Redis     |
   +-------------+         +-------------+   +-------------+

                **[ Service Discovery: Eureka ]
               [ Communication: Kafka + REST ]**
