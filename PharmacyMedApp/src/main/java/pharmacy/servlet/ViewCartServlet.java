package pharmacy.servlet;

import pharmacy.model.CartItem;
import pharmacy.util.PharmacyUserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ViewCartServlet")
public class ViewCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");

        if (userId == null) {
            response.sendRedirect("PharmacyLogin.jsp");
            return;
        }

        List<CartItem> cartItems = new ArrayList<>();
        PharmacyUserDAO cartItemDAO = new PharmacyUserDAO();

        try {
            cartItems = cartItemDAO.getCartItemsByUserId(userId, request);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "ERROR: " + ex.getMessage());
        }

        request.setAttribute("cartItems", cartItems);
        session.setAttribute("cartItems", cartItems);
        getServletContext().getRequestDispatcher("/view_cart.jsp").forward(request, response);
    }
}
