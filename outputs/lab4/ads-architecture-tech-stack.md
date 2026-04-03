# Lab 4: ADS Software Solution Architecture and Tech Stack

## Chosen Architecture Style

- 3-tier web application architecture
- Layered architecture inside the application server
- Modular monolith deployment style

## Chosen Tech Stack

- Client Tier:
  - Angular SPA
  - TypeScript
  - HTML5
  - CSS3
  - Bootstrap
- Application / Middle Tier:
  - Spring Boot
  - Spring MVC REST controllers
  - Spring Security
  - Spring service layer components
  - Spring Data JPA
  - Hibernate ORM
- Data / Integration Tier:
  - PostgreSQL
  - SMTP email service for appointment confirmations

## Main Logical Components

- Angular client UI for:
  - Office Manager portal
  - Dentist portal
  - Patient portal
- REST API / presentation layer
- Business services:
  - Patient Management
  - Dentist Management
  - Appointment Scheduling
  - Billing and Outstanding Balance Checks
  - Notification / Email Service
- Persistence layer:
  - Spring Data JPA repositories
  - Hibernate ORM
- PostgreSQL relational database
- External SMTP email gateway

## Why This Fits ADS

- ADS is one bounded business system with shared data and clear business modules.
- A layered modular monolith is simpler to deploy and maintain than microservices for this scope.
- Angular plus Spring Boot supports a clean separation between UI, REST API, business logic, and persistence.
- PostgreSQL is a strong fit for structured business data such as dentists, patients, appointments, surgeries, and bills.
- SMTP integration supports the required appointment confirmation emails.
