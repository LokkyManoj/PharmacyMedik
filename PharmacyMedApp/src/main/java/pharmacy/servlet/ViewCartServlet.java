package pharmacy.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewCartServlet")
public class ViewCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("user_id"));
		List<CartItem> cartItems = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String dbURL = "jdbc:mysql://localhost:3306/medik";
			String dbUser = "root";
			String dbPass = "12345678";
			Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

			String sql = "SELECT a.product_id, a.product_name, a.product_price, c.quantity " + "FROM pharmacy_admin a "
					+ "JOIN add_cart c ON a.product_id = c.product_id " + "WHERE c.id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int productId = resultSet.getInt("product_id");
				String productName = resultSet.getString("product_name");
				int productPrice = resultSet.getInt("product_price");
				int quantity = resultSet.getInt("quantity");

				cartItems.add(new CartItem(productId, productName, productPrice, quantity));
			}

			conn.close();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			request.setAttribute("message", "ERROR: " + ex.getMessage());
		}

		request.setAttribute("cartItems", cartItems);
		getServletContext().getRequestDispatcher("/view_cart.jsp").forward(request, response);
	}

	public class CartItem {
		private int productId;
		private String productName;
		private int productPrice;
		private int quantity;

		public CartItem(int productId, String productName, int productPrice, int quantity) {
			this.productId = productId;
			this.productName = productName;
			this.productPrice = productPrice;
			this.quantity = quantity;
		}

		public int getProductId() {
			return productId;
		}

		public String getProductName() {
			return productName;
		}

		public int getProductPrice() {
			return productPrice;
		}

		public int getQuantity() {
			return quantity;
		}
	}
}
