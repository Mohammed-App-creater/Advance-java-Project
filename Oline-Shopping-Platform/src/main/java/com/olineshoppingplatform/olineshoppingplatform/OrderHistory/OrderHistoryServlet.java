package com.olineshoppingplatform.olineshoppingplatform.OrderHistory;


import com.google.gson.Gson;
import com.olineshoppingplatform.olineshoppingplatform.utils.Order;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/orders/history")
public class OrderHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        int customerId;
        try {
            customerId = Integer.parseInt(request.getParameter("customerId")); // Get customer ID from request
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Invalid customer ID\"}");
            return;
        }

        OrderDB OrderDB = new OrderDB();

        try {
            List<Order> orders = OrderDB.getOrderHistory(customerId);

            Gson gson = new Gson();
            String json = gson.toJson(orders);
            out.write(json);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\": \"Unable to fetch order history\"}");
        }
    }
}
