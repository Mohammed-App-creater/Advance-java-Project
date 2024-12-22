

# Product Listings API - Frontend Integration Documentation

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

The API will return a JSON array of products for the requested category and page.

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

## Frontend Integration Steps:

1. **Send Request to API**:
    - The frontend should make a `POST` request to `/HomeProductListings` with the required parameters (`categoryName`, `pageSize`, `page`).
    - Example (JavaScript using `fetch`):

   ```javascript
   const pageSize = 10;  // Number of products per page
   const page = 1;  // Current page number
   const categoryName = "Electronics";  // Category name

   fetch('http://localhost:8080/HomeProductListings', {
     method: 'POST',
     headers: {
       'Content-Type': 'application/json'
     },
     body: JSON.stringify({
       categoryName,
       pageSize,
       page
     })
   })
   .then(response => response.json())
   .then(data => {
     const products = data.products;
     const totalProducts = data.totalProducts;
     
     // Use the 'products' array to render the products on the page
     // Use 'totalProducts' to calculate the total number of pages
     renderProducts(products);
     setupPagination(totalProducts, pageSize);
   })
   .catch(error => console.error('Error fetching products:', error));
   ```

2. **Display Products**:
    - After receiving the response, use the `products` array to display the products on the page.

   Example of displaying a product:

   ```html
   <div id="product-list">
     <!-- Products will be dynamically injected here -->
   </div>

   <script>
     function renderProducts(products) {
       const productListDiv = document.getElementById('product-list');
       productListDiv.innerHTML = "";  // Clear existing products
       
       products.forEach(product => {
         const productDiv = document.createElement('div');
         productDiv.classList.add('product');
         productDiv.innerHTML = `
           <h3>${product.name}</h3>
           <p>${product.description}</p>
           <p>Price: $${product.price}</p>
           <p>Discount: $${product.discount}</p>
           <p>Stock: ${product.stock}</p>
           <p>Category: ${product.category_name}</p>
         `;
         productListDiv.appendChild(productDiv);
       });
     }
   </script>
   ```

3. **Pagination**:
    - Use the `totalProducts` value returned by the API to calculate how many pages there are. The number of pages is determined by dividing `totalProducts` by `pageSize`.
    - Example of creating pagination buttons:

   ```html
   <div id="pagination">
     <!-- Pagination buttons will be dynamically injected here -->
   </div>

   <script>
     function setupPagination(totalProducts, pageSize) {
       const totalPages = Math.ceil(totalProducts / pageSize);
       const paginationDiv = document.getElementById('pagination');
       paginationDiv.innerHTML = "";  // Clear existing pagination

       for (let i = 1; i <= totalPages; i++) {
         const pageButton = document.createElement('button');
         pageButton.textContent = i;
         pageButton.onclick = () => loadPage(i);
         paginationDiv.appendChild(pageButton);
       }
     }

     function loadPage(pageNumber) {
       // Fetch products for the selected page
       fetchProducts(pageNumber);
     }
   </script>
   ```

## Conclusion:
This documentation outlines how the frontend developer can use the Product Listings API with page-based pagination to display products. Use the provided request and response format to implement the necessary functionality and integrate it with the frontend.

---

### How to Use This in Your Project:

1. **Backend Setup**:
    - Ensure that the backend is properly set up with the Product Listings API to accept `POST` requests at `/HomeProductListings`.
    - Ensure the database is populated with categories and products to retrieve.

2. **Frontend Setup**:
    - Implement the JavaScript code to make the `POST` request to the API, handle the response, and render the products and pagination on the page.

3. **Testing**:
    - Test the pagination functionality by clicking through the pagination buttons and verifying that the correct products for each page are displayed.

---

You can now save this as a `README.md` file and add it to your GitHub repository! Let me know if you'd like me to generate the file for you directly.