# Online Shopping Platform - Frontend Integration Guide

This document provides a step-by-step guide for React developers to set up and integrate the backend with the React frontend of the **Online Shopping Platform**.

---

## **Prerequisites**

1. **Node.js**: Ensure Node.js (v16 or above) is installed.
2. **Vite**: Frontend is built using Vite. Install Vite globally if not already installed:
   ```bash
   npm install -g create-vite
   ```
3. **Backend**: Backend should be deployed and running. Ensure the backend base URL is accessible (e.g., `http://localhost:8080`).

---

## **Project Setup**

1. **Clone the Frontend Repository**:
   ```bash
   git clone https://github.com/Mohammed-App-creater/Advance-java-Project.git
   cd Advance-java-Project
   ```

2. **Install Dependencies**:  
   Run the following command in the root directory to install project dependencies:
   ```bash
   npm install
   ```

3. **Configure the Backend API URL**:
    - Open the `src/config/api.js` file (create if it doesn't exist).
    - Add the backend base URL:
      ```javascript
      export const BASE_URL = "http://localhost:8080"; // Replace with the deployed backend URL
      ```

---

## **Integration Points**

### 1. **User Profile**
- **Backend Endpoint**:  
  `GET /user/profile`  
  `PUT /user/update`
- **Frontend Usage**:  
  Use Axios or Fetch to call the endpoints.  
  Example:
  ```javascript
  import axios from "axios";
  import { BASE_URL } from "../config/api";

  // Fetch user profile
  const fetchUserProfile = async () => {
    const response = await axios.get(`${BASE_URL}/user/profile`);
    console.log(response.data);
  };

  // Update user profile
  const updateUserProfile = async (userData) => {
    const response = await axios.put(`${BASE_URL}/user/update`, userData);
    console.log(response.data);
  };
  ```

### 2. **Products**
- **Backend Endpoint**:  
  `GET /products`  
  `GET /products/:id`
- **Frontend Usage**:  
  Example:
  ```javascript
  const fetchProducts = async () => {
    const response = await axios.get(`${BASE_URL}/products`);
    return response.data;
  };
  ```

### 3. **CORS Support**
The backend is configured to support CORS. Ensure the `BASE_URL` in the React app matches the backend URL.

---

## **Starting the Development Server**

1. Run the development server:
   ```bash
   npm run dev
   ```
2. Open your browser and navigate to:  
   `http://localhost:5173`

---

## **Folder Structure**

- **src/components**: Reusable UI components (e.g., buttons, forms).
- **src/pages**: Pages corresponding to customer-facing features (e.g., profile, product details).
- **src/redux**: State management with Redux.
- **src/utils**: Utility functions like API helpers.
- **src/config**: Configuration files (e.g., API base URL).

---

## **Example Requests**

1. **Fetching User Profile**:
   ```javascript
   useEffect(() => {
     const fetchProfile = async () => {
       const res = await axios.get(`${BASE_URL}/user/profile`);
       console.log(res.data);
     };
     fetchProfile();
   }, []);
   ```

2. **Updating User Profile**:
   ```javascript
   const handleUpdate = async () => {
     const userData = { username: "JohnDoe", email: "john@example.com" };
     await axios.put(`${BASE_URL}/user/update`, userData);
     alert("Profile updated successfully!");
   };
   ```

---

## **Deployment**

1. Build the production bundle:
   ```bash
   npm run build
   ```
2. Serve the `dist` folder using any static file server or deploy to platforms like Vercel or Netlify.

---

## **Support**

For backend issues, contact the backend team or refer to their README for debugging and endpoint details.

---

