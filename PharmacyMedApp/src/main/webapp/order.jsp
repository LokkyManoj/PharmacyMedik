<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="pharmacy.model.Order"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Medik Pharmacy</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color:#b2f9ff;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .order-status {
                    background-color:#97a1a2;
        
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
        }
        .order-status h2 {
            margin-bottom: 20px;
            color: #333;
        }
        .order-details {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        .order-item {
            display: flex;
            justify-content: space-between;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 5px;
        }
        .order-item label {
            font-weight: bold;
            color: #555;
        }
        .order-item span {
            color: #777;
        }
        .form-group {
            margin-top: 20px;
            display: flex;
            flex-direction: column;
                       
            
            
        }
        .form-group label {
            font-weight: bold;
            margin-bottom: 5px;
        }
        .form-group input {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 100%;
        }
        .form-group button {
            margin-top: 20px;
            padding: 10px;
            background-color:#3d7676;
            border: none;
            border-radius: 5px;
            color: #fff;
            font-size: 16px;
            cursor: pointer;
        }
        .form-group button:hover {
            background-color:black;
        }
    </style>
</head>
<body>
<% Order order = new Order(); %>
    <div class="order-status">
        <h2>Order Status</h2>
        <div class="order-details">
            <div class="order-item">
                <label>Order Date:</label>
                <span><%= request.getAttribute("orderDate") %></span>
            </div>
            <div class="order-item">
                <label>Expected Delivery Date:</label>
                <span><%= request.getAttribute("expectedDeliveryDate") %></span>
            </div>
            <div class="order-item">
                <label>Order Status:</label>
                <span>Pending</span>
            </div>
            <div class="order-item">
                <label>Total Quantity:</label>
                <span><%= request.getAttribute("totalQuantity") %></span>
            </div>
            <div class="order-item">
                <label>Total Price:</label>
                <span><%= request.getAttribute("total") %></span>
            </div>
        </div>
        <form action="OrderProductServlet" method="post">
                    <input type="hidden" name="total" value="<%= request.getAttribute("total") %>" />
        
            <input type="hidden" name="productId" value="<%= request.getAttribute("productId") %>" />
            <input type="hidden" name="userId" value="<%= request.getAttribute("userId") %>" />
            <input type="hidden" name="quantity" value="<%= request.getAttribute("quantity") %>" />
            <input type="hidden" name="status" value="<%= request.getAttribute("status") %>" />
            <input type="hidden" name="orderDate" value="<%= request.getAttribute("orderDate") %>" />
            <input type="hidden" name="expectedDeliveryDate" value="<%= request.getAttribute("expectedDeliveryDate") %>" />

            <div class="form-group">
                <label>Add Delivery Address:</label>
                <input type="text" name="address" min=10  required/>
            </div>
            <div class="form-group">
                <button type="submit" name="action" value="placeOrder">Confrim</button>
            </div>
        </form>
       </div>
                       
</body>
</html>