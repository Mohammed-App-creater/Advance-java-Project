package com.olineshoppingplatform.olineshoppingplatform.Admin;



import com.olineshoppingplatform.olineshoppingplatform.utils.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserManagementServlet extends HttpServlet {
    private UserManagementDAO userManagementDAO;

    @Override
    public void init() throws ServletException {
        try {
            userManagementDAO = new UserManagementDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Fetch all users
            List<User> users = userManagementDAO.getAllUsers();
            request.setAttribute("users", users);

            // Forward to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/manager/user_management.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load user data.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            int userId = Integer.parseInt(request.getParameter("userId"));

            if ("activate".equals(action)) {
                userManagementDAO.updateUserStatus(userId, true);
            } else if ("deactivate".equals(action)) {
                userManagementDAO.updateUserStatus(userId, false);
            } else if ("resetPassword".equals(action)) {
                String newPassword = request.getParameter("newPassword");
                userManagementDAO.resetPassword(userId, newPassword);
            }

            // Redirect back to user management page
            response.sendRedirect(request.getContextPath() + "/manager/user_management");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to perform the action.");
        }
    }
}
