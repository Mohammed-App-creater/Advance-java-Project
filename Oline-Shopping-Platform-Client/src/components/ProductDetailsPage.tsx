import React, { useState, useEffect } from 'react';
//import { useParams } from 'react-router-dom';
import axios from 'axios';

interface Product {
  productId: string;
  name: string;
  description: string;
  price: number;
  stock: number;
  sellerName: string;
  imageUrl: string;
}

interface Review {
  id: string;
  reviewText: string;
  rating: number;
  customerId: string;
}

const ProductDetailsPage: React.FC = () => {
  //const { productId } = useParams<{ productId: string }>();
  const productId = "1";
  const [product, setProduct] = useState<Product | null>(null);
  const [reviews, setReviews] = useState<Review[]>([]);

  useEffect(() => {
    // Fetch product details from the backend API
    axios.get(`http://localhost:8080/ProductDetailsServlet?email=mohammedisamil160@gmail.com&password=12345678&id=${productId}`)
      .then((response) => {
        setProduct(response.data.product);
      })
      .catch((error) => {
        console.error('Error fetching product details:', error);
      });

    // Fetch reviews for the product
    // axios.get(`http://localhost:8080/olineshoppingplatform/api/reviews/${productId}`)
    //   .then((response) => {
    //     setReviews(response.data.reviews);
    //   })
    //   .catch((error) => {
    //     console.error('Error fetching reviews:', error);
    //   });
  }, [productId]);

  const addToCart = () => {
    if (product) {
      axios.post('/api/cart', { productId: product.productId, quantity: 1 })
        .then(() => alert('Product added to cart'))
        .catch((error) => {
          console.error('Error adding product to cart:', error);
        });
    }
  };

  return (
    <div className="container mx-auto p-4">
      {product ? (
        <>
          <div className="flex flex-col md:flex-row items-center space-y-4 md:space-x-8">
            <img
              src={product.imageUrl}
              alt={product.name}
              className="w-full md:w-1/3 rounded-lg shadow-lg"
            />
            <div className="flex flex-col space-y-4 w-full md:w-2/3">
              <h1 className="text-3xl font-semibold">{product.name}</h1>
              <p className="text-lg">{product.description}</p>
              <p className="text-xl font-bold">Price: ${product.price}</p>
              <p className="text-lg">
                <strong>Seller:</strong> {product.sellerName}
              </p>
              <p className="text-lg">
                <strong>Availability:</strong> {product.stock > 0 ? 'In Stock' : 'Out of Stock'}
              </p>

              <button
                onClick={addToCart}
                disabled={product.stock === 0}
                className="bg-blue-500 text-white py-2 px-4 rounded-md shadow hover:bg-blue-600 disabled:bg-gray-400"
              >
                Add to Cart
              </button>
            </div>
          </div>

          <div className="mt-8">
            <h2 className="text-2xl font-semibold">Reviews</h2>
            {reviews.length > 0 ? (
              <div className="space-y-4 mt-4">
                {reviews.map((review) => (
                  <div key={review.id} className="border p-4 rounded-lg shadow-sm">
                    <p className="text-lg font-medium">{review.reviewText}</p>
                    <p className="text-sm text-gray-500">Rating: {review.rating} / 5</p>
                  </div>
                ))}
              </div>
            ) : (
              <p className="mt-2 text-gray-500">No reviews yet</p>
            )}
          </div>

          {/* Display similar products */}
          <div className="mt-8">
            <h3 className="text-xl font-semibold">Similar Products</h3>
            <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-4 mt-4">
              {/* Similar products will be displayed here */}
            </div>
          </div>
        </>
      ) : (
        <p className="text-xl">Loading product details...</p>
      )}
    </div>
  );
};

export default ProductDetailsPage;
