package pharmacy.servlet;

import pharmacy.util.PharmacyUserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int cartId = Integer.parseInt(request.getParameter("cartId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        PharmacyUserDAO dao = new PharmacyUserDAO();
        try {
            boolean updateSuccess = dao.updateCartQuantity(cartId, quantity);
            if (updateSuccess) {
                session.setAttribute("quantity", quantity);
                response.sendRedirect("ViewCartServlet");
            } else {
                request.setAttribute("message", "Failed to update cart quantity");
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "ERROR: " + ex.getMessage());
            response.sendRedirect("error.jsp");
        }
    }
}
