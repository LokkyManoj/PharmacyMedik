<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Success</title>
</head>
<body>
    <h2>Payment Successful</h2>
    <div>
        <label for="productName">Product Name:</label>
        <span>${sessionScope.productName}</span>
    </div>
    <div>
        <label for="amount">Amount:</label>
        <span>${sessionScope.amount}</span>
    </div>
    <div>
        <label for="expectedDeliveryDate">Expected Delivery Date:</label>
        <span>${sessionScope.expectedDeliveryDate}</span>
    </div>
    <div>
        <label for="address">Delivery Address:</label>
        <span>${sessionScope.address}</span>
    </div>
    <form action="OrderProductServlet" method="post">
        <button type="submit" name="action" value="buy">Order Now</button>
    </form>
</body>
</html>
