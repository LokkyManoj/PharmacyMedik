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

@WebServlet("/PharmacyMainServlet2")
public class PharmacyMainServlet2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
            String category = "Fitness Supplements";
            int isDeleted = 0;
            products = productDAO.getProductsByCategory(category,isDeleted, request);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("Message", "ERROR: " + ex.getMessage());
        }

        request.setAttribute("products", products);
        getServletContext().getRequestDispatcher("/PharmacyMain.jsp").forward(request, response);
    }
}
