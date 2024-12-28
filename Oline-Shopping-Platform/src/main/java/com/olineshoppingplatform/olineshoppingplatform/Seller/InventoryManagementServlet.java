package com.olineshoppingplatform.olineshoppingplatform.Seller;

import com.google.gson.JsonObject;
import com.olineshoppingplatform.olineshoppingplatform.utils.Inventory;
import com.olineshoppingplatform.olineshoppingplatform.utils.ServletUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/seller/inventory")
public class InventoryManagementServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int sellerId = (int) request.getSession().getAttribute("sellerId");

        // Get inventory and low-stock products
        InventoryDAO inventoryDAO = new InventoryDAO();
        List<Inventory> inventoryList = inventoryDAO.getInventory(sellerId);
        request.setAttribute("inventory", inventoryList);

        // Forward to the inventory management page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/seller/inventory-management.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int sellerId = (int) request.getSession().getAttribute("sellerId");
        int productId = Integer.parseInt(request.getParameter("product_id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        InventoryDAO inventoryDAO = new InventoryDAO();
        inventoryDAO.updateInventory(sellerId, productId, quantity);

        // Redirect to the inventory management page
        response.sendRedirect("/seller/inventory");
    }
}
