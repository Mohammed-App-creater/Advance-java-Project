package com.olineshoppingplatform.olineshoppingplatform.CustmerProfile;


import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;
import com.olineshoppingplatform.olineshoppingplatform.utils.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Method to get a product by ID
    public Product getProductById(int productId) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "SELECT * FROM Products WHERE product_id = ?";
        try (Connection con = DBHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("image_url"),
                        rs.getBigDecimal("price"),
                        rs.getBigDecimal("discount"),
                        rs.getInt("stock"),
                        rs.getInt("seller_id"),
                        rs.getInt("category_id")
                );
            }
        }
        return null;
    }

    // Method to get all products by category ID
    public List<Product> getProductsByCategoryId(int categoryId) throws SQLException, ClassNotFoundException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE category_id = ?";
        try (Connection con = DBHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("image_url"),
                        rs.getBigDecimal("price"),
                        rs.getBigDecimal("discount"),
                        rs.getInt("stock"),
                        rs.getInt("seller_id"),
                        rs.getInt("category_id")
                ));
            }
        }
        return products;
    }

    // Method to update a product
    public boolean updateProduct(Product product) throws SQLException {
        String query = "UPDATE Products SET name = ?, description = ?, price = ?, discount = ?, stock = ?, category_id = ? WHERE product_id = ?";
        try (Connection con = DBHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setBigDecimal(4, product.getDiscount());
            ps.setInt(5, product.getStock());
            ps.setInt(6, product.getCategoryId());
            ps.setInt(7, product.getProductId());
            return ps.executeUpdate() > 0;
        }
    }
}

