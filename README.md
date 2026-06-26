# Video Game Store E-Commerce Platform

A full-stack web application designed for a seamless video game retail experience. This project utilizes a decoupled architecture, featuring a REST API built with Java and Spring Boot (MVC Pattern) on the backend, paired with a server-side rendered frontend powered by Nuxt.js.

## Features

### User Management & Authentication
* **Account Registration:** Secure user sign-up.
* **Session Management:** Account login and authentication flow.

### Shopping Cart Dynamics
* **Add to Cart:** Browse the catalog and add games to an active cart.
* **Modify Quantity:** Dynamically update the item count of cart selections.
* **Remove Items:** Delete individual games from the shopping cart.
* **Clear Cart:** Empty the entire cart in a single action.

### Order Processing
* **Checkout Flow:** Process the active cart and finalize the order.

---

## Tech Stack

* **Frontend:** Nuxt.js (Vue), Node.js, npm
* **Backend:** Java, Spring Boot (MVC)
* **Data Access:** Spring Data JPA / Hibernate
* **Database:** MySQL

---

## Getting Started

### Prerequisites
Make sure you have the following installed on your machine:
* [Node.js](https://nodejs.org/) (v16+)
* [Java JDK](https://www.oracle.com/java/technologies/downloads/) (v17+)
* [MySQL Server](https://dev.mysql.com/downloads/)



### 2. Backend Setup (Spring Boot)

Open a terminal, navigate to your backend directory, and boot the API:

```bash
# Navigate into the backend directory
cd backend

# Run the application (using the included Maven Wrapper)
./mvnw spring-boot:run
```
*The Spring Boot server will start on `http://localhost:8080`.*

### 3. Frontend Setup (Nuxt.js)

Open a **second terminal window**, navigate to the frontend directory, and start the UI:

```bash
# Navigate into the frontend directory
cd frontend

# Install dependencies
npm install

# Launch the Nuxt development server
npm run dev
```
*The web application will be live at `http://localhost:3000`.*

---

