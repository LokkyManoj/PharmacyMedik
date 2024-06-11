package pharmacy.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pharmacy.model.Payment;
import pharmacy.util.PharmacyUserDAO;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        if ("placeOrder".equals(action)) {
            Date paymentDate = Date.valueOf(LocalDate.now());
            String paymentMethod = request.getParameter("paymentMethod");

            int amount = Integer.parseInt(request.getParameter("amount"));
            int userId = (int) session.getAttribute("id");
            int productId = (int) session.getAttribute("product_id");
            String productName = (String) session.getAttribute("productName");
            Date expectedDeliveryDate = (Date) session.getAttribute("expectedDeliveryDate");
            String address = (String) session.getAttribute("address");

            Payment payment = new Payment();
            payment.setPaymentDate(paymentDate);
            payment.setPaymentMethod(paymentMethod);
            payment.setAmount(amount);
            payment.setUserId(userId);
            payment.setProductId(productId);

            try {
                PharmacyUserDAO dao = new PharmacyUserDAO();
                boolean success = dao.payment(payment);

                if (success) {
                    session.setAttribute("productName", productName);
                    session.setAttribute("amount", amount);
                    session.setAttribute("expectedDeliveryDate", expectedDeliveryDate);
                    session.setAttribute("address", address);
                    
                    response.sendRedirect("paymentSuccess.jsp");
                } else {
                    request.setAttribute("message", "Payment failed");
                    getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                request.setAttribute("message", "An error occurred: " + ex.getMessage());
                getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }
}
