# Movie Ticket Booking System (Spring Boot)

A backend service for managing movie ticket bookings, built with Spring Boot, JPA (Hibernate), PostgreSQL, and Stripe for payments.

This repository contains the server-side APIs for creating bookings, managing theatres, shows and seats, and processing payments via Stripe, including a secure webhook endpoint.


## Tech Stack
- Language: Java 21
- Framework: Spring Boot 3.2 (Web, Data JPA, Validation)
- Database: PostgreSQL
- ORM: Hibernate (via Spring Data JPA)
- Payments: Stripe (stripe-java)
- Build & Package Manager: Maven (with Maven Wrapper `mvnw` / `mvnw.cmd`)
- Logging: SLF4J (via Spring Boot defaults)
- Utilities: Gson, Lombok


## Entry Point
- Main class: `com.manikanda.movie_ticket_booking_system.MovieTicketBookingSystemApplication`
- Starts an embedded server (default Tomcat) and exposes REST APIs.


## Requirements
- Java 21 (JDK 21)
- Maven (optional, wrapper is included)
- PostgreSQL database (running locally or accessible remotely)
- Stripe account (for API keys and webhook signing secret)


## Getting Started

### 1) Clone the repository
```
git clone <this-repo-url>
cd movie-ticket-booking-system
```

### 2) Configure environment
The file `src/main/resources/application.properties` currently contains hard-coded local configuration values (database, Stripe keys). For security and portability, prefer externalizing these values to environment variables or a non-committed `application-local.properties` profile.

Environment variables recommended:
- `SPRING_DATASOURCE_URL` — e.g., `jdbc:postgresql://localhost:5433/Ticket_Booking_System`
- `SPRING_DATASOURCE_USERNAME` — e.g., `postgres`
- `SPRING_DATASOURCE_PASSWORD` — e.g., `*****`
- `STRIPE_API_KEY` — your Stripe secret key (e.g., `sk_test_...`)
- `STRIPE_WEBHOOK_SECRET` — your Stripe webhook signing secret (`whsec_...`)

How to bind them:
- Either set OS environment variables with these names (Spring Boot recognizes the `SPRING_*` keys automatically).
- Or create `application-local.properties` and run with `--spring.profiles.active=local`.

Current default configuration (for reference only; do NOT commit real secrets):
```
spring.datasource.url=jdbc:postgresql://localhost:5433/Ticket_Booking_System
spring.datasource.username=postgres
spring.datasource.password=root123
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
stripe.api.key=sk_test_...
stripe.webhook.secret=whsec_...
```

TODO: Replace the secrets in `application.properties` with placeholders or profile-specific overrides and document secure setup in detail.

### 3) Database setup
- Create a PostgreSQL database named `Ticket_Booking_System` (or update the URL accordingly).
- Ensure the configured user has privileges to create/alter tables (JPA `ddl-auto=update` is enabled by default).


## Build and Run

Using Maven Wrapper on Windows (PowerShell or CMD):
- Build: `mvnw.cmd clean package`
- Run: `mvnw.cmd spring-boot:run`

Using Maven Wrapper on macOS/Linux:
- Build: `./mvnw clean package`
- Run: `./mvnw spring-boot:run`

You can also run the compiled jar (after packaging):
```
java -jar target/movie-ticket-booking-system-0.0.1-SNAPSHOT.jar
```

Optional profile example:
```
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
```


## Scripts and Common Commands
- `mvnw.cmd clean` — clean build outputs (Windows)
- `mvnw.cmd test` — run tests (Windows)
- `mvnw.cmd spring-boot:run` — run the application (Windows)
- `./mvnw clean`, `./mvnw test`, `./mvnw spring-boot:run` — macOS/Linux equivalents


## API Endpoints (selected)
Only endpoints present in the codebase are listed here. More controllers exist in `src/main/java/.../controller`. Avoid guessing unknowns.

- Payments
  - `POST /payments/create` — create a payment for a booking
  - `GET /payments/{paymentId}` — get payment by id
  - `GET /payments/transaction/{transactionId}` — get payment by transaction id
  - `GET /payments/booking/{bookingId}` — get payment by booking id
  - `POST /payments/{paymentId}/confirm` — confirm a payment
  - `POST /payments/{paymentId}/refund?refundAmount={amount}` — refund a payment

- Stripe Webhook
  - `POST /api/stripe/webhook` — handles `checkout.session.completed` events; verifies signature using `STRIPE_WEBHOOK_SECRET` and updates payment/booking when `payment_status = paid`.

TODO: Document remaining controllers and their endpoints (e.g., bookings, theatres, screens, seats, shows) with request/response schemas.


## Stripe Integration
- Secret key is injected from `stripe.api.key` (suggest externalizing to `STRIPE_API_KEY`).
- Webhook signature verified using `stripe.webhook.secret` (suggest externalizing to `STRIPE_WEBHOOK_SECRET`).
- Ensure your local server is reachable by Stripe (use `stripe listen` or `ngrok`) and forward events to `http://localhost:8080/api/stripe/webhook`.

Example (Stripe CLI):
```
stripe listen --events checkout.session.completed --forward-to localhost:8080/api/stripe/webhook
```


## Testing
- Unit/Context test entry: `src/test/java/com/manikanda/movie_ticket_booking_system/MovieTicketBookingSystemApplicationTests.java`
- Run tests:
  - Windows: `mvnw.cmd test`
  - macOS/Linux: `./mvnw test`

TODO: Add more unit and integration tests for controllers, services, and repositories.


## Project Structure
```
D:/Full_Stack_Projects/movie-ticket-booking-system
├─ pom.xml
├─ mvnw / mvnw.cmd
├─ HELP.md
├─ src
│  ├─ main
│  │  ├─ java
│  │  │  └─ com
│  │  │     └─ manikanda
│  │  │        └─ movie_ticket_booking_system
│  │  │           ├─ MovieTicketBookingSystemApplication.java   # Entry point
│  │  │           ├─ controller/                                # REST controllers (payments, webhook, etc.)
│  │  │           ├─ services/                                  # Business logic
│  │  │           ├─ repo/                                      # Spring Data repositories
│  │  │           ├─ entity/                                    # JPA entities
│  │  │           └─ ...
│  │  └─ resources
│  │     ├─ application.properties                              # App configuration (DB, Stripe, JPA)
│  │     ├─ static/                                             # Static assets (if used)
│  │     └─ templates/                                          # Server-side templates (if used)
│  └─ test
│     └─ java
│        └─ com
│           └─ manikanda
│              └─ movie_ticket_booking_system
│                 └─ MovieTicketBookingSystemApplicationTests.java
└─ target/                                                     # Build outputs
```


## Configuration Reference
Key dependencies from `pom.xml`:
- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `org.postgresql:postgresql` (runtime)
- `com.stripe:stripe-java`
- `com.google.code.gson:gson`
- `org.projectlombok:lombok`
- `spring-boot-starter-test` (test scope)

Java/Maven settings:
- Java version: 21 (maven-compiler-plugin)
- Spring Boot Maven Plugin enabled


## Notes and TODOs
- TODO: Replace hard-coded secrets in `application.properties` with environment variables or secure profiles.
- TODO: Add API documentation (e.g., OpenAPI/Swagger) and endpoint examples.
- TODO: Provide database migrations (e.g., Flyway/Liquibase) instead of relying on `ddl-auto=update`.
- TODO: Add integration tests and CI configuration (GitHub Actions / Jenkins) as needed.
- TODO: Document authentication/authorization if/when added. Currently no auth layer is documented.


## License
No explicit license file is present in this repository.

- TODO: Choose and add a LICENSE (e.g., MIT, Apache-2.0). Until then, the project should be treated as All Rights Reserved by default.


## Additional Resources
- See `HELP.md` for general Spring Boot + Maven references generated by the initializer.
