package com.olineshoppingplatform.olineshoppingplatform.HomePageData;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ProductListingsDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/oline_shopping_platform_war_exploded";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "199605";

    public static JsonArray getProductListings(String category, int pageSize, int offset) throws Exception {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT p.product_id, p.name, p.description, p.price, p.discount, p.stock, c.name AS category_name " +
                    "FROM Products p " +
                    "JOIN categories c ON p.category_id = c.id " +
                    "WHERE c.name = ? " +
                    "LIMIT ? OFFSET ?";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, category);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, offset);

            ResultSet rs = stmt.executeQuery();

            JsonArray products = new JsonArray();

            while (rs.next()) {
                JsonObject product = new JsonObject();
                product.addProperty("id", rs.getInt("product_id"));
                product.addProperty("name", rs.getString("name"));
                product.addProperty("description", rs.getString("description"));
                product.addProperty("price", rs.getDouble("price"));
                product.addProperty("discount", rs.getDouble("discount"));
                product.addProperty("stock", rs.getInt("stock"));
                product.addProperty("category", rs.getString("category_name"));
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage(), e);
        }
    }
}
