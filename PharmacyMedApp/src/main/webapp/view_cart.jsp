<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="pharmacy.servlet.ViewCartServlet.CartItem"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Cart</title>
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
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }

    table, th, td {
        border: 1px solid #ddd;
    }

    th, td {
        padding: 8px;
        text-align: left;
    }

    th {
        background-color: #f2f2f2;
    }

    .actions {
        text-align: center;
    }

    .actions form {
        display: inline;
    }

    .actions button {
        background-color: #4CAF50;
        color: white;
        padding: 8px 16px;
        margin: 4px 2px;
        border: none;
        cursor: pointer;
        border-radius: 4px;
    }

    .actions button.delete {
        background-color: #f44336;
    }

    .actions button:hover {
        opacity: 0.8;
    }
</style>
</head>
<body>
    <h2>Your Cart</h2>
    <table>
        <tr>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Total</th>
        </tr>
        <%
            List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
            if (cartItems != null) {
                for (CartItem item : cartItems) {
        %>
        <tr>
            <td><%= item.getProductId() %></td>
            <td><%= item.getProductName() %></td>
            <td>Rs.<%= item.getProductPrice() %></td>
            <td><%= item.getQuantity() %></td>
            <td>Rs.<%= item.getProductPrice() * item.getQuantity() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="5">No items in the cart</td>
        </tr>
        <%
            }
        %>
    </table>
    <div class="actions">
        <form action="CheckoutServlet" method="post">
            <button type="submit">Proceed to Checkout</button>
        </form>
        <form action="ClearCartServlet" method="post">
            <button type="submit" class="delete">Clear Cart</button>
        </form>
    </div>
</body>
</html>
