package pharmacy.util;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pharmacy.model.CartItem;
import pharmacy.model.PharmacyUserReg;
import pharmacy.model.Product;

import pharmacy.util.PharmacyRegConnection;

public class PharmacyUserDAO {

	public void saveUser(PharmacyUserReg user) throws ClassNotFoundException, SQLException {
		String sql = "INSERT INTO pharmacy_reg_user (name, mobile_no, email, password) VALUES (?, ?, ?, ?)";

		try (Connection connection = PharmacyRegConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, String.valueOf(user.getMobileNo()));
			preparedStatement.setString(3, user.getMail());
			preparedStatement.setString(4, user.getPassword());

//            preparedStatement.executeUpdate();
			int rowsAffected = preparedStatement.executeUpdate();
			System.out.println("Rows affected: " + rowsAffected);
		}
	}

	public List<Product> getProducts(String searchQuery) throws ClassNotFoundException, SQLException {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM pharmacy_admin";
		if (searchQuery != null && !searchQuery.trim().isEmpty()) {
			sql += " WHERE product_name LIKE ?";
		}

		try (Connection connection = PharmacyRegConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			if (searchQuery != null && !searchQuery.trim().isEmpty()) {
				preparedStatement.setString(1, "%" + searchQuery + "%");
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int productId = resultSet.getInt("product_id");
					String productName = resultSet.getString("product_name");
					Blob productImage = resultSet.getBlob("product_image");
					int productQuantity = resultSet.getInt("product_quantity");
					int productPrice = resultSet.getInt("product_price");
					String description = resultSet.getString("description");
					String uses = resultSet.getString("uses");
					String contains = resultSet.getString("contains");
					String category = resultSet.getString("product_category");
					String mfdDate = resultSet.getString("mfd_date");
					String expDate = resultSet.getString("exp_date");

					products.add(new Product(productId, productName, productImage, productQuantity, productPrice,
							description, uses, contains, category, mfdDate, expDate));
				}
			}
		}
		return products;
	}

	public boolean addProduct(int productId, String productName, InputStream productImage, int productQuantity,
			int productPrice, String description, String uses, String contains, String category, String mfdDate,
			String expDate) throws ClassNotFoundException, SQLException {

		String sql = "INSERT INTO pharmacy_admin (product_id, product_name, product_image, product_quantity, product_price, description, uses, contains, product_category, mfd_date, exp_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = PharmacyRegConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, productId);
			statement.setString(2, productName);

			if (productImage != null) {
				statement.setBlob(3, productImage);
			}

			statement.setInt(4, productQuantity);
			statement.setInt(5, productPrice);
			statement.setString(6, description);
			statement.setString(7, uses);
			statement.setString(8, contains);
			statement.setString(9, category);
			statement.setString(10, mfdDate);
			statement.setString(11, expDate);

			int row = statement.executeUpdate();
			return row > 0;
		}
	}

	public boolean addToCart(int userId, int productId, int quantity) throws ClassNotFoundException, SQLException {
		String sql = "INSERT INTO add_cart (quantity, id, product_id) VALUES (?, ?, ?)";

		try (Connection connection = PharmacyRegConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, quantity);
			statement.setInt(2, userId);
			statement.setInt(3, productId);

			int row = statement.executeUpdate();
			return row > 0;
		}
	}
	
	 public boolean deleteProduct(int productId) throws ClassNotFoundException, SQLException {
	        String sql = "DELETE FROM pharmacy_admin WHERE product_id = ?";

	        try (Connection connection = PharmacyRegConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	            
	            statement.setInt(1, productId);

	            int rowsDeleted = statement.executeUpdate();
	            return rowsDeleted > 0;
	        }
	    }
	 
	 public List<Product> getProductsByCategory(String category) throws ClassNotFoundException, SQLException {
	        List<Product> products = new ArrayList<>();

	        String sql = "SELECT * FROM pharmacy_admin WHERE product_category = ?";
	        try (Connection conn = PharmacyRegConnection.getConnection();
	             PreparedStatement statement = conn.prepareStatement(sql)) {

	            statement.setString(1, category);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    int productId = resultSet.getInt("product_id");
	                    String productName = resultSet.getString("product_name");
	                    Blob productImage = resultSet.getBlob("product_image");
	                    int productQuantity = resultSet.getInt("product_quantity");
	                    int productPrice = resultSet.getInt("product_price");
	                    String description = resultSet.getString("description");
	                    String uses = resultSet.getString("uses");
	                    String contains = resultSet.getString("contains");
	                    String mfdDate = resultSet.getString("mfd_date");
	                    String expDate = resultSet.getString("exp_date");

	                    Product product = new Product(productId, productName, productImage, productQuantity, productPrice,
	                            description, uses, contains, category, mfdDate, expDate);
	                    products.add(product);
	                }
	            }
	        }

	        return products;
	    }
	 
	 public List<CartItem> getCartItemsByUserId(int userId) throws ClassNotFoundException, SQLException {
	        List<CartItem> cartItems = new ArrayList<>();

	        String sql = "SELECT a.product_id, a.product_name, a.product_price, a.product_image, c.quantity " +
	                     "FROM pharmacy_admin a " +
	                     "JOIN add_cart c ON a.product_id = c.product_id " +
	                     "WHERE c.id = ?";
	        try (Connection conn = PharmacyRegConnection.getConnection();
	             PreparedStatement statement = conn.prepareStatement(sql)) {

	            statement.setInt(1, userId);
	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                int productId = resultSet.getInt("product_id");
	                String productName = resultSet.getString("product_name");
	                int productPrice = resultSet.getInt("product_price");
	                Blob productImage = (Blob) resultSet.getBlob("product_image");
	                int quantity = resultSet.getInt("quantity");

	                CartItem cartItem = new CartItem(productId, productName, productPrice, (com.mysql.cj.jdbc.Blob) productImage, quantity);
	                cartItems.add(cartItem);
	            }
	        }

	        return cartItems;
	    }
}
