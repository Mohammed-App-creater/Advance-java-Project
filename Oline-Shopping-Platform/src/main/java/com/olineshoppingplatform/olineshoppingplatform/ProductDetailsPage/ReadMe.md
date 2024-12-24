 

# Online Shopping Platform - Backend API

This is the backend for an online shopping platform where users can view product details and reviews. It provides a set of RESTful APIs that a frontend built with **Vite** and **React** (with **Redux** for state management) can consume to display product information and customer reviews.

## Table of Contents

- [Technologies Used](#technologies-used)
- [API Endpoints](#api-endpoints)
- [Frontend Integration](#frontend-integration)
- [Setup Instructions](#setup-instructions)
- [Database Schema](#database-schema)
- [License](#license)

## Technologies Used

- **Java 11** (or higher)
- **Servlets** for handling HTTP requests
- **JDBC** for interacting with MySQL
- **MySQL** for database management
- **Tomcat** or another Java web server for deploying the servlet-based backend
- **Vite** for frontend bundling
- **React** for building the user interface
- **Redux** for state management
- **Axios** or **Fetch** for API calls to the backend

## API Endpoints

### `GET /productDetailsServlet?id={productId}`

Fetches the details of a product and its associated reviews.

#### Request
- **URL**: `/productDetailsServlet?id={productId}`
- **Method**: GET
- **Query Parameters**:
    - `id`: The ID of the product to retrieve (required).

#### Response (JSON)
- **Product Information**: Contains product details like `productId`, `name`, `description`, `imageUrl`, `price`, `discount`, `categoryId`, `stock`, and `sellerId`.
- **Reviews**: A list of reviews associated with the product, including `id`, `rating`, `reviewText`, `customerId`, and `createdAt`.

##### Example Request:
```
GET http://localhost:8080/olineshoppingplatform/ProductDetailsServlet?id=1
```

##### Example Response:
```json
{
  "product": {
    "productId": 1,
    "name": "Smartphone",
    "description": "Latest model with all the advanced features.",
    "imageUrl": "https://example.com/images/smartphone.jpg",
    "price": 499.99,
    "discount": 10.00,
    "categoryId": 2,
    "stock": 25,
    "sellerId": 1
  },
  "reviews": [
    {
      "id": 101,
      "rating": 4.5,
      "reviewText": "Great phone with excellent battery life!",
      "customerId": 1,
      "createdAt": "2024-12-20T12:34:56"
    },
    {
      "id": 102,
      "rating": 3.0,
      "reviewText": "Good phone but overpriced for the features.",
      "customerId": 2,
      "createdAt": "2024-12-21T14:20:30"
    }
  ]
}
```

## Frontend Integration

To integrate this API with your React frontend, you can use `fetch` or `axios` to make GET requests to the backend. Below is an example of how to fetch and display product details and reviews in your React components.

### Frontend Setup

You can use **Vite** for bundling your React app and **Redux** for state management. If you haven't set up Vite with React and Redux yet, follow these instructions:

1. **Create a new Vite project with React**:
   ```bash
   npm create vite@latest my-app --template react
   cd my-app
   ```

2. **Install dependencies**:
   ```bash
   npm install redux react-redux axios
   ```

3. **Create the Redux Store and Actions**:

    - **store.js** (set up the Redux store):
      ```javascript
      import { createStore } from 'redux';
 
      const initialState = {
        product: null,
        reviews: [],
      };
 
      function productReducer(state = initialState, action) {
        switch (action.type) {
          case 'SET_PRODUCT_DETAILS':
            return { ...state, product: action.product };
          case 'SET_REVIEWS':
            return { ...state, reviews: action.reviews };
          default:
            return state;
        }
      }
 
      const store = createStore(productReducer);
 
      export default store;
      ```

    - **actions.js** (create Redux actions):
      ```javascript
      export const setProductDetails = (product) => ({
        type: 'SET_PRODUCT_DETAILS',
        product,
      });
 
      export const setReviews = (reviews) => ({
        type: 'SET_REVIEWS',
        reviews,
      });
      ```

4. **App.js** (main application file where Redux state is used):
   ```javascript
   import React, { useEffect } from "react";
   import { useDispatch, useSelector } from "react-redux";
   import { setProductDetails, setReviews } from "./actions";
   import axios from "axios";

   const ProductDetails = ({ productId }) => {
     const dispatch = useDispatch();
     const { product, reviews } = useSelector((state) => state);

     useEffect(() => {
       const fetchProductDetails = async () => {
         const response = await axios.get(`http://localhost:8080/olineshoppingplatform/ProductDetailsServlet?id=${productId}`);
         dispatch(setProductDetails(response.data.product));
         dispatch(setReviews(response.data.reviews));
       };

       fetchProductDetails();
     }, [productId, dispatch]);

     if (!product) {
       return <div>Loading...</div>;
     }

     return (
       <div>
         <h2>{product.name}</h2>
         <img src={product.imageUrl} alt={product.name} />
         <p>{product.description}</p>
         <p>Price: ${product.price}</p>
         <p>Discount: {product.discount}%</p>
         <p>Stock: {product.stock}</p>

         <h3>Reviews:</h3>
         <ul>
           {reviews.map((review) => (
             <li key={review.id}>
               <p>Rating: {review.rating}</p>
               <p>{review.reviewText}</p>
               <p>Reviewed by customer {review.customerId} on {new Date(review.createdAt).toLocaleString()}</p>
             </li>
           ))}
         </ul>
       </div>
     );
   };

   export default ProductDetails;
   ```

5. **index.js** (rendering the app and providing the Redux store):
   ```javascript
   import React from 'react';
   import ReactDOM from 'react-dom/client';
   import './index.css';
   import App from './App';
   import { Provider } from 'react-redux';
   import store from './store';

   ReactDOM.createRoot(document.getElementById('root')).render(
     <Provider store={store}>
       <App />
     </Provider>
   );
   ```

### How to Use in Your React App:

1. Make sure your **React** app is running on a different port (e.g., `3000`).
2. Ensure that your **backend** is running on `localhost:8080` (or update the API URLs as needed).
3. Use the `ProductDetails` component and pass the `productId` as a prop when you want to display product details.

```javascript
// Example usage in your app component
import React from 'react';
import ProductDetails from './ProductDetails';

function App() {
  return (
    <div>
      <h1>Product Details</h1>
      <ProductDetails productId={1} />
    </div>
  );
}

export default App;
```

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/olineshoppingplatform-backend.git
cd olineshoppingplatform-backend
```

### 2. Set up the database

1. Create a new MySQL database:
   ```sql
   CREATE DATABASE online_shopping;
   ```

2. Import the schema for `Products` and `Ratings` tables from above.

### 3. Configure database connection

Edit the `DBHelper.java` file to configure your database connection settings. Replace the placeholder values with your MySQL credentials.

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

### 4. Set up the web server

Deploy the project on a Java web server (e.g., Apache Tomcat). Ensure that the server is configured to support Servlets and is pointing to the project’s `webapp` directory.

### 5. Build and run

- Build the project using your favorite IDE (e.g., IntelliJ IDEA) or from the command line.
- Start the Tomcat server, and navigate to the servlet endpoint in your browser.

Example URL for product details:
```
http://localhost:8080/olineshoppingplatform/ProductDetailsServlet?id=1
```

---

This updated `README.md` now explicitly integrates the Vite React setup, including Redux for state management, and ensures that the instructions reflect a full frontend setup with API integration.