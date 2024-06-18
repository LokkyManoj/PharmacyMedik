package pharmacy.servlet;

import pharmacy.model.Payment;
import pharmacy.model.Product;
import pharmacy.util.PharmacyUserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

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
            Product product = dao.getProductById(productId);
            if (product != null) {
                int newQuantity = product.getProductQuantity() - quantity;
                if (newQuantity >= 0) {
                    boolean updateSuccess = dao.updateProductQuantity(productId, newQuantity);
                    if (updateSuccess) {
                        boolean deleteSuccess = dao.deleteCartItemsByUserId(userId);
                        if (deleteSuccess) {
                            // Process payment
                            Date paymentDate = Date.valueOf(LocalDate.now());
                            String paymentMethod = (String) session.getAttribute("paymentMethod");
                            if (paymentMethod == null) {
                                paymentMethod = request.getParameter("paymentMethod"); // Get from request if not in session
                            }

                            Payment payment = new Payment();
                            payment.setPaymentDate(paymentDate);
                            payment.setPaymentMethod(paymentMethod);
                            payment.setAmount(total);
                            payment.setUserId(userId);
                            payment.setProductId(productId);

                            boolean paymentSuccess = dao.payment(payment);

                            if (paymentSuccess) {
                                session.setAttribute("amount", total);
                                response.sendRedirect("lastPage.jsp");
                            } else {
                                request.setAttribute("message", "Payment failed");
                                response.sendRedirect("error.jsp");
                            }
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
