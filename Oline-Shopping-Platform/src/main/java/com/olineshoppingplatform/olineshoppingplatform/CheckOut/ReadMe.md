
# Online Shopping Platform

This is a simple online shopping platform where customers can browse products, add them to their shopping cart, place orders, and manage their profiles. Sellers can list their products, and managers have administrative access to manage the platform.

## Features

- **Customer Features**:
    - Browse products by categories.
    - Add products to the cart.
    - View detailed product information, including price and seller info.
    - Place orders and track shipments.
    - Manage profile and shipping address.

- **Seller Features**:
    - Add and manage products.
    - View and manage orders.

- **Manager Features**:
    - Manage users (customers, sellers).
    - Manage orders and products.

## Technologies Used

- **Frontend**:
    - **React**: For building the user interface.
    - **React Router**: For routing.
    - **Redux**: For state management.
    - **Tailwind CSS** & **ShadCN UI**: For styling.
    - **Vite**: For faster build and development.

- **Backend**:
    - **Java Servlet**: For handling business logic.
    - **MySQL**: For database management.
    - **JDBC**: For database connectivity.

- **Database**:
    - MySQL database to store user, product, order, and payment information.

## Setup and Installation

### Prerequisites

- JDK 8 or later.
- MySQL database.
- Apache Tomcat or similar servlet container.

### Clone the repository

```bash
git clone https://github.com/yourusername/online-shopping-platform.git
cd online-shopping-platform
```

### Setting up the Database

1. Import the provided SQL schema into your MySQL database.
2. Update the database connection details in your project (e.g., in the `DB` connection class or `application.properties` file).

### Build and Run the Project

1. **Backend (Servlets)**:
    - Compile the Java code using your IDE or build tool.
    - Deploy the servlet-based application on a servlet container (e.g., Apache Tomcat).

2. **Frontend (Vite React)**:
    - Navigate to the frontend directory:
      ```bash
      cd frontend
      ```
    - Install the dependencies:
      ```bash
      npm install
      ```
    - Start the Vite development server:
      ```bash
      npm run dev
      ```

### Running the Application

- Open your browser and visit the frontend URL (usually `http://localhost:5173` for Vite).
- Access the backend via the servlet container URL (e.g., `http://localhost:8080`).

## Folder Structure

```
/online-shopping-platform
├── /backend
│   ├── /src
│   ├── /lib
│   ├── /webapp
│   └── pom.xml (for Maven projects)
│
├── /frontend
│   ├── /src
│   ├── /public
│   └── package.json
│
└── /sql
    ├── schema.sql
```

## Contributing

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Make your changes and commit them.
4. Push to your forked repository.
5. Open a pull request with a description of the changes you made.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Thanks to the open-source community for the libraries and tools used in this project.
- Special thanks to the instructors and peers for their feedback and support.

---

### Key Sections to Adjust:

- **Project Overview**: No changes needed; just highlight the use of **Vite** for faster React development.
- **Technologies Used**: Added **Vite** to the frontend section.
- **Setup Instructions**: Updated to include instructions for using **Vite**.
- **Folder Structure**: Ensure it's clear that **frontend** now uses **Vite React**.
- **Contributing**: Same process applies, no need for modification.

