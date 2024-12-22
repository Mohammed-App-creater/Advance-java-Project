import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

interface Product {
  id: string;
  name: string;
  description: string;
  price: number;
  stock: number;
  sellerName: string;
}

interface Review {
  id: string;
  content: string;
  rating: number;
}

const ProductDetailsPage: React.FC = () => {
  const { productId } = useParams<{ productId: string }>(); // useParams hook with typing
  const [product, setProduct] = useState<Product | null>(null);
  const [reviews, setReviews] = useState<Review[]>([]);

//   useEffect(() => {
//     // Fetch product details from the backend API
//     axios.get(`http://localhost:8080/Oline_Shopping_Platform_war_exploded/${productId}`)
//       .then((response) => {
//         setProduct(response.data);
//       })
//       .catch((error) => {
//         console.error('Error fetching product details:', error);
//       });

//     // Fetch reviews for the product
//     axios.get(`/api/reviews/${productId}`)
//       .then((response) => {
//         setReviews(response.data);
//       })
//       .catch((error) => {
//         console.error('Error fetching reviews:', error);
//       });
//   }, [productId]);

useEffect(() => {
    // Simulating a product fetch with sample data
    const sampleProduct = {
      id: '12345',
      name: 'Wireless Bluetooth Headphones',
      description: 'High-quality wireless headphones with noise cancellation and 20 hours of battery life. Perfect for music lovers and frequent travelers.',
      price: 99.99,
      stock: 150,
      sellerName: 'TechStore',
    };

    const sampleReviews = [
      { id: 'r1', content: 'These headphones are amazing! Great sound quality and comfortable to wear for long periods.', rating: 5 },
      { id: 'r2', content: 'Good value for money, but the noise cancellation could be better.', rating: 4 },
      { id: 'r3', content: 'Not bad, but the build quality feels a little cheap. Sound is decent though.', rating: 3 }
    ];

    setProduct(sampleProduct);
    setReviews(sampleReviews);
  }, [productId]);

  const addToCart = () => {
    // Handle adding product to cart (this would call an API endpoint)
    if (product) {
      axios.post('/api/cart', { productId: product.id, quantity: 1 })
        .then(() => alert('Product added to cart'))
        .catch((error) => {
          console.error('Error adding product to cart:', error);
        });
    }
  };

  return (
    <div className="product-details">
      {product ? (
        <>
          <h1>{product.name}</h1>
          <p>{product.description}</p>
          <p><strong>Price:</strong> ${product.price}</p>
          <p><strong>Seller:</strong> {product.sellerName}</p>
          <p><strong>Availability:</strong> {product.stock > 0 ? 'In Stock' : 'Out of Stock'}</p>

          <h2>Reviews</h2>
          {reviews.length > 0 ? (
            reviews.map((review) => (
              <div key={review.id}>
                <p>{review.content}</p>
                <p><strong>Rating:</strong> {review.rating}</p>
              </div>
            ))
          ) : (
            <p>No reviews yet</p>
          )}

          <button onClick={addToCart}>Add to Cart</button>
          
          {/* Display similar products (this would likely come from an API) */}
          <h3>Similar Products</h3>
          {/* Display similar products in a list or grid */}
        </>
      ) : (
        <p>Loading product details...</p>
      )}
    </div>
  );
};

export default ProductDetailsPage;
