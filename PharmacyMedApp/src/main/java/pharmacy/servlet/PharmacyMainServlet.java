package pharmacy.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PharmacyMainServlet")
public class PharmacyMainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String dbURL = "jdbc:mysql://localhost:3306/medik";
            String dbUser = "root";
            String dbPass = "12345678";
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            String sql = "SELECT * FROM pharmacy_admin WHERE product_category = 'Medicine'";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

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

                products.add(new Product(productId, productName, productImage, productQuantity, productPrice,
                        description, uses, contains, category));
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("Message", "ERROR: " + ex.getMessage());
        }

        request.setAttribute("products", products);
        getServletContext().getRequestDispatcher("/PharmacyMain.jsp").forward(request, response);
    }

    public class Product {
        private int productId;
        private String productName;
        private Blob productImage;
        private int productQuantity;
        private int productPrice;
        private String description;
        private String uses;
        private String contains;
        private String category;

        public Product(int productId, String productName, Blob productImage, int productQuantity, int productPrice,
                       String description, String uses, String contains, String category) {
            this.productId = productId;
            this.productName = productName;
            this.productImage = productImage;
            this.productQuantity = productQuantity;
            this.productPrice = productPrice;
            this.description = description;
            this.uses = uses;
            this.contains = contains;
            this.category = category;
        }

        public int getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public Blob getProductImage() {
            return productImage;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public String getDescription() {
            return description;
        }

        public String getUses() {
            return uses;
        }

        public String getContains() {
            return contains;
        }

        public String getCategory() {
            return category;
        }
    }
}
