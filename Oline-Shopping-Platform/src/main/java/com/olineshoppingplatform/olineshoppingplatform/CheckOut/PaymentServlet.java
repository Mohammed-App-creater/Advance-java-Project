package com.olineshoppingplatform.olineshoppingplatform.CheckOut;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class PaymentServlet extends HttpServlet {

    // Handling POST request to add payment
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            int orderId = Integer.parseInt(request.getParameter("order_id"));
            String bankName = request.getParameter("bank_name");
            double amount = Double.parseDouble(request.getParameter("amount"));

            // Call DAO to add payment
            boolean isAdded = PaymentDB.addPayment(orderId, bankName, amount);
            if (isAdded) {
                response.sendRedirect("paymentConfirmation.jsp?orderId=" + orderId);
            } else {
                response.getWriter().println("Error: Payment could not be added.");
            }
        } else if ("updateStatus".equals(action)) {
            int paymentId = Integer.parseInt(request.getParameter("payment_id"));
            String paymentStatus = request.getParameter("payment_status");

            // Call DAO to update payment status
            boolean isUpdated = PaymentDB.updatePaymentStatus(paymentId, paymentStatus);
            if (isUpdated) {
                response.sendRedirect("paymentStatus.jsp?paymentId=" + paymentId);
            } else {
                response.getWriter().println("Error: Payment status could not be updated.");
            }
        }
    }

    // Handling GET request to retrieve payment info
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("getPaymentByOrderId".equals(action)) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Payment payment = PaymentDB.getPaymentByOrderId(orderId);

            if (payment != null) {
                request.setAttribute("payment", payment);
                RequestDispatcher dispatcher = request.getRequestDispatcher("paymentDetails.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().println("Error: No payment information found for this order.");
            }
        } else if ("getPaymentsByUser".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            List<Payment> payments = PaymentDB.getPaymentsByUserId(userId);

            request.setAttribute("payments", payments);
            RequestDispatcher dispatcher = request.getRequestDispatcher("userPayments.jsp");
            dispatcher.forward(request, response);
        }
    }
}

