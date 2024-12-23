# React Developer Guide for Order Confirmation Page

This document provides detailed instructions for React developers integrating the **Order Confirmation Page** with the backend API.

---

## **Overview**
The Order Confirmation Page displays details about an order after it has been placed. This includes:
- Order summary (e.g., customer name, total amount, and order status).
- List of ordered items (e.g., product name, quantity, and price).
- Shipping details (e.g., tracking number, shipping status, and delivery date).

The page fetches data from the backend API and renders it dynamically.

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

## **Frontend Development Instructions**

### 1. **Install Dependencies**
Ensure you have the following libraries installed in your React project:

```bash
npm install axios react-router-dom
```

### 2. **Create the Order Confirmation Page Component**

#### File Path:
`src/pages/OrderConfirmation.jsx`

#### Sample Code:
```javascript
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const OrderConfirmation = () => {
    const { orderId } = useParams(); // Retrieve orderId from the URL.
    const [orderData, setOrderData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchOrderData = async () => {
            try {
                const response = await axios.get(`/orderConfirmation`, {
                    params: { orderId }
                });
                setOrderData(response.data);
            } catch (err) {
                setError('Failed to load order details.');
            } finally {
                setLoading(false);
            }
        };

        fetchOrderData();
    }, [orderId]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>{error}</div>;

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

### 3. **Add Route for the Page**

#### File Path:
`src/App.jsx`

#### Add the Route:
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

### 4. **Styling (Optional)**
Use Tailwind CSS or your preferred styling library to enhance the appearance of the page. Example:

```css
.order-confirmation {
    padding: 2rem;
}

.order-summary, .order-items, .shipping-details {
    margin-bottom: 2rem;
}

.order-items ul {
    list-style-type: none;
    padding: 0;
}

.order-items li {
    display: flex;
    align-items: center;
    margin-bottom: 1rem;
}

.order-items img {
    margin-right: 1rem;
    border-radius: 8px;
}
```

---

## **Testing**
1. **Setup Local API Proxy (Optional):**
   If the backend runs on a different port, configure a proxy in `vite.config.js`:
   ```javascript
   server: {
       proxy: {
           '/orderConfirmation': 'http://localhost:8080'
       }
   }
   ```

2. **Verify Endpoint:**
   Test the backend API with a tool like Postman to ensure it returns the correct data.

3. **Test the Page:**
    - Navigate to `/order-confirmation/:orderId` in your React app.
    - Verify that the page displays order details correctly.

---

## **Notes**
- Ensure the backend API is deployed and accessible to the frontend.
- Handle any additional edge cases (e.g., empty orders, missing shipping data) as required.

---


