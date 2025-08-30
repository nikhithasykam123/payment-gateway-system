# 💳 Payment Gateway System (Spring Boot Microservices)

Microservices-based payment flow using **Spring Boot 3**, **Spring Cloud**, **Eureka**, **Gateway**, **Kafka**, and **MySQL**.

## Services
- `service-registry` – Eureka server
- `api-gateway` – Spring Cloud Gateway
- `user-service` – Users + wallet
- `order-service` – Orders
- `payment-service` – Payment orchestration
- `transaction-service` – Transaction logs
- `notification-service` – Kafka consumer

## Quick Start
1) `docker-compose up -d` (starts MySQL, Zookeeper, Kafka)
2) Run Spring Boot apps in this order:
   - service-registry
   - api-gateway
   - user-service
   - order-service
   - payment-service
   - transaction-service
   - notification-service

## Default Ports
- Eureka: 8761
- Gateway: 8080
- user-service: 8081
- order-service: 8082
- payment-service: 8083
- transaction-service: 8084
- notification-service: 8085

## API Sketch
- User: `POST /users` register, `PUT /users/{id}/wallet/add?amount=100`, `PUT /users/{id}/wallet/deduct?amount=50`
- Order: `POST /orders` create, `GET /orders/{id}`, `PUT /orders/{id}/status?value=PAID`
- Payment: `POST /payments/pay` {userId, orderId, amount}
- Transaction: `GET /transactions/user/{userId}`

> This is a **starter** scaffold. Extend as needed for interviews or demos.


## 🔐 JWT Authentication (added)
- `POST /auth/login` (user-service) — provide `{"email":"<user-email>"}` to get a JWT token.
- Use `Authorization: Bearer <token>` header when calling secured endpoints (any endpoint except register/login).

## 📨 Redis-based SMS Simulation
- Redis is included in `docker-compose.yml` and runs on default port `6379`.
- Payment flow publishes an SMS message to Redis channel `sms` after success/failure.
- `notification-service` listens to `sms` channel and logs the message simulating an SMS send.
- In production, replace the SMS logger with Twilio/MSG91 integration.

## ▶️ Quick run (updated)
1) `docker-compose up -d` (starts MySQL, Zookeeper, Kafka, Redis)
2) Start services as before.
3) Example flow:
   - Create user: `POST http://localhost:8081/users` body `{ "name":"Alex","email":"a@x.com","walletBalance":500 }`
   - Login: `POST http://localhost:8081/auth/login` body `{ "email":"a@x.com" }` → get `token`
   - Create order: `POST http://localhost:8082/orders` `{ "userId":1, "amount":150 }`
   - Pay: `POST http://localhost:8083/payments/pay` `{ "userId":1, "orderId":1, "amount":150 }`
   - Watch notification-service logs — you'll see `>>> [SMS SENT] ...` printed.
