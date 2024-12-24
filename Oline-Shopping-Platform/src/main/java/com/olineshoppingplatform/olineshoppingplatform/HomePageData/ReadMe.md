

## **Product Listings API - Frontend Integration Documentation (React)**

### API Endpoint:
- **URL**: `/HomeProductListings`
- **Method**: `POST`
- **Content-Type**: `application/json`

### Request Body Parameters:
- **`categoryName`** (String): The category of products to fetch (e.g., "Electronics", "Clothing").
- **`pageSize`** (Integer): The number of products to display per page (e.g., 10).
- **`page`** (Integer): The current page number to fetch (e.g., 1).

#### Request Example:
```json
{
  "categoryName": "Electronics",
  "pageSize": 10,
  "page": 1
}
```

### Response Format:

The API returns a JSON object with an array of products for the requested category and page.

#### Response Example:
```json
{
  "products": [
    {
      "product_id": 1,
      "name": "Smartphone",
      "description": "Latest model with great features.",
      "price": 599.99,
      "discount": 50.00,
      "stock": 100,
      "category_name": "Electronics"
    },
    {
      "product_id": 2,
      "name": "Laptop",
      "description": "High-performance laptop for gaming.",
      "price": 899.99,
      "discount": 100.00,
      "stock": 50,
      "category_name": "Electronics"
    }
  ],
  "totalProducts": 50
}
```

### Frontend Integration Steps (React):

#### 1. **Install Axios**:
Install Axios if not already done:
```bash
npm install axios
```

#### 2. **Create `ProductListings` Component**:
Create a new component to display product listings and handle pagination.

**File Path**: `src/components/ProductListings.js`

```jsx
import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ProductListings = () => {
  const [products, setProducts] = useState([]);
  const [totalProducts, setTotalProducts] = useState(0);
  const [page, setPage] = useState(1);
  const pageSize = 10; // Number of products per page
  const categoryName = "Electronics"; // Example category

  useEffect(() => {
    // Fetch products when page or category changes
    const fetchProducts = async () => {
      try {
        const response = await axios.post('http://localhost:8080/HomeProductListings', {
          categoryName,
          pageSize,
          page,
        });
        setProducts(response.data.products);
        setTotalProducts(response.data.totalProducts);
      } catch (error) {
        console.error('Error fetching products:', error);
      }
    };
    fetchProducts();
  }, [page]);

  // Handle page change
  const handlePageChange = (newPage) => {
    setPage(newPage);
  };

  // Calculate total pages
  const totalPages = Math.ceil(totalProducts / pageSize);

  return (
    <div>
      <h1>Product Listings</h1>
      <div>
        {products.length > 0 ? (
          products.map((product) => (
            <div key={product.product_id} className="product-item">
              <h2>{product.name}</h2>
              <p>{product.description}</p>
              <p>Price: ${product.price}</p>
              <p>Discount: ${product.discount}</p>
              <p>Stock: {product.stock}</p>
              <p>Category: {product.category_name}</p>
            </div>
          ))
        ) : (
          <p>No products available</p>
        )}
      </div>

      {/* Pagination */}
      <div className="pagination">
        {page > 1 && (
          <button onClick={() => handlePageChange(page - 1)}>Previous</button>
        )}
        {page < totalPages && (
          <button onClick={() => handlePageChange(page + 1)}>Next</button>
        )}
      </div>
    </div>
  );
};

export default ProductListings;
```

#### 3. **Explanation of Code**:
- **State Management**:
    - `products`: Holds the list of products for the current page.
    - `totalProducts`: The total number of products available (used to calculate total pages for pagination).
    - `page`: The current page number for pagination.

- **useEffect Hook**:
    - Sends a `POST` request to the `/HomeProductListings` API with the current category, page size, and page number whenever the `page` changes.

- **Pagination**:
    - Based on the `totalProducts`, the total number of pages (`totalPages`) is calculated. Pagination buttons are conditionally displayed to navigate between pages.
    - The `handlePageChange` function updates the `page` state, which triggers the `useEffect` hook to fetch the products for the new page.

#### 4. **Display Products**:
Products are displayed dynamically by mapping over the `products` state, rendering each product's details such as name, description, price, discount, stock, and category.

#### 5. **Styling**:
You can use Tailwind CSS or other libraries to style the product items and pagination buttons. Below is a basic example using Tailwind CSS:

```html
<div className="p-4 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
  <div className="bg-white p-4 border rounded shadow">
    <h3 className="text-xl font-bold">Product Name</h3>
    <p className="text-sm text-gray-600">Product Description</p>
    <p className="text-lg text-green-600">Price: $199.99</p>
    <p className="text-sm text-gray-500">Stock: 100</p>
  </div>
</div>
```

#### 6. **Test and Verify**:
- Ensure your React app fetches and displays the products correctly, and that pagination works as expected.

---

### How to Use This in Your Project:

1. **Backend Setup**:
    - Ensure that the backend is set up to accept `POST` requests at `/HomeProductListings` and correctly returns the list of products based on the parameters.

2. **Frontend Setup**:
    - Integrate the `ProductListings` component into your React app by adding it to the appropriate route or page.
    - Pass the correct category name, page size, and page number as parameters.

3. **Testing**:
    - Test the pagination by navigating through the product pages and ensuring the correct products are displayed for each page.

---

