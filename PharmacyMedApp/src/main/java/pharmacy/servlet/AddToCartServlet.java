package pharmacy.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("product_id"));
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String dbURL = "jdbc:mysql://localhost:3306/medik";
            String dbUser = "root";
            String dbPass = "12345678";
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            String sql = "INSERT INTO add_cart (quantity, id, product_id) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);

            int row = statement.executeUpdate();
            if (row > 0) {
                response.sendRedirect("view_cart.jsp"); // Redirect to the cart view page
            } else {
                request.setAttribute("message", "Failed to add product to cart");
                getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "ERROR: " + ex.getMessage());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
