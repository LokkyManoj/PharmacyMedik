package pharmacy.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.jdbc.Blob;

import pharmacy.model.CartItem;
import pharmacy.util.PharmacyRegConnection;

@WebServlet("/ViewCartServlet")
public class ViewCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("id"));
        Integer userId = (Integer) session.getAttribute("id");

        if (userId == null) {
            // Redirect to login if user is not logged in
            response.sendRedirect("PharmacyLogin.jsp");
            return;
        }

        List<CartItem> cartItems = new ArrayList<>();

        int cartSize = 0; // Initialize cart size

        try {
            Connection conn = PharmacyRegConnection.getConnection();

            String sql = "SELECT a.product_id, a.product_name, a.product_price, a.product_image, c.quantity " +
                         "FROM pharmacy_admin a " +
                         "JOIN add_cart c ON a.product_id = c.product_id " +
                         "WHERE c.id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                int productPrice = resultSet.getInt("product_price");
                Blob productImage = (Blob) resultSet.getBlob("product_image");
                int quantity = resultSet.getInt("quantity");

                cartItems.add(new CartItem(productId, productName, productPrice, productImage, quantity));
                cartSize += quantity; // Increment cart size
            }

    
            session.setAttribute("cartItems", cartSize);

            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "ERROR: " + ex.getMessage());
        }

        request.setAttribute("cartItems", cartItems);
        getServletContext().getRequestDispatcher("/view_cart.jsp").forward(request, response);
    }
}