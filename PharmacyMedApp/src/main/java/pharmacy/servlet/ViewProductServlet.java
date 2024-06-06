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

@WebServlet("/ViewProductServlet")
public class ViewProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = new ArrayList<>();
        String searchQuery = request.getParameter("searchQuery");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String dbURL = "jdbc:mysql://localhost:3306/medik";
            String dbUser = "root";
            String dbPass = "12345678";
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            String sql = "SELECT * FROM pharmacy_admin";
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                sql += " WHERE product_name LIKE ?";
            }
            PreparedStatement statement = conn.prepareStatement(sql);
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                statement.setString(1, "%" + searchQuery + "%");
            }
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
                String mfdDate=resultSet.getString("mfd_date");
                String expDate=resultSet.getString("exp_date");

                
                System.out.println(mfdDate);


                products.add(new Product(productId, productName, productImage, productQuantity, productPrice,
                        description, uses, contains, category,mfdDate,expDate));
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("Message", "ERROR: " + ex.getMessage());
        }

        request.setAttribute("products", products);
        getServletContext().getRequestDispatcher("/view_products.jsp").forward(request, response);
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
        private String mfdDate;
        private String expDate;

        public Product(int productId, String productName, Blob productImage, int productQuantity, int productPrice,
                       String description, String uses, String contains, String category,String mfdDate,String expDate) {
            this.productId = productId;
            this.productName = productName;
            this.productImage = productImage;
            this.productQuantity = productQuantity;
            this.productPrice = productPrice;
            this.description = description;
            this.uses = uses;
            this.contains = contains;
            this.category = category;
            this.mfdDate=mfdDate;
            this.expDate=expDate;

        }

        // Getters
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
        public String getMfdDate() {
        	return mfdDate;
        }
        public String getExpDate() {
        	return expDate;
        }
    }
}
