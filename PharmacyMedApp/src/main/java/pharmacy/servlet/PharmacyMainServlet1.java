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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PharmacyMainServlet1")
public class PharmacyMainServlet1 extends HttpServlet {
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
        List<Product> products = new ArrayList<>();
        PharmacyUserDAO productDAO = new PharmacyUserDAO();

        try {
            String category = "SkinCare Products";
            int isDeleted = 0;
            products = productDAO.getProductsByCategory(category,isDeleted);
            
            int cartItemCount = productDAO.getCartItemCount(userId);
            request.setAttribute("cartItemCount", cartItemCount);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("Message", "ERROR: " + ex.getMessage());
        }

        request.setAttribute("products", products);
        getServletContext().getRequestDispatcher("/PharmacyMain1.jsp").forward(request, response);
    }
}
