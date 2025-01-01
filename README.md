# Online Shopping Platform Documentation

This project is a full-stack online shopping platform built with **Java Servlets** and **MySQL** for the backend, and **Vite + React + Redux** for the frontend. It allows users to view product details, manage their shopping cart, and submit reviews. The platform exposes RESTful APIs that are consumed by a React frontend application to display product information and customer reviews.

## Table of Contents

1. [Technologies Used](#technologies-used)
2. [Backend: Java Servlets + MySQL](#backend-java-servlets--mysql)
3. [Frontend: Vite + React + Redux](#frontend-vite--react--redux)
4. [API Endpoints](#api-endpoints)
5. [Frontend Integration Example](#frontend-integration-example)
6. [Project Setup](#project-setup)
7. [Folder Structure](#folder-structure)
8. [Key Commands](#key-commands)
9. [Database Schema](#database-schema)
10. [License](#license)
11. [Screenshoots](#screenshots)

---

## Technologies Used

### Backend:
- **Java 11+**: The backend uses Java for the core logic and Servlets for handling HTTP requests.
- **Servlets**: For handling API requests and responses.
- **JDBC**: For interacting with the MySQL database.
- **MySQL**: The relational database management system to store products, reviews, and user data.
- **Tomcat**: Java web server for deploying the servlet-based backend.

### Frontend:
- **Vite**: A fast build tool for React applications, used to bundle and serve the frontend.
- **React**: The JavaScript library for building the user interface.
- **Redux**: For managing application state across the frontend.
- **Axios/Fetch**: For making HTTP requests to the backend API.

---

## Backend: Java Servlets + MySQL

The backend exposes a set of RESTful APIs implemented using Java Servlets, allowing interaction with the MySQL database for retrieving product details and customer reviews.

### Database Schema

**Products Table**
```sql
CREATE TABLE Products (
  productId INT PRIMARY KEY,
  name VARCHAR(255),
  description TEXT,
  imageUrl VARCHAR(255),
  price DECIMAL(10, 2),
  discount DECIMAL(5, 2),
  categoryId INT,
  stock INT,
  sellerId INT
);
```

**Reviews Table**
```sql
CREATE TABLE Reviews (
  id INT PRIMARY KEY,
  rating DECIMAL(2, 1),
  reviewText TEXT,
  customerId INT,
  productId INT,
  createdAt DATETIME,
  FOREIGN KEY (productId) REFERENCES Products(productId)
);
```

---

## Frontend: Vite + React + Redux

The frontend is a **Vite**-based React application. It consumes the backend API to display product information and reviews. Redux is used to manage the state of the application, ensuring that data can be shared across components.

### Frontend Features:
- **State Management**: Redux handles product and review data.
- **Modular Components**: React components for easy reuse.
- **API Proxy**: Vite proxies requests to the backend API to avoid CORS issues during development.

### Frontend Setup

1. **Create a Vite + React app**:
   ```bash
   npm create vite@latest my-app --template react
   cd my-app
   ```

2. **Install dependencies**:
   ```bash
   npm install redux react-redux axios
   ```

3. **Setup Redux store**:
   In `store.js`:
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

4. **Create Redux actions**:
   In `actions.js`:
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

---

## API Endpoints

### `GET /productDetailsServlet?id={productId}`

Fetches the details of a product and its associated reviews.

#### Request
- **URL**: `/productDetailsServlet?id={productId}`
- **Method**: GET
- **Query Parameters**:
    - `id`: The ID of the product to retrieve (required).

#### Response (JSON)
- **Product Information**: Contains `productId`, `name`, `description`, `imageUrl`, `price`, `discount`, `categoryId`, `stock`, `sellerId`.
- **Reviews**: A list of reviews associated with the product.

##### Example Request:
```bash
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

---

## Frontend Integration Example

### Example Redux Integration in React

1. **Fetching data in a component**:
   ```javascript
   import React, { useEffect } from 'react';
   import { useDispatch, useSelector } from 'react-redux';
   import { setProductDetails, setReviews } from './actions';
   import axios from 'axios';

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

2. **Integrating the component** in your app:
   ```javascript
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

---

## Project Setup

### Backend Setup

1. Clone the repository and set up the project:
   ```bash
   git clone https://github.com/yourusername/olineshoppingplatform-backend.git
   cd olineshoppingplatform-backend
   ```

2. Set up the database in MySQL:
   ```sql
   CREATE DATABASE online_shopping;
   ```

3. Configure `DBHelper.java` to use your database credentials:
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

4. Deploy the backend on **Tomcat** or another Java web server, and access the API at:
   ```
   http://localhost:8080/olineshoppingplatform/ProductDetailsServlet?id=1
   ```

---

## Folder Structure

### Frontend
```
src/
├── components/
├── features/
├── pages/
├── services/
└── store.js
```

### Backend
```
src/
├── servlets/
├── models/
└── helpers/  // Contains DBHelper.java
```

---

## Key Commands

| Command             | Description                |
|---------------------|----------------------------|
| `npm install`       | Install dependencies       |
| `npm run dev`       | Start the frontend server  |
| `npm run build`     | Build for production       |
| `npm run preview`   | Preview the production build |

---

## Database Schema

1. **Products**: Contains details like `productId`, `name`, `description`, etc.
2. **Reviews**: Contains review details including `rating`, `reviewText`, `customerId`, and `createdAt`.

---

## License

This project is licensed under the MIT License. See `LICENSE` for details.

---

## Screenshots

### Frontend Example:
![Signup Page](https://github.com/Moh-Sad/Advanced-Java-Project/blob/main/Screenshots/signup.png)

![Login Page](https://github.com/Moh-Sad/Advanced-Java-Project/blob/main/Screenshots/login.png)

![Landing Page](https://github.com/Moh-Sad/Advanced-Java-Project/blob/main/Screenshots/Landingpage.png)

![User Profile Page](https://github.com/Moh-Sad/Advanced-Java-Project/blob/main/Screenshots/userProfile.png)

![Products Page](https://github.com/Moh-Sad/Advanced-Java-Project/blob/main/Screenshots/Products.png)

![Product Details Page](https://github.com/Moh-Sad/Advanced-Java-Project/blob/main/Screenshots/ProductDetials.png)

![Cart Page](https://github.com/Moh-Sad/Advanced-Java-Project/blob/main/Screenshots/Cart.png)