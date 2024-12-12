# Ticket Selling System

This project is a multi-threaded Ticket Selling System implemented with Java and Spring Boot. It simulates a real-time ticketing platform where customers can purchase tickets, and vendors supply tickets to a shared pool. The system handles concurrency and synchronization to ensure data integrity and smooth operation.

## Features

- **Vendor and Customer Simulation:** Vendors release tickets at a defined rate, and customers retrieve tickets from a shared pool.
- **Thread Management:** Multi-threaded architecture for vendors and customers.
- **Configurable Parameters:** The system allows configuration of the total tickets, ticket release and retrieval rates, maximum ticket pool capacity, and the number of vendors and customers.
- **Real-Time Ticket Pool:** Synchronized access to the ticket pool to ensure consistent data.
- **REST API:** A Spring Boot-based backend with endpoints for system configuration, management, and control.
- **Graceful Shutdown:** Allows stopping the system safely by interrupting threads and signaling production completion.

## Technologies Used

- **Backend Framework:** Spring Boot
- **Programming Language:** Java
- **Database:** MySQL
- **Build Tool:** Maven
- **Concurrency Handling:** Java threads and synchronized blocks

## Project Structure

## Endpoints

### 1. Configure the System
- **URL:** `/api/ticket-selling/config`
- **Method:** POST
- **Parameters:**
  - `totalTickets`: Total number of tickets available.
  - `ticketReleaseRate`: Rate at which tickets are released by vendors.
  - `ticketRetrievalRate`: Rate at which tickets are retrieved by customers.
  - `maxTicketCapacity`: Maximum capacity of the ticket pool.
- **Response:**
  - `"Config Success"`

### 2. Set Customers
- **URL:** `/api/ticket-selling/set-customers`
- **Method:** POST
- **Parameters:**
  - `totalCus`: Total number of customers.
- **Response:**
  - `"Customers set"`

### 3. Set Vendors
- **URL:** `/api/ticket-selling/set-vendors`
- **Method:** POST
- **Parameters:**
  - `totalVen`: Total number of vendors.
- **Response:**
  - `"Vendors set"`

### 4. Start the System
- **URL:** `/api/ticket-selling/start`
- **Method:** POST
- **Response:**
  - `"System Starting"`

### 5. Stop the System
- **URL:** `/api/ticket-selling/stop`
- **Method:** POST
- **Response:**
  - `"System Stopped"`

## How It Works

1. **Configuration:**
   - Use the `/config` endpoint to set up the initial system parameters.

2. **Customer and Vendor Setup:**
   - Use `/set-customers` and `/set-vendors` to define the number of customers and vendors.

3. **Start the System:**
   - Trigger the `/start` endpoint to begin ticket production and consumption.
   - Vendors add tickets to the shared pool, and customers retrieve tickets.

4. **Stop the System:**
   - Use the `/stop` endpoint to gracefully terminate the system.

## Database Configuration

The system uses MySQL as its database. Ensure the following configurations in the `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/PSRVlog?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=1234
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
server.port=8081

