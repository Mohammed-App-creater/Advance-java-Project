package com.olineshoppingplatform.olineshoppingplatform.OrderConfermation;


import com.olineshoppingplatform.olineshoppingplatform.utils.Order;
import com.olineshoppingplatform.olineshoppingplatform.utils.OrderItem;
import com.olineshoppingplatform.olineshoppingplatform.utils.Shipping;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/orderConfirmation")
public class OrderConfirmationServlet extends HttpServlet {

    private OrderDB OrderDB;

    @Override
    public void init() throws ServletException {
        OrderDB = new OrderDB();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam == null || orderIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Missing orderId parameter\"}");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid orderId parameter\"}");
            return;
        }

        // Fetch order details
        Order order = OrderDB.getOrderDetails(orderId);
        if (order == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\":\"Order not found\"}");
            return;
        }

        // Fetch associated order items and shipping details
        List<OrderItem> orderItems = OrderDB.getOrderItems(orderId);
        Shipping shipping = OrderDB.getShippingDetails(orderId);

        // Build response object
        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("order", order);
        responseObject.put("items", orderItems);
        responseObject.put("shipping", shipping);

        // Convert to JSON and send response
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(responseObject);
        response.getWriter().write(jsonResponse);
    }
}

