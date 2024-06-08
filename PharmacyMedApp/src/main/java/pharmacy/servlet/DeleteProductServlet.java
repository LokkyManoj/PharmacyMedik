package pharmacy.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pharmacy.util.PharmacyUserDAO;

@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        System.out.println("delete post");

        try {
        	PharmacyUserDAO dao = new PharmacyUserDAO();
            boolean success = dao.deleteProduct(productId);
            if (success) {
                response.sendRedirect("ViewProductServlet");
            } else {
                request.setAttribute("message", "Failed to delete product");
                getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "ERROR: " + ex.getMessage());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
