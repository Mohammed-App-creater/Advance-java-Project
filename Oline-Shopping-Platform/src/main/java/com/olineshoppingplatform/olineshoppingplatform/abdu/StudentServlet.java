package com.olineshoppingplatform.olineshoppingplatform.abdu;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "StudentServlet", value = "/register")
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO(); // Initialize the DAO
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get input data from the form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Register the student
        boolean isRegistered = false;
        try {
            isRegistered = studentDAO.registerStudent(name, email, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (isRegistered) {
            out.println("<h3>Student registered successfully!</h3>");
        } else {
            out.println("<h3>Failed to register the student. Email might already be in use.</h3>");
        }
    }
}
