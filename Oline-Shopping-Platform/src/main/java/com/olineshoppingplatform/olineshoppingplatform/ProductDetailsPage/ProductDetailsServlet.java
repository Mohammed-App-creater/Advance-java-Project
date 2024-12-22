package com.olineshoppingplatform.olineshoppingplatform.ProductDetailsPage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

public class ProductDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));  // Get product ID from query parameter

        Product product = ProductDB.getProductById(productId);
        List<Review> reviews = ProductDB.getProductReviews(productId);

        if (product != null) {
            request.setAttribute("product", product);
            request.setAttribute("reviews", reviews);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/productDetails.jsp");
            dispatcher.forward(request, response);  // Forward to JSP page for rendering
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
        }
    }
}

