package pharmacy.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pharmacy.util.PharmacyUserDAO;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("product_id"));
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
        	PharmacyUserDAO dao = new PharmacyUserDAO();
            boolean success = dao.addToCart(userId, productId, quantity);
            if (success) {
                response.sendRedirect("PharmacyMainServlet");
            } else {
                request.setAttribute("message", "Failed to add product to cart");
                getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "ERROR: " + ex.getMessage());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
