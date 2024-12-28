package com.olineshoppingplatform.olineshoppingplatform.Seller;

import com.google.gson.JsonObject;
import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;
import com.olineshoppingplatform.olineshoppingplatform.utils.ServletUtils;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.eclipse.persistence.config.TargetDatabase.Database;

public class DashboardDAO {
    public DashboardSummary getDashboardSummary(int sellerId) {
        DashboardSummary summary = new DashboardSummary();

        try (Connection conn = DBHelper.getConnection()) {
            String query = "SELECT SUM(total_amount) AS total_sales, COUNT(*) AS total_orders " +
                    "FROM orders WHERE seller_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                summary.setTotalSales(rs.getBigDecimal("total_sales"));
                summary.setTotalOrders(rs.getInt("total_orders"));
            }

            // Query to get inventory count
            query = "SELECT COUNT(*) AS total_inventory FROM inventory WHERE seller_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, sellerId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                summary.setTotalInventory(rs.getInt("total_inventory"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return summary;
    }
}
