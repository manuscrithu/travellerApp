# Smart Travel Booking Platform – Microservices Assignment

This project contains 6 Spring Boot microservices communicating using WebClient and Feign Client.

## 📌 Microservices
- **User Service** – Validates user (WebClient call from Booking)
- **Flight Service** – Checked via Feign Client
- **Hotel Service** – Checked via Feign Client
- **Booking Service** – Main orchestrator
- **Payment Service** – Called via WebClient
- **Notification Service** – Called via WebClient

## ⚙️ Technology Stack
- Java 17+
- Spring Boot 3+
- Spring Data JPA + H2
- Spring Web
- Spring WebFlux (WebClient)
- Spring Cloud OpenFeign
- Lombok

## 🔗 Service Ports
| Service | Port |
|--------|------|
| user-service | 8081 |
| flight-service | 8082 |
| hotel-service | 8083 |
| booking-service | 8084 |
| payment-service | 8085 |
| notification-service | 8086 |

## 🧩 Communication Flow
1. Booking → User (WebClient)
2. Booking → Flight (Feign)
3. Booking → Hotel (Feign)
4. Booking → Notification (WebClient)
5. Payment → Booking (WebClient)

## 🛠️ Booking Request Example
```json
{
  "userId": 1,
  "flightId": 200,
  "hotelId": 55,
  "travelDate": "2025-01-10"
}
```

## ▶️ Running the Project
Start each service individually using:

```
mvn spring-boot:run
```

Make sure ports match those in `application.properties`.

## 📬 Postman Collection
Import the file:

**smart-travel-postman-collection.json**

into Postman to test all endpoints.

