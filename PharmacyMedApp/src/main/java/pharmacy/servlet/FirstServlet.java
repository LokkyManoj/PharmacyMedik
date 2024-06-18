package pharmacy.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import pharmacy.model.PharmacyUserReg;
import pharmacy.util.PharmacyUserDAO;


@WebServlet("/FirstServlet")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public FirstServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("hiiiii");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		System.out.println("Hiii");
	    String name = request.getParameter("name");
        long mobileNo = Long.parseLong(request.getParameter("mobileNumber"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        PharmacyUserReg user = new PharmacyUserReg();
        user.setName(name);
        user.setMobileNo(mobileNo);
        user.setMail(email);
        user.setPassword(password);

        PharmacyUserDAO userDao = new PharmacyUserDAO();
        try {
            userDao.saveUser(user);
            response.sendRedirect("PharmacyLogin.jsp");            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error occurred while registering user.");
        }
		
	}

}
