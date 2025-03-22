# Fever Demo

## 📌 Description
Fever Demo is a REST service that offers available event ticket selling all over the world.

## 🛠️ Technologies
- **Java 17**
- **Spring Boot 3.2.10**
- **Spring WebFlux**
- **MongoDB** (Reactive)
- **OpenAPI Generator**
- **Artilley**
- **WebClient** (Reactive)

## 🚀 Installation and configuration
### Requirements
- Have **Java 17** and **Maven** installed
- Have **Postman** or **Newman** installed for E2E tests
- Have **Artillery** installed for load tests

### Execution steps
1. Clone the repository
2. Build and launch the application:
   ```
   make
   ```
3. API will be available in: `http://yourHost:8080/search?starts_at=yyyy-mm-dd&ends_at=yyyy-mm-dd` (localhost in local environment)

### Testing steps
1. Launch thr application:
   ```
   make
   ```
2. Launch Postman collection for manual testing E2E:
3. Or launch them automatically with Newman:
   ```
   make test-e2e
   ```
4. Launch Artillery for load testing ramp scenario:
   ```
   load-test-ramp
   ```
5. Launch Artillery for load testing constant scenario:
   ```
   load-test-constant
   ```   
5. Launch Artillery for load testing smoke scenario:
   ```
   load-test-smoke
   ```    

## 🏗️ Architecture
It follows a reactive hexagonal architecture, with the following layers:
- **Infrastructure**: Contains the REST controller, external webclient, MongoDB functionalities and exceptions
- **Application**: Contains the use cases and the dtos classes.
- **Domain**: Contains the domain classes.

## ⚡ Design principles
- **High scalability**: The application is designed to be high scalable. It uses a reactive architecture to handle high loads.
- **High availability**: The application is designed to be resilient against external and internal errors. It uses circuit breakers to protect the system from failures, has default responses and backup stored information.
- **Performance**: The application is designed to be high performance. It uses non-blocking I/O, reactive programming and MongoDB to handle high loads.
- **Maintainability**: The application is designed to be easily maintainable. It uses a clean architecture (hexagonal architecture) with separate layers for different concerns.
- **Testable**: The application is designed to be easily testable. It uses unit tests, end-to-end tests and load tests to ensure the quality of the code.
- **Documentable**: The application is designed to be easily documentable. It uses OpenAPI to generate the API documentation.


## 🔮 Future improvements
- **High scalability**.
  - **Reactive input**: implement REST reactive input. Due to time, it is implemented nowadays. It should use **Mono<ResponseEntity<EventList>>** and define reactive true in OpenAPI specification (<configOptions> openapitools plugin in pom)
- **High availability**: 
  - Use **data replication** in MongoDB
  - Has **relational database** as a more persistent backup
  - **Resilience4j circuit breaker** : make functional circuit breaker because, due to time it is not working nowadays.
-  **Performance**: 
   - **MongoDB** : use sharding
   - **MongoDB** : create indexes
   - **Redis Cache** : use Redis as cache for non-relation database response
   - **External provider** : the external REST query is the biggest bottleneck.
- **Testable**:
  - **Integration tests**: implement integration tests with **Testcontainers** or similar for MongoDB
  - **Accepting tests**: implement accepting tests with **Cucumber** or similar
- **Documentable**: 
  - **OpenAPI**: improve the OpenAPI documentation with example values, default values, etc.

## 📬 Contributions and contact
- In order to contribute, get a **brach** from repository and open a **pull request**.
- Contact: `miguelcabezaspuerto@gmail.com`

It can always be improved but I hope you like it! 🚀

