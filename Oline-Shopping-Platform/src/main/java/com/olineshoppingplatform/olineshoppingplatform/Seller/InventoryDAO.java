package com.olineshoppingplatform.olineshoppingplatform.Seller;

import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;
import com.olineshoppingplatform.olineshoppingplatform.utils.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    public List<Inventory> getInventory(int sellerId) {
        List<Inventory> inventory = new ArrayList<>();
        try (Connection conn = DBHelper.getConnection()) {
            String query = "SELECT * FROM inventory WHERE seller_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Inventory item = new Inventory(
                        rs.getInt("product_id"),
                        rs.getInt("quantity_available")
                );
                inventory.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public void updateInventory(int sellerId, int productId, int quantity) {
        try (Connection conn = DBHelper.getConnection()) {
            String query = "UPDATE inventory SET quantity_available = ? WHERE seller_id = ? AND product_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, quantity);
            stmt.setInt(2, sellerId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

