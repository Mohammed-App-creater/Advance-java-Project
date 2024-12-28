package com.olineshoppingplatform.olineshoppingplatform.Seller;

import com.google.gson.JsonObject;
import com.olineshoppingplatform.olineshoppingplatform.utils.Product;
import com.olineshoppingplatform.olineshoppingplatform.utils.ServletUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/seller/products")
public class ProductManagementServlet extends HttpServlet {
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int sellerId = (int) request.getSession().getAttribute("sellerId");
//        ProductDAO productDAO = new ProductDAO();
//
//        // Retrieve product list for seller
//        List<Product> products = productDAO.getProductsBySeller(sellerId);
//        request.setAttribute("products", products);
//
//        // Forward to the product management page
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/seller/product-management.jsp");
//        dispatcher.forward(request, response);
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int sellerId = (int) request.getSession().getAttribute("sellerId");
//        String action = request.getParameter("action");
//
//        ProductDAO productDAO = new ProductDAO();
//        if ("add".equals(action)) {
//            // Add product
//            String name = request.getParameter("name");
//            String description = request.getParameter("description");
//            BigDecimal price = new BigDecimal(request.getParameter("price"));
//            int stock = Integer.parseInt(request.getParameter("stock"));
//            String category = request.getParameter("category");
//
//            Product product = new Product(name, description, price, stock, category, sellerId);
//            productDAO.addProduct(product);
//        } else if ("edit".equals(action)) {
//            // Edit product
//            int productId = Integer.parseInt(request.getParameter("product_id"));
//            String name = request.getParameter("name");
//            String description = request.getParameter("description");
//            BigDecimal price = new BigDecimal(request.getParameter("price"));
//            int stock = Integer.parseInt(request.getParameter("stock"));
//
//            Product product = new Product(productId, name, description, price, stock);
//            productDAO.updateProduct(product);
//        } else if ("delete".equals(action)) {
//            // Delete product
//            int productId = Integer.parseInt(request.getParameter("product_id"));
//            productDAO.deleteProduct(productId);
//        }
//
//        // Redirect to the product management page
//        response.sendRedirect("/seller/products");
//    }
}
