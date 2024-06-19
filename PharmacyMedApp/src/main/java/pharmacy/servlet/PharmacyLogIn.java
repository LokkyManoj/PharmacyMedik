package pharmacy.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LoginServlet")
public class PharmacyLogIn extends HttpServlet {
    private static final long serialVersionUID = 1L;

    
    public PharmacyLogIn() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String jdbcUrl = "jdbc:mysql://localhost:3306/medik";
        String dbUsername = "root";
        String dbPassword = "12345678";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "SELECT * FROM pharmacy_reg_user WHERE email = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                if (password.equals(result.getString("password"))) {
                    HttpSession session = request.getSession();
                    Integer userId = (Integer) session.getAttribute("id");

                    session.setAttribute("id", result.getInt("id"));
                    session.setAttribute("email", email);
                    session.setAttribute("name", result.getString("name"));

                    if (email.endsWith("@medik.com")) {
                        response.sendRedirect("adminPage.jsp"); 
                    } else {
                    	 
                        response.sendRedirect("PharmacyHome.jsp"); 
                    }
                } else {
                    request.setAttribute("passwordError", "Invalid Password");
                    request.getRequestDispatcher("PharmacyLogin.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("emailError", "Invalid Email");
                request.getRequestDispatcher("PharmacyLogin.jsp").forward(request, response);
            }

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}