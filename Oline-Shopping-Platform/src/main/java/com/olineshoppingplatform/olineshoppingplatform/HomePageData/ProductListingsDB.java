package com.olineshoppingplatform.olineshoppingplatform.HomePageData;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;

public class ProductListingsDB {
    public static JsonArray getProductListings(String category, int pageSize, int offset) throws Exception {
    String query;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DBHelper.getConnection()) {

            if (category == null || category.equals("")) {
                query = """
            SELECT 
                p.product_id, 
                p.name, 
                p.description, 
                p.price, 
                p.discount, 
                p.stock, 
                p.image_url, 
                c.name AS category_name,
                COALESCE(ROUND(AVG(r.rating)), 0) AS rating
            FROM 
                Products p
            JOIN 
                categories c ON p.category_id = c.id
            LEFT JOIN 
                Ratings r ON p.product_id = r.product_id
            GROUP BY 
                p.product_id, c.name
            LIMIT ? OFFSET ?;
            """;
            } else {
                query = """
            SELECT 
                p.product_id, 
                p.name, 
                p.description, 
                p.price, 
                p.discount, 
                p.stock, 
                p.image_url, 
                c.name AS category_name,
                COALESCE(ROUND(AVG(r.rating)), 0) AS rating
            FROM 
                Products p
            JOIN 
                categories c ON p.category_id = c.id
            LEFT JOIN 
                Ratings r ON p.product_id = r.product_id
            WHERE 
                c.name = ?
            GROUP BY 
                p.product_id, c.name
            LIMIT ? OFFSET ?;
            """;
            }


// Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);

// Set parameters based on the category
            if (category != null && !category.equals("")) {
                stmt.setString(1, category);
                stmt.setInt(2, pageSize);
                stmt.setInt(3, offset);
            } else {
                stmt.setInt(1, pageSize);
                stmt.setInt(2, offset);
            }


            ResultSet rs = stmt.executeQuery();

            JsonArray products = new JsonArray();
            int columnCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                JsonObject product = new JsonObject();
                product.addProperty("id", rs.getInt("product_id"));
                product.addProperty("name", rs.getString("name"));
                product.addProperty("description", rs.getString("description"));
                product.addProperty("price", rs.getDouble("price"));
                product.addProperty("discount", rs.getDouble("discount"));
                product.addProperty("stock", rs.getInt("stock"));
                product.addProperty("category", rs.getString("category_name"));
                product.addProperty("image_url", rs.getString("image_url"));
                product.addProperty("rating", rs.getInt("rating"));
                products.add(product);
            }
            System.out.println(products + " mope nope");//debug

            return products;
        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage(), e);
        }
    }
}
