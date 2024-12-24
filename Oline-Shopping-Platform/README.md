# Online Shopping Platform - Full Backend and Frontend Integration Guide

This comprehensive guide outlines how to set up and integrate the backend of the **Online Shopping Platform** with a frontend built using Vite, React (TypeScript), and Redux for state management. It provides detailed instructions for backend deployment, frontend integration, and Redux setup.

---

## Table of Contents

1. [Backend Overview](#backend-overview)
2. [Backend Setup](#backend-setup)
3. [Frontend Overview](#frontend-overview)
4. [Frontend Integration with Backend](#frontend-integration-with-backend)
5. [Redux Implementation](#redux-implementation)
6. [Deployment Instructions](#deployment-instructions)
7. [Support](#support)

---

## Backend Overview

The backend of the Online Shopping Platform is a servlet-based Java application that handles user data, product management, orders, and reviews. It is deployed on a Java web server (e.g., Tomcat) and uses MySQL for data storage.

### Key Features:
- RESTful APIs for managing users, products, orders, and reviews.
- Secure interaction with the database using JDBC.
- Cross-Origin Resource Sharing (CORS) enabled for frontend communication.

---

## Backend Setup

### Prerequisites:
1. **Java 11+** installed.
2. **Apache Tomcat** or any servlet-compatible web server.
3. **MySQL** database.

### Steps:

1. **Clone the Backend Repository**:
   ```bash
   git clone https://github.com/yourusername/online-shopping-backend.git
   cd online-shopping-backend
   ```

2. **Database Setup**:
    - Create a MySQL database:
      ```sql
      CREATE DATABASE online_shopping;
      ```
    - Import the provided schema:
      ```sql
      USE online_shopping;
      SOURCE /path/to/schema.sql;
      ```

3. **Configure Database Connection**:
    - Update the `DBHelper.java` file with your database credentials:
      ```java
      public class DBHelper {
          public static Connection getConnection() throws SQLException {
              String url = "jdbc:mysql://localhost:3306/online_shopping";
              String username = "root";
              String password = "yourpassword";
              return DriverManager.getConnection(url, username, password);
          }
      }
      ```

4. **Build and Deploy the Application**:
    - Compile the project using your IDE (e.g., IntelliJ IDEA) or Maven.
    - Deploy the `.war` file to Tomcat’s `webapps` directory.

5. **Start the Server**:
    - Run Tomcat and access the backend APIs at:
      ```
      http://localhost:8080/online-shopping-backend
      ```

---

## Frontend Overview

The frontend is built using Vite, React (TypeScript), and Redux for state management. It connects to the backend via RESTful APIs to fetch and update data dynamically.

### Prerequisites:
1. **Node.js 16+**
2. **Vite** (installed globally):
   ```bash
   npm install -g create-vite
   ```

---

## Frontend Integration with Backend

### Steps to Integrate:

1. **Clone the Frontend Repository**:
   ```bash
   git clone https://github.com/yourusername/online-shopping-frontend.git
   cd online-shopping-frontend
   ```

2. **Install Dependencies**:
   ```bash
   npm install
   ```

3. **Configure API Base URL**:
    - Open `src/config/api.ts`:
      ```typescript
      export const BASE_URL = "http://localhost:8080/online-shopping-backend";
      ```

4. **Example API Calls**:
    - Fetch product details:
      ```typescript
      import axios from "axios";
      import { BASE_URL } from "../config/api";
 
      export const fetchProductDetails = async (productId: number) => {
          const response = await axios.get(`${BASE_URL}/productDetailsServlet?id=${productId}`);
          return response.data;
      };
      ```

5. **Run the Frontend**:
   ```bash
   npm run dev
   ```
    - Access the application at:
      ```
      http://localhost:5173
      ```

---

## Redux Implementation

Redux is used for state management to centralize and manage application-wide data such as user sessions, product lists, and cart details.

### Steps to Add Redux:

1. **Install Redux Toolkit**:
   ```bash
   npm install @reduxjs/toolkit react-redux
   ```

2. **Create the Redux Store**:
    - `src/redux/store.ts`:
      ```typescript
      import { configureStore } from "@reduxjs/toolkit";
      import productReducer from "./slices/productSlice";
 
      export const store = configureStore({
          reducer: {
              products: productReducer,
          },
      });
 
      export type RootState = ReturnType<typeof store.getState>;
      export type AppDispatch = typeof store.dispatch;
      ```

3. **Create a Slice**:
    - `src/redux/slices/productSlice.ts`:
      ```typescript
      import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
      import { fetchProductDetails } from "../../api/productApi";
 
      interface ProductState {
          product: any;
          loading: boolean;
          error: string | null;
      }
 
      const initialState: ProductState = {
          product: null,
          loading: false,
          error: null,
      };
 
      export const getProductDetails = createAsyncThunk(
          "products/getProductDetails",
          async (productId: number) => {
              const response = await fetchProductDetails(productId);
              return response;
          }
      );
 
      const productSlice = createSlice({
          name: "products",
          initialState,
          reducers: {},
          extraReducers: (builder) => {
              builder
                  .addCase(getProductDetails.pending, (state) => {
                      state.loading = true;
                      state.error = null;
                  })
                  .addCase(getProductDetails.fulfilled, (state, action) => {
                      state.loading = false;
                      state.product = action.payload;
                  })
                  .addCase(getProductDetails.rejected, (state, action) => {
                      state.loading = false;
                      state.error = action.error.message || "Failed to fetch product details";
                  });
          },
      });
 
      export default productSlice.reducer;
      ```

4. **Provide the Store**:
    - Wrap your application in the `Provider` component in `main.tsx`:
      ```typescript
      import React from "react";
      import ReactDOM from "react-dom/client";
      import { Provider } from "react-redux";
      import { store } from "./redux/store";
      import App from "./App";
 
      ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
          <React.StrictMode>
              <Provider store={store}>
                  <App />
              </Provider>
          </React.StrictMode>
      );
      ```

5. **Use Redux in Components**:
   ```typescript
   import React, { useEffect } from "react";
   import { useDispatch, useSelector } from "react-redux";
   import { getProductDetails } from "../redux/slices/productSlice";
   import { RootState } from "../redux/store";

   const ProductDetails = ({ productId }: { productId: number }) => {
       const dispatch = useDispatch();
       const { product, loading, error } = useSelector((state: RootState) => state.products);

       useEffect(() => {
           dispatch(getProductDetails(productId));
       }, [dispatch, productId]);

       if (loading) return <p>Loading...</p>;
       if (error) return <p>Error: {error}</p>;

       return (
           <div>
               <h1>{product.name}</h1>
               <p>{product.description}</p>
           </div>
       );
   };

   export default ProductDetails;
   ```

---

## Deployment Instructions

1. **Backend Deployment**:
    - Deploy the backend on a cloud server or local environment with a public URL.

2. **Frontend Deployment**:
    - Build the production bundle:
      ```bash
      npm run build
      ```
    - Deploy the `dist` folder to a static hosting service (e.g., Netlify, Vercel).

---

## Support

For backend issues, refer to the backend repository’s README of each part. For frontend issues, contact the frontend team or refer to this guide.

