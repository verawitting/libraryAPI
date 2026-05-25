# Library API – REST Optimization & Security Lab

## Overview

This project is a REST API for a library system built with Spring Boot.  
It demonstrates security, performance optimisation, and secret management using modern backend techniques.


## Features Implemented

### Security
- Spring Security with role-based access control (USER / ADMIN)
- HTTP Basic authentication
- CORS configuration for frontend communication
- Rate limiting using Bucket4j (IP-based protection)


### Performance Optimisation
- Redis caching for `GET /books/{id}`
- Spring Cache abstraction
- Pagination support using Pageable for list endpoints


### Secret Management
- Vault integration
- Database password retrieved at runtime
- No sensitive credentials stored in `application.properties`


## Performance Testing

### Method
Response times were measured using `curl` for repeated requests to GET /api/v1/books/{id}. A manual benchmark was performed by calling the endpoint multiple times in sequence.

A simulated delay (`Thread.sleep(2000)`) was added in the service layer to better highlight caching effects during testing.


### Results

#### Without cache (first request / cold execution)

- Request 1: **0.337 s**
- Request 5 (after reset / uncached path): **0.274 s**

These requests triggered the full service flow including repository access and simulated delay.


#### With cache enabled (subsequent requests)

- Request 2: **0.011 s**
- Request 3: **0.012 s**
- Request 4: **0.007 s**
- Request 6: **0.014 s**

These requests were served from cache (Spring Cache layer), bypassing database access and service delay.


### Cache behaviour logs

Spring confirmed cache hits via:


Cache entry for key '1' found in cache(s) [books]


This indicates that cached values were successfully retrieved instead of executing repository calls.


### Conclusion

Caching significantly improves read performance by avoiding repeated database access and expensive service execution. Even with a simulated delay, cached responses are returned almost instantly.


## How to Run

### 1. Start Vault (dev mode)

vault server -dev


### 2. Set Vault token
Add token to environment variable:

VAULT_TOKEN=your-token


### 3. Start Redis

redis-server


### 4. Run Spring Boot

./mvnw spring-boot:run


## Tech Stack
- Spring Boot
- Spring Security
- Spring Data JPA
- Redis
- Vault
- Bucket4j
- H2 Database
