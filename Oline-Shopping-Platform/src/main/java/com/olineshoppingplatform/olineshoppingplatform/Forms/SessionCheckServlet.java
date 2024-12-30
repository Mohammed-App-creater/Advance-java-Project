package com.olineshoppingplatform.olineshoppingplatform.Forms;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "SessionCheckServlet", value = "/checkSession")
public class SessionCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set response type to JSON
        response.setContentType("application/json");
        // Get the session without creating a new one
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Retrieve attributes
            Object userId = session.getAttribute("userId");
            Object userEmail = session.getAttribute("userEmail");

            if (userId != null && userEmail != null) {
                // User is authenticated
                response.getWriter().write("{\"authenticated\": true, \"userId\": \"" + userId + "\", \"userEmail\": \"" + userEmail + "\"}");
            } else {
                // Session exists but attributes are missing
                response.getWriter().write("{\"authenticated\": false, \"message\": \"Session data incomplete\"}");

            }
        } else {
            // No session exists
            response.getWriter().write("{\"authenticated\": false, \"message\": \"User not logged in\"}");
        }
    }
}
