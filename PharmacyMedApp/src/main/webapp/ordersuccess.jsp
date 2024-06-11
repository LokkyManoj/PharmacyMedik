<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Success</title>
</head>
<body>
    <form action="PaymentServlet" method="post">
        <div>
            <label for="productName">Product Name:</label>
            <span>${sessionScope.productName}</span>
        </div>
        <div>
            <label for="amount">Amount:</label>
            <input type="text" id="amount" name="amountDisplay" value="${sessionScope.total}" readonly>
            <input type="hidden" id="amountHidden" name="amount" value="${sessionScope.total}">
        </div>
        <div>
            <label for="expectedDeliveryDate">Expected Delivery Date:</label>
            <span>${sessionScope.expectedDeliveryDate}</span>
        </div>
        <div>
            <label for="address">Delivery Address:</label>
            <span>${sessionScope.address}</span>
        </div>
        <div>
            <label for="paymentMethod">Select Payment Method:</label><br>
            <input type="radio" id="cod" name="paymentMethod" value="Cash on Delivery" required>
            <label for="cod">Cash on Delivery</label><br>
            <input type="radio" id="upi" name="paymentMethod" value="UPI Method">
            <label for="upi">UPI Method</label>
        </div>
        <div>
            <button type="submit" name="action" value="placeOrder">Continue</button>
        </div>
    </form>
</body>
</html>
