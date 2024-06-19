package pharmacy.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pharmacy.util.PharmacyUserDAO;


@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public RemoveFromCartServlet() {
        super();
       
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        HttpSession session = request.getSession();
	        int cartId=(Integer) session.getAttribute("cartId");

	        PharmacyUserDAO cartItemDAO = new PharmacyUserDAO();

	        try {
	            boolean removed = cartItemDAO.removeFromCart(cartId);
	            if (removed) {
	                response.sendRedirect("ViewCartServlet");
	            } 
	        } catch (SQLException | ClassNotFoundException ex) {
	            ex.printStackTrace();
	            request.setAttribute("message", "ERROR: " + ex.getMessage());
	        }
	    }

}
