package pharmacy.util;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pharmacy.model.CartItem;
import pharmacy.model.Order;
import pharmacy.model.PharmacyUserReg;
import pharmacy.model.Product;
import pharmacy.model.Payment;




public class PharmacyUserDAO {

	public void saveUser(PharmacyUserReg user) throws ClassNotFoundException, SQLException {
		String sql = "INSERT INTO pharmacy_reg_user (name, mobile_no, email, password) VALUES (?, ?, ?, ?)";

		try (Connection connection = PharmacyRegConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, String.valueOf(user.getMobileNo()));
			preparedStatement.setString(3, user.getMail());
			preparedStatement.setString(4, user.getPassword());

			int rowsAffected = preparedStatement.executeUpdate();
		}
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
	 
	 public List<Product> getProductsByCategory(String category,int isDeleted) throws ClassNotFoundException, SQLException {
	        List<Product> products = new ArrayList<>();

	        String sql = "SELECT * FROM pharmacy_admin WHERE product_category = ? and  is_deleted=?";
	        try (Connection conn = PharmacyRegConnection.getConnection();
	             PreparedStatement statement = conn.prepareStatement(sql)) {

	            statement.setString(1, category);
	            statement.setInt(2, isDeleted);

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
                    

                    session.setAttribute("product_id", resultSet.getInt("product_id"));
                    
                    int cartId=resultSet.getInt("cart_id");
                    
	                int productId = resultSet.getInt("product_id");
	                String productName = resultSet.getString("product_name");
	                int productPrice = resultSet.getInt("product_price");
	                Blob productImage = resultSet.getBlob("product_image");
	                int quantity = resultSet.getInt("quantity");

	                CartItem cartItem = new CartItem(productId, productName, productPrice, (com.mysql.cj.jdbc.Blob) productImage, quantity, cartId);
	                cartItems.add(cartItem);
	            }
	           
	        }

	        return cartItems;
	    }
	 
	
	 
	 
	 public boolean removeFromCart(int cartId) throws ClassNotFoundException, SQLException {
		    String sql = "DELETE FROM add_cart WHERE cart_id = ?";
		    try (Connection connection = PharmacyRegConnection.getConnection();
		         PreparedStatement statement = connection.prepareStatement(sql)) {

		        statement.setInt(1,cartId);
		        int rowsAffected = statement.executeUpdate();
		       
		        return rowsAffected > 0;
		    }
		}
	 

	 
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
	 public Product getProductById(int productId) throws SQLException, ClassNotFoundException {
	        String query = "SELECT * FROM pharmacy_admin WHERE product_id = ?";
	        try (Connection con = PharmacyRegConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setInt(1, productId);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                Product product = new Product();
	                product.setProductId(rs.getInt("product_id"));
	                product.setProductName(rs.getString("product_name"));
	                product.setProductQuantity(rs.getInt("product_quantity"));
	                return product;
	            }
	        }
	        return null;
	    }

	    public boolean updateProductQuantity(int productId, int newQuantity) throws SQLException, ClassNotFoundException {
	        String query = "UPDATE pharmacy_admin SET product_quantity = ? WHERE product_id = ?";
	        try (Connection con = PharmacyRegConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setInt(1, newQuantity);
	            ps.setInt(2, productId);
	            int rowsUpdated = ps.executeUpdate();
	            return rowsUpdated > 0;
	        }
	    }
	    
	    public boolean deleteCartItemsByUserId(int userId) throws SQLException, ClassNotFoundException {
	        String sql = "DELETE FROM add_cart WHERE id = ?";
	        try (Connection connection =PharmacyRegConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, userId);
	            int rowsAffected = statement.executeUpdate();
	            return rowsAffected > 0;
	        }
	    }
	    
	    public boolean updateCartQuantity(int cartId, int quantity) throws ClassNotFoundException, SQLException {
	        String sql = "UPDATE add_cart SET quantity = ? WHERE cart_id = ?";
	        try (Connection connection = PharmacyRegConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, quantity);
	            statement.setInt(2, cartId);
	            int rowsUpdated = statement.executeUpdate();
	            return rowsUpdated > 0;
	        }
	    }
	    
	    public boolean softDeleteProduct(int productId) throws SQLException, ClassNotFoundException {
	        String sql = "UPDATE pharmacy_admin SET is_deleted = 1 WHERE product_id = ?";
	        try (Connection conn = PharmacyRegConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, productId);
	            int rowsUpdated = stmt.executeUpdate();
	            return rowsUpdated > 0;
	        }
	    }
	    
	    public List<Product> getProducts(String searchQuery) throws SQLException, ClassNotFoundException {
	        String sql = "SELECT * FROM pharmacy_admin WHERE is_deleted = 0";
	        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
	            sql += " AND product_name LIKE ?";
	        }

	        try (Connection conn = PharmacyRegConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
	                stmt.setString(1, "%" + searchQuery + "%");
	            }

	            ResultSet rs = stmt.executeQuery();
	            List<Product> products = new ArrayList<>();
	            while (rs.next()) {
	                Product product = new Product(
	                    rs.getInt("product_id"),
	                    rs.getString("product_name"),
	                    rs.getBlob("product_image"),
	                    rs.getInt("product_quantity"),
	                    rs.getInt("product_price"),
	                    rs.getString("description"),
	                    rs.getString("uses"),
	                    rs.getString("contains"),
	                    rs.getString("product_category"),
	                    rs.getString("mfd_date"),
	                    rs.getString("exp_date")
	                );
	                products.add(product);
	            }
	            return products;
	        }
	    }
	    
	    public int getCartItemCount(int userId) throws SQLException, ClassNotFoundException {
	        int count = 0;
	        String sql = "select count(*) as cart_count from add_cart where id = ?";
	        
	        try (Connection conn = PharmacyRegConnection.getConnection();
		             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setInt(1, userId);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    count = rs.getInt("cart_count");
	                }
	            }
	        }
	        
	        return count;
	    }
	 
}
