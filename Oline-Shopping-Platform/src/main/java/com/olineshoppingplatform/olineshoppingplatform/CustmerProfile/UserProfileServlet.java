package com.olineshoppingplatform.olineshoppingplatform.CustmerProfile;


import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;

import com.google.gson.JsonObject;
import com.olineshoppingplatform.olineshoppingplatform.utils.ServletUtils;
import com.olineshoppingplatform.olineshoppingplatform.utils.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/userProfile")
public class UserProfileServlet extends HttpServlet {

    // Handle GET requests to display user profile information
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));  // Assuming userId is passed as a parameter

        UserDAO userDAO = new UserDAO();
        try {
            // Get user by ID from the database
            User user = userDAO.getUserById(userId);
            if (user != null) {
                // Set the user as an attribute in the request
                request.setAttribute("user", user);
                // Forward the request to the user profile JSP page
                request.getRequestDispatcher("/userProfile.jsp").forward(request, response);
            } else {
                // Redirect to an error page if the user is not found
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    // Handle POST requests to update user profile information
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User(userId, username, email, password);

        UserDAO userDAO = new UserDAO();
        try {
            // Update the user details in the database
            boolean updateSuccess = userDAO.updateUser(user);
            if (updateSuccess) {
                // Redirect to the profile page if the update is successful
                response.sendRedirect("userProfile?userId=" + userId);
            } else {
                // Redirect to an error page if the update fails
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
