package pharmacy.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/AddProductServlet")
@MultipartConfig(maxFileSize = 16177215) 
public class AddImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve values from the form
        int productId = Integer.parseInt(request.getParameter("product_id"));
        String productName = request.getParameter("product_name");
        InputStream inputStream = null; // Input stream of the upload file
        Part filePart = request.getPart("product_image");
        if (filePart != null) {
            // Debugging messages
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
            // Obtain the input stream of the uploaded file
            inputStream = filePart.getInputStream();
        }
        int productQuantity = Integer.parseInt(request.getParameter("product_quantity"));
        int productPrice = Integer.parseInt(request.getParameter("product_price"));
        String description = request.getParameter("description");
        String uses = request.getParameter("uses");
        String contains = request.getParameter("contains");
        String category=request.getParameter("product_category");
        String mfdDate=request.getParameter("mfd_date");
       String expDate=request.getParameter("exp_date");
        
        

        String message = null; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String dbURL = "jdbc:mysql://localhost:3306/medik";
            String dbUser = "root";
            String dbPass = "12345678";
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            String sql = "INSERT INTO pharmacy_admin (product_id, product_name, product_image, product_quantity, product_price, description, uses, contains,product_category,mfd_date,exp_date) values (?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, productId);
            statement.setString(2, productName);

            if (inputStream != null) {
                // Fetches input stream of the upload file for the blob column
                statement.setBlob(3, inputStream);
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
            if (row > 0) {
                message = "File uploaded and saved into database";
            }
            conn.close();
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Sets the message in request scope
        request.setAttribute("Message", message);
        // Forwards to the message page
        getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }
}
