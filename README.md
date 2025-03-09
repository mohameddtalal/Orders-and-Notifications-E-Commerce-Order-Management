🛒 E-Commerce Order Management System
📌 Project Overview
This project is an E-Commerce Order Management System developed using Spring Boot and Java. It enables customers to create accounts, browse products, place simple and compound orders, and receive notifications via email or SMS. The system follows well-structured design patterns such as Strategy, Abstract Factory, Composite, and Template Patterns to enhance maintainability and flexibility.
🔑 Features
🏦 Customer Management
Register an account with a specific balance.
Check if an account exists and retrieve customer details.
Fetch all registered customers in the system.
📦 Product Management
Create new products with name, vendor, price, and category.
Retrieve all available products in the system.
Find a product by name for easy lookup.
🛍️ Order Processing
Simple Orders: Customers can place an order for one or multiple products.
Compound Orders: Customers can place multiple orders for themselves or friends.
Retrieve orders by ID or fetch all placed orders.
📩 Notification System
Email Notifications: Customers receive an email confirmation of their purchases.
SMS Notifications: Customers receive a text message with purchase details.
Retrieve all pending notifications in the queue.
🏗️ Design Patterns Used
Strategy Pattern (Database Handling) – Allows switching between different database implementations.
Abstract Factory Pattern (Notification System) – Generates email or SMS notifications dynamically.
Composite Pattern (Order System) – Handles simple and compound orders efficiently.
Template Pattern (Order Processing) – Ensures consistent order placement procedures.
🛠️ Technologies Used
Backend: Java, Spring Boot
Database: In-Memory Database (MemoryDB)
API Testing: Postman Collection (See Below)
IDE: IntelliJ IDEA / Eclipse / VS Code
🚀 Getting Started
📌 Prerequisites
Install Java 17+
Install Spring Boot 3+
Install Postman (for API testing)
