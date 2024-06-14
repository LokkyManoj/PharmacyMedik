package pharmacy.servlet;

import pharmacy.util.PharmacyUserDAO;
import pharmacy.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/PlaceOrderAndDeleteCartServlet")
public class PlaceOrderAndDeleteCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int productId = (int) session.getAttribute("product_id");
        int cartId = (int) session.getAttribute("cartId");
        int quantity = (int) session.getAttribute("quantity");
        int total = (int) session.getAttribute("total");
        Integer userId = (Integer) session.getAttribute("id");

        if (userId == null) {
            response.sendRedirect("PharmacyLogin.jsp");
            return;
        }

        PharmacyUserDAO dao = new PharmacyUserDAO();
        try {
            // Place the order
            Product product = dao.getProductById(productId);
            if (product != null) {
                int newQuantity = product.getProductQuantity() - quantity;
                if (newQuantity >= 0) {
                    boolean updateSuccess = dao.updateProductQuantity(productId, newQuantity);
                    if (updateSuccess) {
                        // Delete the cart items
                        boolean deleteSuccess = dao.deleteCartItemsByUserId(userId);
                        if (deleteSuccess) {
                            session.setAttribute("amount", total);
                            response.sendRedirect("lastPage.jsp");
                        } else {
                            request.setAttribute("message", "Failed to delete cart items");
                            response.sendRedirect("error.jsp");
                        }
                    } else {
                        request.setAttribute("message", "Failed to update product quantity");
                        response.sendRedirect("error.jsp");
                    }
                } else {
                    request.setAttribute("message", "Insufficient product quantity");
                    response.sendRedirect("error.jsp");
                }
            } else {
                request.setAttribute("message", "Product not found");
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "ERROR: " + ex.getMessage());
            response.sendRedirect("error.jsp");
        }
    }
}
