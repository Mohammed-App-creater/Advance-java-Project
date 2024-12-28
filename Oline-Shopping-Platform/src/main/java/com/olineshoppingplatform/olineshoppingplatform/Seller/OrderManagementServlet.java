package com.olineshoppingplatform.olineshoppingplatform.Seller;

import com.google.gson.JsonObject;
import com.olineshoppingplatform.olineshoppingplatform.utils.Order;
import com.olineshoppingplatform.olineshoppingplatform.utils.ServletUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/seller/orders")
public class OrderManagementServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int sellerId = (int) request.getSession().getAttribute("sellerId");

        // Get orders related to the seller
        OrderDAO orderDAO = new OrderDAO();
//        List<Order> orders = orderDAO.getOrdersBySeller(sellerId);
//        request.setAttribute("orders", orders);

        // Forward to the order management page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/seller/order-management.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("order_id"));
        String status = request.getParameter("status");

        OrderDAO orderDAO = new OrderDAO();
        orderDAO.updateOrderStatus(orderId, status);

        // Redirect to the order management page
        response.sendRedirect("/seller/orders");
    }
}
