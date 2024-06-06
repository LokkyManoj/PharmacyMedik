package pharmacy.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        System.out.println("delete post");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String dbURL = "jdbc:mysql://localhost:3306/medik";
            String dbUser = "root";
            String dbPass = "12345678";
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            String sql = "DELETE FROM pharmacy_admin WHERE product_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, productId);

            statement.executeUpdate();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        response.sendRedirect("ViewProductServlet");
    }
}
