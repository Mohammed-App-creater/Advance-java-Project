package com.olineshoppingplatform.olineshoppingplatform.Forms;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/checkSession")
public class SessionCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set response type to JSON
        response.setContentType("application/json");

        // Get the session, create it if it doesn't exist
        HttpSession session = request.getSession(false); // Don't create a new session if it doesn't exist

        // Check if the session exists and if it has the "userId" attribute
        if (session != null && session.getAttribute("userId") != null) {
            // User is logged in, return a success response
            response.getWriter().write("{\"authenticated\": true, \"userId\": \"" + session.getAttribute("userId") + "\"}");
        } else {
            // User is not logged in
            response.getWriter().write("{\"authenticated\": false, \"message\": \"User not logged in\"}");
        }
    }
}

