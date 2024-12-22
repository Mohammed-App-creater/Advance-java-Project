

# Product Listings API - Frontend Integration Documentation (React)

## API Endpoint:
- **URL**: `/HomeProductListings`
- **Method**: `POST`
- **Content-Type**: `application/json`

## Request Body Parameters:
- `categoryName` (String): The category of products to fetch (e.g., "Electronics", "Clothing").
- `pageSize` (Integer): The number of products to display per page (e.g., 10).
- `page` (Integer): The current page number to fetch (e.g., 1).

### Request Example:

```json
{
  "categoryName": "Electronics",
  "pageSize": 10,
  "page": 1
}
```


## Response Format:

The API will return a JSON object with an array of products for the requested category and page.

### Response Example:

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
    // More products...
  ],
  "totalProducts": 50
}
```

## Frontend Integration Steps (React):

1. **Install Axios**:
   If you haven't already, you can use `axios` to send HTTP requests. Install it via npm:

   ```bash
   npm install axios
   ```

2. **Create ProductListing Component**:

   In your React app, create a new component to handle product listings and pagination.

   ```jsx
   // src/components/ProductListings.js
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

3. **Explanation of Code**:

   - **State Management**:
      - `products`: Holds the list of products for the current page.
      - `totalProducts`: Total number of products available (used to calculate total pages for pagination).
      - `page`: The current page number for pagination.

   - **useEffect Hook**:
      - This hook runs when the component is mounted or when the `page` state changes. It sends a `POST` request to the `/HomeProductListings` API with the current category, page size, and page number.

   - **Pagination**:
      - Based on the `totalProducts`, we calculate the total number of pages (`totalPages`) and conditionally display pagination buttons to navigate between pages.
      - When a page button is clicked, the `handlePageChange` function updates the `page` state, which triggers the `useEffect` hook to fetch the new products.

4. **Display Products**:
   - Products are displayed dynamically using the `products` state, where each product's details (name, description, price, etc.) are shown in a `div`.

5. **Styling**:
   You can apply Tailwind CSS, Bootstrap, or custom CSS to style the product items and pagination buttons.

   Example of basic styling with Tailwind:

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

6. **Test and Verify**:
   - Run your React app and ensure that the products are being displayed correctly, and that pagination works as expected.

---

### How to Use This in Your Project:

1. **Backend Setup**:
   - Ensure that the backend is properly set up with the Product Listings API to accept `POST` requests at `/HomeProductListings`.

2. **Frontend Setup**:
   - Follow the steps above to integrate the `ProductListings` component into your React app.
   - Make sure to pass the correct category name, page size, and page number as parameters.

3. **Testing**:
   - Test the pagination functionality by navigating through the product pages and ensuring that the correct products are displayed for each page.

---
