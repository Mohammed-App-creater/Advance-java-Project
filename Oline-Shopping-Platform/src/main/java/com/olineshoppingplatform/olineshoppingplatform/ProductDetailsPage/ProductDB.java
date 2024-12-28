package com.olineshoppingplatform.olineshoppingplatform.ProductDetailsPage;

import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDB {

    // Fetch product details by product_id
    public static Product getProductById(int productId) throws ClassNotFoundException {

        Product product = null;
        String query = "SELECT * FROM products WHERE product_id = ?";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("image_url"),
                        rs.getDouble("price"),
                        rs.getDouble("discount"),
                        rs.getInt("category_id"),
                        rs.getInt("stock"),
                        rs.getInt("seller_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // Fetch product reviews based on product_id
    public static List<Review> getProductReviews(int productId) {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM Ratings WHERE product_id = ?";

        try (Connection connection = DBHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Review review = new Review(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("rating"),
                        rs.getString("review_text")
                );
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}

