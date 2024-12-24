

# Vite + React + Redux Project Setup

This README provides instructions for setting up, running, and working with a Vite + React project, integrated with Redux for state management. The project is part of an online shopping platform, which interacts with a Java-based backend (using servlets).

## Table of Contents
1. [Project Setup](#project-setup)
2. [Development Server](#development-server)
3. [Backend Integration](#backend-integration)
4. [Redux Integration](#redux-integration)
5. [Folder Structure](#folder-structure)
6. [Key Commands](#key-commands)
7. [API Integration](#api-integration)
8. [Build and Deployment](#build-and-deployment)

---

## Project Setup

1. **Install Node.js and npm**  
   Ensure Node.js and npm are installed. Check versions:
   ```bash
   node -v
   npm -v
   ```

2. **Create a Vite Project**  
   Create a new Vite project with React:
   ```bash
   npm create vite@latest
   ```

   Follow the prompts:
    - Project name: `my-shopping-platform`
    - Framework: `React`
    - Variant: `JavaScript` or `TypeScript` (as per preference)

3. **Navigate to the Project Directory**
   ```bash
   cd my-shopping-platform
   ```

4. **Install Dependencies**  
   Install the required dependencies:
   ```bash
   npm install
   ```

5. **Install Additional Libraries (Optional)**  
   Add libraries like `react-router-dom`, `axios`, `@reduxjs/toolkit`, and `react-redux`:
   ```bash
   npm install react-router-dom axios @reduxjs/toolkit react-redux
   ```

---

## Development Server

Start the development server:
```bash
npm run dev
```
The server will run locally, typically at `http://localhost:5173`. Open this URL in your browser to view the app.

---

## Backend Integration

To integrate with your Java backend, follow these steps:

1. **Setup API Proxy**  
   Configure Vite to proxy API requests to your backend (e.g., Tomcat server running at `http://localhost:8080`). Add the proxy configuration in `vite.config.js`:

   ```javascript
   import { defineConfig } from 'vite';
   import react from '@vitejs/plugin-react';

   export default defineConfig({
     plugins: [react()],
     server: {
       proxy: {
         '/api': {
           target: 'http://localhost:8080', // Backend server URL
           changeOrigin: true,
           secure: false,
         },
       },
     },
   });
   ```

2. **Make API Calls**  
   Use `axios` or the `fetch` API in React to make requests to your backend. Example:
   ```javascript
   import axios from 'axios';

   const fetchOrders = async () => {
     try {
       const response = await axios.get('/api/orders/history?customerId=1');
       console.log(response.data);
     } catch (error) {
       console.error('Error fetching orders:', error);
     }
   };
   ```

---

## Redux Integration

### Step 1: Set Up Redux Store

Create a `store.js` file to configure the Redux store.

#### `src/store.js`

```javascript
import { configureStore } from '@reduxjs/toolkit';
import cartReducer from './features/cart/cartSlice';

export const store = configureStore({
  reducer: {
    cart: cartReducer, // Add the cart reducer to the store
  },
});
```

### Step 2: Create a Cart Slice

Create a `cartSlice.js` to manage the shopping cart's state.

#### `src/features/cart/cartSlice.js`

```javascript
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  items: [], // This will store the items in the cart
};

const cartSlice = createSlice({
  name: 'cart',
  initialState,
  reducers: {
    addToCart: (state, action) => {
      state.items.push(action.payload); // Add the product to the cart array
    },
    removeFromCart: (state, action) => {
      state.items = state.items.filter(item => item.productId !== action.payload);
    },
    clearCart: (state) => {
      state.items = [];
    },
  },
});

export const { addToCart, removeFromCart, clearCart } = cartSlice.actions;

export default cartSlice.reducer;
```

### Step 3: Provide Redux Store to the App

Wrap your application with the Redux `Provider` to make the store available across your app.

#### `src/main.jsx`

```javascript
import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux'; // Import Provider to wrap the app
import { store } from './store'; // Import the Redux store
import App from './App'; // Your main app component

ReactDOM.createRoot(document.getElementById('root')).render(
  <Provider store={store}> {/* Wrap the App with the Redux Provider */}
    <App />
  </Provider>
);
```

### Step 4: Use Redux in Components

Now, you can manage the cart state using Redux in any of your components.

#### `src/components/CartPage.jsx`

```javascript
import React from 'react';
import { useDispatch, useSelector } from 'react-redux'; // Import hooks for accessing state and dispatching actions
import { addToCart, removeFromCart } from '../features/cart/cartSlice'; // Import actions

const CartPage = () => {
  const dispatch = useDispatch(); // To dispatch actions to the store
  const cart = useSelector((state) => state.cart.items); // To access the cart items from the Redux store

  const handleAddToCart = (product) => {
    dispatch(addToCart(product)); // Dispatch the action to add the product to the cart
  };

  const handleRemoveFromCart = (productId) => {
    dispatch(removeFromCart(productId)); // Dispatch the action to remove the product by productId
  };

  return (
    <div>
      <h1>Shopping Cart</h1>
      {cart.length === 0 ? (
        <p>Your cart is empty!</p>
      ) : (
        cart.map((item) => (
          <div key={item.productId}>
            <p>{item.productName}</p>
            <p>Price: ${item.price}</p>
            <button onClick={() => handleRemoveFromCart(item.productId)}>Remove</button>
          </div>
        ))
      )}
      <button onClick={() => handleAddToCart({ productId: 1, productName: 'Sample Product', price: 10 })}>
        Add Sample Product
      </button>
    </div>
  );
};

export default CartPage;
```

---

## Folder Structure

Your project folder structure will look like this:

```
my-shopping-platform/
├── public/               # Static assets
├── src/
│   ├── components/       # Reusable React components
│   ├── pages/            # Page-specific components
│   ├── features/         # Redux slices (e.g., cartSlice)
│   ├── services/         # API service files (e.g., axios calls)
│   ├── App.jsx           # Main application component
│   ├── main.jsx          # Entry point
│   ├── store.js          # Redux store
├── vite.config.js        # Vite configuration file
├── package.json          # npm dependencies and scripts
└── README.md             # Project documentation
```

---

## Key Commands

### Install Dependencies
```bash
npm install
```

### Start Development Server
```bash
npm run dev
```

### Build for Production
```bash
npm run build
```

### Preview Production Build
```bash
npm run preview
```

---

## API Integration

Here's how the frontend interacts with the backend:

1. **API Structure**  
   The backend exposes RESTful endpoints (e.g., `/api/orders/history`).

2. **Fetching Data**  
   Use `fetch` or `axios` in React to interact with these endpoints. Example with `axios`:
   ```javascript
   import axios from 'axios';

   const fetchOrderHistory = async (customerId) => {
     try {
       const response = await axios.get(`/api/orders/history?customerId=${customerId}`);
       return response.data;
     } catch (error) {
       console.error('Error fetching order history:', error);
       throw error;
     }
   };
   ```

3. **Displaying Data**  
   Map the fetched data to components:
   ```jsx
   import React, { useEffect, useState } from 'react';

   const OrderHistory = () => {
     const [orders, setOrders] = useState([]);
     const [error, setError] = useState(null);

     useEffect(() => {
       const fetchOrders = async () => {
         try {
           const data = await fetchOrderHistory(1);
           setOrders(data);
         } catch (err) {
           setError(err.message);
         }
       };
       fetchOrders();
     }, []);

     if (error) return <div>Error: {error}</div>;

     return (
       <div>
         <h1>Order History</h1>
         {orders.map(order => (
           <div key={order.orderId}>
             <p>Order ID: {order.orderId}</p>
             <p>Total: {order.totalAmount}</p>
           </div>
         ))}
       </div>
     );
   };

   export default OrderHistory;
   ```

---

## Build and Deployment

1. **Build the Project**
   Create a production build:
   ```bash
   npm run build
   ```

   The output will be generated in the `dist/` folder.

2. **Deploy the Build**
    - Serve the `dist/` folder using a static server (e.g., Nginx, Apache).
    - Ensure the backend API is accessible from the deployed app.

---

