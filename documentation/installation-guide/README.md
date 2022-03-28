This section provides information about the installation of Order Service. 

A order service application built on Spring Framework. 
When running this application a command line runner will be executed in order to populate the in-memory postgres database with entries from a JSON file. 
The resource of order service is orders. 

## Technologies used:

  * Language: **Java**
  * Development Kit: **Java 11 SDK**
  * Framework: **Spring Framework**
  * Build Automation Tool: **Apache Maven**
  * Database: **Postgres 14.0**
  * Database: **H2 (In-Memory)**
  * ORM: **Hibernate**
  * Unit Testing Framework: **JUnit**
  * Service API: **REST**
  * IDE: **IntelliJ IDEA (Ultimate Edition)**
  * Security: **Keycloak-16.1.0**
  * Docker: **Docker desktop**
  * Documentation: **Swagger v3.0**
  
## Getting Started

These instructions will get you a copy of the project up and running on you local machine for development and testing purposes.

## Local Installation

### Prerequisites

#### Required

   * Java SDK 11
   * Postgres 14
   * Keycloak 16.1.0

## Hardware Requirements

This section describes the minimum and recommended available resources required for successful installation and work.

### Minimum Hardware Requirements

   * 600m CPU
   
   * 250Mi Memory
   
   * Xms=52
    
   * Xmx=104

### Recommended Resources

   * 1 CPU
   
   * 350Mi Memory
   
   * Xms=74
   
   * Xmx=146
    
## Ports

### Exposed Ports

   * 8484 - API
       
   * 8080 - Keycloak
       
   * 5432 - Postgres
   
## Environment

   * DB_URL: jdbc:postgresql://localhost:5432/order_service
       
   * DB_USERNAME: postgres
       
   * DB_PASSWORD: 1234
   
   * KEYCLOAK_URL: http://localhost:8080/auth
   
   * KEYCLOAK_REALM: my_realm
   
   * KEYCLOAK_RESOURCE: my_client
    
    
    
    
   
      