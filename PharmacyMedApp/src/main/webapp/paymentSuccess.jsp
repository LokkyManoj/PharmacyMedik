<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Medik Pharmacy</title>
</head>
<body>
    <h2>Payment Successful</h2>
    
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
    <form action="PlaceOrderServlet" method="get">
        <button type="submit">Order Now</button>
    </form>
</body>
</html>
