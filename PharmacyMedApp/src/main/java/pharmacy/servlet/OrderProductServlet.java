package pharmacy.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pharmacy.model.CartItem;
import pharmacy.model.Order;
import pharmacy.util.PharmacyUserDAO;

@WebServlet("/OrderProductServlet")
public class OrderProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String EXPECTED_DELIVERY_DATE = "expectedDeliveryDate";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String action = request.getParameter("action");
        if ("buy".equals(action)) {
        	
        	List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
            int totalQuantity = 0;
            int total = 0;

            for (CartItem item : cartItems) {
                totalQuantity += item.getQuantity();
                total += item.getProductPrice() * item.getQuantity();
            }
            int productId = (int) session.getAttribute("product_id");
            int userId = (int) session.getAttribute("id");
            int quantity = (int) session.getAttribute("quantity");
             total=Integer.parseInt(request.getParameter("total"));
            
            String status = "Pending";
            Date orderDate = Date.valueOf(LocalDate.now());
            Date expectedDeliveryDate = Date.valueOf(LocalDate.now().plusDays(5));

            request.setAttribute("productId", productId);
            request.setAttribute("userId", userId);
            request.setAttribute("quantity", quantity);
            request.setAttribute("status", status);
            session.setAttribute("total", total);
            request.setAttribute("total", total);
            request.setAttribute("totalQuantity", totalQuantity);
            
            request.setAttribute("orderDate", orderDate);
            request.setAttribute(EXPECTED_DELIVERY_DATE, expectedDeliveryDate);


            getServletContext().getRequestDispatcher("/order.jsp").forward(request, response);
        } else {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String status = request.getParameter("status");
            Date orderDate = Date.valueOf(request.getParameter("orderDate"));
            Date expectedDeliveryDate = Date.valueOf(request.getParameter(EXPECTED_DELIVERY_DATE));
            session.setAttribute(EXPECTED_DELIVERY_DATE,expectedDeliveryDate );
            String address = request.getParameter("address");
           session.setAttribute("address", address);


            Order order = new Order();
            order.setProductId(productId);
            order.setUserId(userId);
            order.setQuantity(quantity);
            order.setStatus(status);
            order.setOrderDate(orderDate);
            order.setExpectedDeliveryDate(expectedDeliveryDate);
            order.setAddress(address);

            try {
                PharmacyUserDAO dao = new PharmacyUserDAO();
                boolean success = dao.placeOrder(order);
                if (success) {
                    response.sendRedirect("ordersuccess.jsp");
                } else {
                    request.setAttribute("message", "Failed to place the order");
                    getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
                request.setAttribute("message", "ERROR: " + ex.getMessage());
                getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }
}
