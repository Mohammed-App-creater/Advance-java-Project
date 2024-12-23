# Frontend Developer Guide for Shopping Cart Integration

This guide outlines how to integrate the shopping cart functionality into the frontend of the online shopping platform using React.

---

## **API Endpoints**

Here are the backend API endpoints available for managing the shopping cart:

### **1. Get Cart Items**
**Endpoint:** `/cart`

- **Method:** `GET`
- **Description:** Fetch all items in the user's cart.
- **Response:**
  ```json
  [
    {
      "id": 1,
      "userId": 101,
      "productId": 202,
      "quantity": 2,
      "productName": "Product Name",
      "price": 29.99,
      "imageUrl": "http://example.com/image.jpg"
    }
  ]
  ```

### **2. Add Item to Cart**
**Endpoint:** `/cart`

- **Method:** `POST`
- **Description:** Add a product to the user's cart.
- **Request Body:**
  ```json
  {
    "productId": 202,
    "quantity": 2
  }
  ```
- **Response:**
    - **200 OK** if the operation succeeds.

### **3. Update Cart Item Quantity**
**Endpoint:** `/cart/{productId}`

- **Method:** `PUT`
- **Description:** Update the quantity of a specific product in the user's cart.
- **Request Parameters:**
    - `productId`: The ID of the product to update.
- **Query Parameter:**
    - `quantity`: The new quantity.
- **Response:**
    - **200 OK** if the operation succeeds.

### **4. Remove Item from Cart**
**Endpoint:** `/cart/{productId}`

- **Method:** `DELETE`
- **Description:** Remove a product from the user's cart.
- **Request Parameters:**
    - `productId`: The ID of the product to remove.
- **Response:**
    - **200 OK** if the operation succeeds.

---

## **Steps for Frontend Integration**

### **1. Setup Axios for API Calls**

Install Axios:
```bash
npm install axios
```

Create an `api.js` file:
```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // Replace with your backend URL
  withCredentials: true, // If using cookies for session management
});

export default api;
```

### **2. Create Cart Context (Optional)**

Use React Context to manage cart state globally:
```javascript
import React, { createContext, useContext, useState } from 'react';

const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const [cart, setCart] = useState([]);

  const fetchCart = async () => {
    const response = await api.get('/cart');
    setCart(response.data);
  };

  const addToCart = async (productId, quantity) => {
    await api.post('/cart', { productId, quantity });
    fetchCart();
  };

  const updateCartItem = async (productId, quantity) => {
    await api.put(`/cart/${productId}?quantity=${quantity}`);
    fetchCart();
  };

  const removeFromCart = async (productId) => {
    await api.delete(`/cart/${productId}`);
    fetchCart();
  };

  return (
    <CartContext.Provider
      value={{ cart, fetchCart, addToCart, updateCartItem, removeFromCart }}
    >
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => useContext(CartContext);
```

Wrap your app with `CartProvider` in `index.js`:
```javascript
import { CartProvider } from './context/CartContext';

ReactDOM.render(
  <CartProvider>
    <App />
  </CartProvider>,
  document.getElementById('root')
);
```

### **3. Components for Cart**

#### **Cart Page**
```javascript
import React, { useEffect } from 'react';
import { useCart } from '../context/CartContext';

const CartPage = () => {
  const { cart, fetchCart, updateCartItem, removeFromCart } = useCart();

  useEffect(() => {
    fetchCart();
  }, []);

  return (
    <div>
      <h1>Your Cart</h1>
      {cart.map((item) => (
        <div key={item.id}>
          <img src={item.imageUrl} alt={item.productName} width="50" />
          <p>{item.productName}</p>
          <p>Price: ${item.price}</p>
          <input
            type="number"
            value={item.quantity}
            onChange={(e) => updateCartItem(item.productId, e.target.value)}
          />
          <button onClick={() => removeFromCart(item.productId)}>Remove</button>
        </div>
      ))}
    </div>
  );
};

export default CartPage;
```

#### **Add to Cart Button**
```javascript
import React from 'react';
import { useCart } from '../context/CartContext';

const AddToCartButton = ({ productId }) => {
  const { addToCart } = useCart();

  const handleClick = () => {
    addToCart(productId, 1); // Default quantity is 1
  };

  return <button onClick={handleClick}>Add to Cart</button>;
};

export default AddToCartButton;
```

---

## **Notes for Frontend Developer**
1. **Authentication**: Ensure the user is authenticated before accessing the cart endpoints.
2. **Error Handling**: Handle API errors gracefully using try-catch blocks or Axios interceptors.
3. **Styling**: Use Tailwind CSS or your preferred framework to style the components.
4. **Testing**: Test each API call and component thoroughly.

