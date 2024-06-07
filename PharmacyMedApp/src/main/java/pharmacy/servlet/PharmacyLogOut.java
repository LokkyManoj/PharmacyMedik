package pharmacy.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class PharmacyLogOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

		public PharmacyLogOut() {
		super();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(false); // Fetch the existing session

		if (session != null) {
			session.invalidate(); 
		}

		response.sendRedirect("PharmacyHome.jsp"); 
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

		HttpSession session = request.getSession(false); 

		if (session != null) {
			session.invalidate(); 
		}

		response.sendRedirect("PharmacyHome.jsp"); 
	}
}
