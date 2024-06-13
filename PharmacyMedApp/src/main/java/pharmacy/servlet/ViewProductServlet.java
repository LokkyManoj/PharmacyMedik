package pharmacy.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pharmacy.model.Product;
import pharmacy.util.PharmacyUserDAO;

@WebServlet("/ViewProductServlet")
public class ViewProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("searchQuery");

        try {
            PharmacyUserDAO dao = new PharmacyUserDAO();
            List<Product> products = dao.getProducts(searchQuery);
            request.setAttribute("products", products);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("Message", "ERROR: " + ex.getMessage());
        }

        getServletContext().getRequestDispatcher("/view_products.jsp").forward(request, response);
    }
}