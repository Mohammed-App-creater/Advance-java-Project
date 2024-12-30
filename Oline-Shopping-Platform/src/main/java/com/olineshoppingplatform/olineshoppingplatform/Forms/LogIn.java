package com.olineshoppingplatform.olineshoppingplatform.Forms;

import com.google.gson.JsonObject;
import com.olineshoppingplatform.olineshoppingplatform.utils.ServletUtils;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(name = "LogIn", value = "/LogIn")
public class LogIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set CORS headers using utility method
        ServletUtils.setCorsHeaders(response);

        // Parse the request body using utility method
        JsonObject jsonObject = ServletUtils.parseJsonRequestBody(request, response);
        if (jsonObject == null) return; // If parsing failed, response is already handled.

        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();

        PrintWriter out = response.getWriter();

        try {
            System.out.println("Email: " + email + " Password: " + password);
            boolean authenticated = LogInDB.authenticate(email, password);
            System.out.println("Authenticating: " + authenticated);

            if (authenticated) {
                // Create session or get the existing session
                HttpSession session = request.getSession(true);
                session.setAttribute("userEmail", email);

                // Log session info for debugging
                System.out.println("Session ID: " + session.getId());
                System.out.println("User Email set in session: " + session.getAttribute("userEmail"));

                // Fetch userId (check if it's null or empty)
                String userIdStr = LogInDB.getUserId(email, password);
                if (userIdStr != null && !userIdStr.isEmpty()) {
                    int userId = Integer.parseInt(userIdStr);
                    session.setAttribute("userId", userId);
                    System.out.println("User ID set in session: " + userId);
                } else {
                    System.err.println("Failed to retrieve userId for email: " + email);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.write("{\"error\": \"Failed to retrieve user ID.\"}");
                    return;
                }

                // Set session timeout to 72 hours (in seconds)
                session.setMaxInactiveInterval(72 * 60); // 72 mint in seconds
                out.write("{\"success\": true}");
            } else {
                out.write("{\"success\": false}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\": \"An internal error occurred.\"}");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
