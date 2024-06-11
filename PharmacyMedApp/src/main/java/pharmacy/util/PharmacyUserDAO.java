package pharmacy.util;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pharmacy.model.CartItem;
import pharmacy.model.Order;
import pharmacy.model.PharmacyUserReg;
import pharmacy.model.Product;
import pharmacy.model.Order;
import pharmacy.model.Payment;


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
	
	public void retrieveCart(int cartId,HttpServletRequest request) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM add_cart WHERE cart_id = ?";

        try (Connection connection = PharmacyRegConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, cartId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	HttpSession session = request.getSession();
                session.setAttribute("cart_id", resultSet.getInt("cart_id"));
                int retrievedCartId = resultSet.getInt("cart_id");
                int quantity = resultSet.getInt("quantity");
                int userId = resultSet.getInt("id");
                int productId = resultSet.getInt("product_id");

                // Now you can use the retrieved values as needed
                System.out.println("Cart ID: " + retrievedCartId);
                System.out.println("Quantity: " + quantity);
                System.out.println("User ID: " + userId);
                System.out.println("Product ID: " + productId);
            }
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
	 
	 public List<Product> getProductsByCategory(String category,HttpServletRequest request) throws ClassNotFoundException, SQLException {
	        List<Product> products = new ArrayList<>();

	        String sql = "SELECT * FROM pharmacy_admin WHERE product_category = ?";
	        try (Connection conn = PharmacyRegConnection.getConnection();
	             PreparedStatement statement = conn.prepareStatement(sql)) {

	            statement.setString(1, category);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                	
	                	HttpSession session = request.getSession();
	                    
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
	 
	 public List<CartItem> getCartItemsByUserId(int userId,HttpServletRequest request) throws ClassNotFoundException, SQLException {
	        List<CartItem> cartItems = new ArrayList<>();

	        String sql = "SELECT c.cart_id,a.product_id, a.product_name, a.product_price, a.product_image, c.quantity " +
	                     "FROM pharmacy_admin a " +
	                     "JOIN add_cart c ON a.product_id = c.product_id " +
	                     "WHERE c.id = ?";
	        try (Connection conn = PharmacyRegConnection.getConnection();
	             PreparedStatement statement = conn.prepareStatement(sql)) {

	            statement.setInt(1, userId);
	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	            	HttpSession session = request.getSession();
                    session.setAttribute("quantity", resultSet.getInt("quantity"));
                   
                    session.setAttribute("cartId", resultSet.getInt("cart_id"));
//                    System.out.println("pharmacy DAO : "+resultSet.getInt("cart_id"));
                    session.setAttribute("product_id", resultSet.getInt("product_id"));
                    System.out.println("first"+resultSet.getInt("product_id"));
                    
                    int cartId=resultSet.getInt("cart_id");
                    
	                int productId = resultSet.getInt("product_id");
	                String productName = resultSet.getString("product_name");
	                int productPrice = resultSet.getInt("product_price");
	                Blob productImage = (Blob) resultSet.getBlob("product_image");
	                int quantity = resultSet.getInt("quantity");

	                CartItem cartItem = new CartItem(productId, productName, productPrice, (com.mysql.cj.jdbc.Blob) productImage, quantity, cartId);
	                cartItems.add(cartItem);
	            }
	           
	        }

	        return cartItems;
	    }
	 
	
	 
	 
	 public boolean removeFromCart(int userId, int productId) throws ClassNotFoundException, SQLException {
		    String sql = "DELETE FROM add_cart WHERE id = ? AND product_id = ?";
		    try (Connection connection = PharmacyRegConnection.getConnection();
		         PreparedStatement statement = connection.prepareStatement(sql)) {
		        statement.setInt(1, userId);
		        statement.setInt(2, productId);
		        int rowsAffected = statement.executeUpdate();
		        return rowsAffected > 0;
		    }
		}
	 
//	 public void placeOrder(int productId, int userId) throws SQLException, ClassNotFoundException {
//		    try (Connection conn = PharmacyRegConnection.getConnection()) {
//		        // Get cart item details for the selected product and user
//		        CartItem cartItem = getCartItemDetails(productId, userId, conn);
//
//		        // Calculate the expected delivery date (one day ahead of the order date)
//		        LocalDate orderDate = LocalDate.now();
//		        LocalDate expectedDeliveryDate = orderDate.plusDays(1);
//
//		        // Insert order details into the orders table
//		        String sql = "INSERT INTO orders (product_id, order_date, quantity, status, expected_delivery_date, address, id) " +
//		                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
//		        try (PreparedStatement statement = conn.prepareStatement(sql)) {
//		            statement.setInt(1, cartItem.getProductId());
//		            statement.setDate(2, java.sql.Date.valueOf(orderDate)); // Current date as order date
//		            statement.setInt(3, cartItem.getQuantity());
//		            statement.setString(4, "Processing"); // Initial status
//		            statement.setDate(5, java.sql.Date.valueOf(expectedDeliveryDate)); // Expected delivery date
//		            statement.setString(6, null); // Address initially null
//		            statement.setInt(7, userId);
//
//		            statement.executeUpdate();
//		        }
//		    }
//		}
//
//	private CartItem getCartItemDetails(int productId, int userId, Connection conn) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	 
	 public boolean placeOrder(Order order) throws ClassNotFoundException, SQLException {
	        String sql = "INSERT INTO orders (product_id, order_date, quantity, status, expected_delivery_date,id, address) VALUES (?, ?, ?, ?, ?, ?, ?)";

	        try (Connection connection = PharmacyRegConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setInt(1, order.getProductId());
	            statement.setDate(2, order.getOrderDate());
	            statement.setInt(3, order.getQuantity());
	            statement.setString(4, order.getStatus());
	            statement.setDate(5, order.getExpectedDeliveryDate());
	            statement.setInt(6, order.getUserId());
	            statement.setString(7, order.getAddress());

	            int row = statement.executeUpdate();
	            return row > 0;
	        }
	    }
	 
	 public boolean payment(Payment payment) throws ClassNotFoundException, SQLException {
			String sql = "INSERT INTO payment (payment_date, payment_method,amount,id,product_id) VALUES (?, ?, ?,?,?)";

			try (Connection connection = PharmacyRegConnection.getConnection();
					PreparedStatement statement = connection.prepareStatement(sql)) {

				statement.setDate(1, payment.getPaymentDate());
				statement.setString(2,payment.getPaymentMethod());
				statement.setDouble(3,payment.getAmount());
				statement.setInt(4,payment.getUserId());
				statement.setInt(5,payment.getProductId());
				int row = statement.executeUpdate();

				return row > 0;
			}
		}
}
