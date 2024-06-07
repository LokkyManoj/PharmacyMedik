<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Blob"%>
<%@ page import="java.util.Base64"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.IOException"%>
<%@ page import ="pharmacy.model.CartItem"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Cart</title>
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f8f8f8;
        margin: 0;
        padding: 20px;
    }

    h2 {
        color: #333;
        text-align: center;
        margin-bottom: 20px;
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
        width: 25%;
        box-sizing: border-box;
        background-color: #fff;
        text-align: center;
    }

    .product-card img {
        max-width: 100%;
        height: auto;
        border-radius: 8px;
    }

    .product-info {
        margin-top: 12px;
    }

    .product-info p {
        margin: 8px 0;
    }

    .product-info strong {
        display: block;
        margin-bottom: 4px;
        color: #555;
    }

    .product-actions {
        margin-top: 12px;
        display: flex;
        justify-content: center;
        gap: 10px;
    }

    .product-actions button {
        background-color: #3d7676;
        color: #fff;
        border: none;
        padding: 8px 15px;
        cursor: pointer;
        border-radius: 15px;
        font-size: 14px;
        transition: background-color 0.3s ease;
    }

    .product-actions button.delete {
        background-color: #dc3545;
    }

    .product-actions button:hover {
        background-color: #99bbd3;
    }

    .product-actions button.delete:hover {
        background-color: #c82333;
    }
    
    .no-items {
        text-align: center;
        font-size: 18px;
        color: #777;
        margin-top: 20px;
    }

</style>
</head>
<body>
    <h2>Your Cart</h2>
    <div class="container">
        <%
        List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
        if (cartItems != null && !cartItems.isEmpty()) {
            for (CartItem item : cartItems) {
        %>
        <div class="product-card">
            <h3><%= item.getProductName() %></h3>
            <%
            Blob blob = item.getProductImage();
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
            <img src="data:image/jpeg;base64,<%= base64Image %>" alt="<%= item.getProductName() %>">
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
                <p><strong>Price:</strong> Rs.<%= item.getProductPrice() %></p>
                <p><strong>Quantity:</strong> <%= item.getQuantity() %></p>
                <p><strong>Total Price:</strong> Rs.<%= item.getProductPrice() * item.getQuantity() %></p>
            </div>
            <div class="product-actions">
                <form action="UpdateProductServlet" method="get">
                    <button type="submit">Buy</button>
                </form>
                <form action="RemoveFromCartServlet" method="post">
                    <button type="submit" class="delete">Remove</button>
                </form>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <p class="no-items">No items added to the cart.</p>
        <%
        }
        %>
    </div>
</body>
</html>
