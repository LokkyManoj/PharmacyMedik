package pharmacy.servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pharmacy.model.Product;

@WebServlet("/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("productId"));

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String dbURL = "jdbc:mysql://localhost:3306/medik";
			String dbUser = "root";
			String dbPass = "12345678";
			Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

			String sql = "SELECT * FROM pharmacy_admin WHERE product_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, productId);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String productName = resultSet.getString("product_name");
				Blob productImage = resultSet.getBlob("product_image");
				int productQuantity = resultSet.getInt("product_quantity");
				int productPrice = resultSet.getInt("product_price");
				String description = resultSet.getString("description");
				String uses = resultSet.getString("uses");
				String contains = resultSet.getString("contains");
				String category = resultSet.getString("product_category");
				String mfdDate=resultSet.getString("mfd_date");
				String expDate=resultSet.getString("exp_date");


				Product product = new Product(productId, productName, productImage, productQuantity, productPrice,
						description, uses, contains, category,mfdDate,expDate);

				request.setAttribute("product", product);
			}

			conn.close();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			request.setAttribute("Message", "ERROR: " + ex.getMessage());
		}

		getServletContext().getRequestDispatcher("/update_product.jsp").forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("productId"));
		String productName = request.getParameter("productName");
		int productQuantity=Integer.parseInt(request.getParameter("product_quantity"));
		String productPrice = request.getParameter("productPrice");
		String description = request.getParameter("description");
		String uses = request.getParameter("uses");
		String contains = request.getParameter("contains");
		String category = request.getParameter("category");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String dbURL = "jdbc:mysql://localhost:3306/medik";
			String dbUser = "root";
			String dbPass = "12345678";
			Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

			String updateQuery = "UPDATE pharmacy_admin SET product_name = ?,product_quantity=?, product_price = ?, description = ?, uses = ?, contains = ?, product_category = ? WHERE product_id = ?";
			PreparedStatement statement = conn.prepareStatement(updateQuery);
			statement.setString(1, productName);
			statement.setInt(2, productQuantity);

			statement.setString(3, productPrice);
			statement.setString(4, description);
			statement.setString(5, uses);
			statement.setString(6, contains);
			statement.setString(7, category);
			statement.setInt(8, productId);

			statement.executeUpdate();
			conn.close();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		response.sendRedirect("ViewProductServlet");
	}
}
