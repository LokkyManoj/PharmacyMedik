package pharmacy.servlet;

import pharmacy.model.Product;
import pharmacy.util.PharmacyUserDAO;

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
    private static final String ERROR_PAGE = "error.jsp";
    private static final String MESSAGE = "message";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");

        if (userId == null) {
            response.sendRedirect("PharmacyLogin.jsp");
            return;
        }

        int productId = (int) session.getAttribute("product_id");
        int quantity = (int) session.getAttribute("quantity");
        int total = (int) session.getAttribute("total");

        PharmacyUserDAO dao = new PharmacyUserDAO();

        try {
            Product product = dao.getProductById(productId);
            if (product != null) {
                int newQuantity = product.getProductQuantity() - quantity;
                if (newQuantity >= 0) {
                    boolean updateSuccess = updateProductAndDeleteCart(dao, productId, userId, newQuantity);
                    if (updateSuccess) {
                        response.sendRedirect("lastPage.jsp");
                    } else {
                        request.setAttribute(MESSAGE, "Failed to process order");
                        response.sendRedirect(ERROR_PAGE);
                    }
                } else {
                    request.setAttribute(MESSAGE, "Insufficient product quantity");
                    response.sendRedirect(ERROR_PAGE);
                }
            } else {
                request.setAttribute(MESSAGE, "Product not found");
                response.sendRedirect(ERROR_PAGE);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute(MESSAGE, "ERROR: " + ex.getMessage());
            response.sendRedirect(ERROR_PAGE);
        }
    }

    private boolean updateProductAndDeleteCart(PharmacyUserDAO dao, int productId, int userId, int newQuantity)
            throws SQLException, ClassNotFoundException {
        boolean updateSuccess = dao.updateProductQuantity(productId, newQuantity);
        if (updateSuccess) {
            boolean deleteSuccess = dao.deleteCartItemsByUserId(userId);
            return deleteSuccess;
        }
        return false;
    }
}
