# Frontend Developer Guide: Integrating Login Functionality

This guide explains how to integrate the backend login functionality into the React frontend application. Follow the steps below to set up and use the login API.

---

## API Endpoint Details

### **Login API**
- **Endpoint**: `http://localhost:8080/LogIn`
- **Method**: `POST`
- **Headers**:
    - `Content-Type: application/json`
- **Request Body**:
  ```json
  {
      "email": "user@example.com",
      "password": "password123"
  }
  ```
- **Response**:
    - Success:
      ```json
      {
          "success": true
      }
      ```
    - Failure:
      ```json
      {
          "success": false
      }
      ```
    - Invalid JSON:
      ```json
      {
          "error": "Invalid JSON format"
      }
      ```

---

## Frontend Implementation Steps

### 1. **Create a Login Form**

Use a React component to create a login form with fields for email and password.

```jsx
import React, { useState } from 'react';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    const loginData = { email, password };

    try {
      const response = await fetch('http://localhost:8080/LogIn', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(loginData),
      });

      const result = await response.json();

      if (result.success) {
        setMessage('Login successful!');
      } else {
        setMessage('Invalid email or password.');
      }
    } catch (error) {
      console.error('Error during login:', error);
      setMessage('An error occurred. Please try again later.');
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default Login;
```

### 2. **Configure CORS**
Ensure the backend server allows requests from `http://localhost:5173` (the React development server). This has already been implemented in the backend servlet using:
```java
response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
response.setHeader("Access-Control-Allow-Headers", "Content-Type");
```

### 3. **Testing the Login**
- Start the backend server (`localhost:8080`).
- Start the React development server (`localhost:5173`).
- Navigate to the login page and test with valid and invalid credentials.

### 4. **Handle User Authentication State**
- Use a global state management library (like Redux or Context API) to store the user authentication state after a successful login.
- Redirect the user to the appropriate page based on their role (`customer`, `seller`, or `manager`).

Example:
```jsx
if (result.success) {
  navigate('/dashboard'); // Redirect to dashboard after successful login
}
```

---

## Additional Notes

- **Password Security**: Ensure that passwords are hashed on the backend for security.
- **Error Handling**: Display appropriate messages for network errors or server issues.
- **Validation**: Add client-side validation for email and password fields before submitting the form.

---

By following this guide, you can successfully integrate the login functionality into the React frontend application.
