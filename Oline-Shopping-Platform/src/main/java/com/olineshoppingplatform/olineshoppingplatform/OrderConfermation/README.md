

# Vite React Developer Guide for Order Confirmation Page with Redux

This document provides detailed instructions for developers using **Vite React** with **Redux** to implement the **Order Confirmation Page** integrated with the backend API.

---

## **Overview**
The Order Confirmation Page displays details about an order after it has been placed. This includes:
- Order summary (e.g., customer name, total amount, and order status).
- List of ordered items (e.g., product name, quantity, and price).
- Shipping details (e.g., tracking number, shipping status, and delivery date).

The page fetches data from the backend API and renders it dynamically using **Redux** for state management.

---

## **Backend API Endpoint**

### Endpoint:
```
GET /orderConfirmation
```

### Query Parameter:
- **`orderId`** (required): The ID of the order to fetch details for.

### Response Example (JSON):
```json
{
    "order": {
        "orderId": 123,
        "customerName": "John Doe",
        "orderDate": "2024-12-23T12:34:56",
        "totalAmount": 100.50,
        "shippingAddress": "123 Main St",
        "status": "shipped"
    },
    "items": [
        {
            "productName": "Product A",
            "imageUrl": "http://example.com/product-a.jpg",
            "quantity": 2,
            "price": 20.00
        },
        {
            "productName": "Product B",
            "imageUrl": "http://example.com/product-b.jpg",
            "quantity": 1,
            "price": 60.50
        }
    ],
    "shipping": {
        "trackingNumber": "TRACK123",
        "shippingStatus": "in_transit",
        "deliveryDate": "2024-12-25T12:00:00"
    }
}
```

---

## **Setup Instructions**

### 1. **Initialize Vite Project**
Create a Vite project with React and install Redux dependencies:

```bash
npm create vite@latest order-confirmation-page --template react
cd order-confirmation-page
npm install react-redux @reduxjs/toolkit axios react-router-dom
```

---

## **State Management with Redux**

### 2. **Configure Redux Store**

#### File Path:
`src/store.js`

#### Code:
```javascript
import { configureStore } from '@reduxjs/toolkit';
import orderReducer from './slices/orderSlice';

export const store = configureStore({
    reducer: {
        order: orderReducer,
    },
});
```

---

### 3. **Create Order Slice**

#### File Path:
`src/slices/orderSlice.js`

#### Code:
```javascript
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

// Async Thunk for Fetching Order Data
export const fetchOrderDetails = createAsyncThunk(
    'order/fetchOrderDetails',
    async (orderId) => {
        const response = await axios.get('/orderConfirmation', {
            params: { orderId },
        });
        return response.data;
    }
);

const orderSlice = createSlice({
    name: 'order',
    initialState: {
        data: null,
        loading: false,
        error: null,
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchOrderDetails.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchOrderDetails.fulfilled, (state, action) => {
                state.loading = false;
                state.data = action.payload;
            })
            .addCase(fetchOrderDetails.rejected, (state, action) => {
                state.loading = false;
                state.error = action.error.message;
            });
    },
});

export default orderSlice.reducer;
```

---

### 4. **Wrap App with Redux Provider**

#### File Path:
`src/main.jsx`

#### Code:
```javascript
import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import { store } from './store';
import App from './App';

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <Provider store={store}>
            <App />
        </Provider>
    </React.StrictMode>
);
```

---

## **Frontend Development Instructions**

### 5. **Create the Order Confirmation Page Component**

#### File Path:
`src/pages/OrderConfirmation.jsx`

#### Code:
```javascript
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchOrderDetails } from '../slices/orderSlice';
import { useParams } from 'react-router-dom';

const OrderConfirmation = () => {
    const { orderId } = useParams();
    const dispatch = useDispatch();
    const { data: orderData, loading, error } = useSelector((state) => state.order);

    useEffect(() => {
        dispatch(fetchOrderDetails(orderId));
    }, [dispatch, orderId]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;

    const { order, items, shipping } = orderData;

    return (
        <div className="order-confirmation">
            <h1>Order Confirmation</h1>
            <div className="order-summary">
                <h2>Order Summary</h2>
                <p><strong>Order ID:</strong> {order.orderId}</p>
                <p><strong>Customer Name:</strong> {order.customerName}</p>
                <p><strong>Total Amount:</strong> ${order.totalAmount}</p>
                <p><strong>Status:</strong> {order.status}</p>
                <p><strong>Order Date:</strong> {new Date(order.orderDate).toLocaleString()}</p>
                <p><strong>Shipping Address:</strong> {order.shippingAddress}</p>
            </div>

            <div className="order-items">
                <h2>Ordered Items</h2>
                <ul>
                    {items.map((item, index) => (
                        <li key={index}>
                            <img src={item.imageUrl} alt={item.productName} width="100" />
                            <p><strong>{item.productName}</strong></p>
                            <p>Quantity: {item.quantity}</p>
                            <p>Price: ${item.price}</p>
                        </li>
                    ))}
                </ul>
            </div>

            <div className="shipping-details">
                <h2>Shipping Details</h2>
                <p><strong>Tracking Number:</strong> {shipping.trackingNumber}</p>
                <p><strong>Status:</strong> {shipping.shippingStatus}</p>
                <p><strong>Delivery Date:</strong> {new Date(shipping.deliveryDate).toLocaleString()}</p>
            </div>
        </div>
    );
};

export default OrderConfirmation;
```

---

### 6. **Add Route for the Page**

#### File Path:
`src/App.jsx`

#### Code:
```javascript
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import OrderConfirmation from './pages/OrderConfirmation';

function App() {
    return (
        <Router>
            <Routes>
                {/* Other routes */}
                <Route path="/order-confirmation/:orderId" element={<OrderConfirmation />} />
            </Routes>
        </Router>
    );
}

export default App;
```

---

## **Testing**
Follow the original React instructions for testing, updating any relevant proxy configurations in `vite.config.js` if needed.

--- 

