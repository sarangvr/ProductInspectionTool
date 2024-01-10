Product Inspection Tool

Overview
The Product Inspection Tool is a software application designed to manage and inspect various products across different categories. It allows users to perform CRUD (Create, Read, Update, Delete) operations on products, track quality metrics, and conduct inspections.

Features
List All Products: View a list of all products with their details.
Add New Product: Add a new product with relevant details such as name, category, price, and quantity.
Edit Product: Modify existing product details, including category-specific parameters.
Delete Product: Remove a product from the system, along with associated category-specific data.
View Category Details: Retrieve detailed information specific to a product's category.
Quality Inspection: Conduct quality inspections, providing comments, inspector details, and the inspection result.

Technologies Used:
Java 8: The backend of the application is developed in Java.
Front-end: HTML6, CSS, JavaScript, Bootstap
Spring Boot: Utilized for building and managing the application, including dependency injection.
Hibernate: ORM (Object-Relational Mapping) tool for data access and persistence.
SLF4J and Logback: Logging frameworks for capturing and managing application logs.

Project Structure:
The project is organized into packages, each representing a specific aspect of the application, such as entities, repositories, services, and utility classes.
com.ensat.services: Contains the service implementation for managing products and inspections.
com.ensat.category.entities: Entity classes representing different product categories.
com.ensat.category.repositories: Repositories for data access related to product categories.
com.ensat.entities: Entities related to product details, inspections, and quality metrics.
com.ensat.repositories: Repositories for general data access.
com.ensat.utility: Utility classes for various functions.

Getting Started:
Clone the repository to your local machine.
Open the project in your preferred Java IDE.
Set up a database connection in the application.properties file.
Run the application.

Usage:
Access the application through the specified URL or endpoint.
Use the provided API or user interface to perform the desired actions (list, add, edit, delete, inspect).

Important Notes:
Ensure that the necessary dependencies and libraries are installed before running the application.
Database configuration and connection details can be modified in the application.properties file.

Contributors:
Sarang VR

License:
This project is licensed under the MIT License.

Feel free to contribute, report issues, or suggest improvements!
Login Screen:
![login](https://github.com/sarangvr/ProductInspectionTool/assets/84840340/1e9c5a25-1076-4933-bc0f-82429d5da26f)

Home Screen:
![Home](https://github.com/sarangvr/ProductInspectionTool/assets/84840340/2f443568-033b-42a6-94af-7d531621b122)

Products Screen:(Here you can Add,Edit and Delete)
![products](https://github.com/sarangvr/ProductInspectionTool/assets/84840340/e520d539-6e4b-43e4-b673-ab64cb75250b)

Quality Metric Screen(Here you can Inspect Auto, or manual)
![manual_inspect](https://github.com/sarangvr/ProductInspectionTool/assets/84840340/d262b934-53d1-4049-b0a3-dfa51dfbf7c6)

Reports Screen:(Shows the Pie Graph based on Quality of Products)
![reports](https://github.com/sarangvr/ProductInspectionTool/assets/84840340/ca5bc011-fe78-4474-bf01-a6f9d7e2cfd2)
