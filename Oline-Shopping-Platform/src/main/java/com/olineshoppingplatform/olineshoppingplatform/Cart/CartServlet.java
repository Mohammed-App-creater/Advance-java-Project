package com.olineshoppingplatform.olineshoppingplatform.Cart;

import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;


@WebServlet("/cart/*")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = getUserIdFromSession(request); // Implement session handling
        List<CartItem> cartItems = CartDB.getCartItems(userId);

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(cartItems)); // Convert to JSON
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = getUserIdFromSession(request);
        CartItem cartItem = new Gson().fromJson(request.getReader(), CartItem.class);

        boolean success = CartDB.addToCart(userId, cartItem.getProductId(), cartItem.getQuantity());

        response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = getUserIdFromSession(request);
        int productId = Integer.parseInt(request.getPathInfo().substring(1));
        int newQuantity = Integer.parseInt(request.getParameter("quantity"));

        boolean success = CartDB.updateCartItem(userId, productId, newQuantity);

        response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = getUserIdFromSession(request);
        int productId = Integer.parseInt(request.getPathInfo().substring(1));

        boolean success = CartDB.removeFromCart(userId, productId);

        response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
    }

    private int getUserIdFromSession(HttpServletRequest request) {
        // Dummy implementation, replace with actual session handling
        return (int) request.getSession().getAttribute("userId");
    }
}

