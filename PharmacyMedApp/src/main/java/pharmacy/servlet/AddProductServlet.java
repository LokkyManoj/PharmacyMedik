package pharmacy.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import pharmacy.util.PharmacyUserDAO;

@WebServlet("/AddImageServlet")
@MultipartConfig(maxFileSize = 16177215) 
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session=request.getSession();
        int productId = Integer.parseInt(request.getParameter("product_id"));
        String productName = request.getParameter("product_name");
        InputStream inputStream = null; 
        Part filePart = request.getPart("product_image");
        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }
        int productQuantity = Integer.parseInt(request.getParameter("product_quantity"));
        int productPrice = Integer.parseInt(request.getParameter("product_price"));
        String description = request.getParameter("description");
        String uses = request.getParameter("uses");
        String contains = request.getParameter("contains");
        String category = request.getParameter("product_category");
        String mfdDate = request.getParameter("mfd_date");
        String expDate = request.getParameter("exp_date");
        
       session.setAttribute("exp_date", expDate);
        

        String message = null; 

        try {
            PharmacyUserDAO dao = new PharmacyUserDAO();
            boolean success = dao.addProduct(productId, productName, inputStream, productQuantity, productPrice, description, uses, contains, category, mfdDate, expDate);
            if (success) {
                message = "Product uploaded and saved into database";
            } else {
                message = "Failed to upload and save product";
            }
        } catch (SQLException | ClassNotFoundException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        }

        request.setAttribute("Message", message);
        getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }
}
