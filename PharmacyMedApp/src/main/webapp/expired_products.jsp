<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Blob"%>
<%@ page import="java.util.Base64"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="pharmacy.model.Product"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.IOException"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Medik Pharmacy - Expired Products</title>
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
<style>
.back-icon-container {
    position: absolute;
    top: 20px;
    left: 20px;
}

.back-icon-container i {
    color: black;
    font-size: 24px;
    cursor: pointer;
}
.container {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
}

.product-card {
    border: 1px solid #ccc;
    padding: 16px;
    margin: 16px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    width: 45%;
    box-sizing: border-box;
}

.product-card img {
    max-width: 50%;
    height: 30%;
}

.product-info {
    margin-top: 12px;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 12px;
}

table, th, td {
    border: 1px solid #ddd;
}

th, td {
    padding: 8px;
    text-align: left;
}

.search-container {
    display: flex;
    align-items: center;
    border: 1px solid #ccc;
    border-radius: 8px;
    overflow: hidden;
    margin-bottom: 20px;
width: 500px;
 margin-left:50px;    
}

.search-container input[type="text"] {
    flex: 1;
    border: none;
    padding: 10px;
    font-size: 16px;
}

.search-container button {
    background-color: #007bff;
    color: #fff;
    border: none;
    padding: 10px 15px;
    cursor: pointer;
}

.search-container button:hover {
    background-color: #0056b3;
}

.product-actions {
    margin-top: 12px;
    display: flex;
    justify-content: space-between;
}

.product-actions button {
    background-color:#3d7676;
    color: #fff;
    border: none;
    padding: 8px 12px;
    cursor: pointer;
    border-radius: 4px;
}

.product-actions button.delete {
    background-color: #dc3545;
}
</style>
</head>
<body>
<div class="back-icon-container">
        <a href="adminPage.jsp"><i class='bx bx-arrow-back'></i></a>
    </div>
    <form action="ViewProductServlet" method="get">
        <div class="search-container">
            <input type="text" name="searchQuery" id="searchInput" placeholder="Search...">
            <button type="submit" name="searchButton">
                <i class='bx bx-search-alt'></i>
            </button>
        </div>
        <h2>Expired Products List</h2>
        <div class="container">
            <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products != null) {
                for (Product product : products) {
            %>
            <div class="product-card">
                <h3><%=product.getProductName()%></h3>
                <%
                Blob blob = product.getProductImage();
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    try {
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                        }
                        byte[] imageBytes = outputStream.toByteArray();
                        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                %>
                <img src="data:image/jpeg;base64,<%=base64Image%>"
                    alt="<%=product.getProductName()%>">
                <%
                } catch (IOException e) {
                e.printStackTrace();
                } finally {
                inputStream.close();
                outputStream.close();
                }
                }
                %>
                <div class="product-info">
                    <p>
                        <strong>Price:</strong> Rs.<%=product.getProductPrice()%></p>
                    <p>
                        <strong>Description:</strong>
                        <%=product.getDescription()%></p>
                    <p>
                        <strong>ProductCategory:</strong>
                        <%=product.getCategory()%></p>
                        
                         <p>
                        <strong>ProductQuantity:</strong>
                        <%=product.getProductQuantity()%></p>
                        
                        
                    <table>
                        <tr>
                            <th>Uses</th>
                            <td><%=product.getUses()%></td>
                        </tr>
                        <tr>
                            <th>Contains</th>
                            <td><%=product.getContains()%></td>
                        </tr>
                        <tr>
                            <th>Mfd Date</th>
                            <td><%=product.getMfdDate()%></td>
                        </tr>
                        <tr>
                            <th>Exp Date</th>
                            <td><%=product.getExpDate()%></td>
                        </tr>
                    </table>
                </div>
            </div>
            <%
            }
            } else {
            %>
            <p>No expired products available.</p>
            <%
            }
            %>
        </div>
    </form>
</body>
</html>
