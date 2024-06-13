<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Success</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            color: #333;
        }

        form {
            max-width: 500px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        form div {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input[type="text"][readonly] {
            border: none;
            background-color: transparent;
            padding: 0;
            font-size: 16px;
            margin-top: 5px;
        }

        span {
            display: block;
            padding: 10px 0;
            font-size: 16px;
        }

        .payment-method {
            display: flex;
            align-items: center;
            padding: 10px;
            border: 1px solid #ddd;
            margin: 10px 0;
            border-radius: 5px;
            cursor: pointer;
        }

        .payment-method label {
            flex-grow: 1;
            cursor: pointer;
        }

        button {
            width: 100%;
            background-color: #3d7676;
            color: white;
            border: none;
            cursor: pointer;
            padding: 10px;
            font-size: 16px;
            border-radius: 4px;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: black;
        }

        .sidebar {
            height: 100%;
            width: 0;
            position: fixed;
            z-index: 1;
            top: 0;
            right: 0;
            background-color: #f8f9fa;
            overflow-x: hidden;
            transition: 0.5s;
            padding-top: 60px;
            box-shadow: -2px 0 5px rgba(0, 0, 0, 0.5);
        }

        .sidebar div {
            padding: 8px 32px;
            text-decoration: none;
            font-size: 18px;
            color: #333;
            display: block;
            transition: 0.3s;
        }

        .sidebar input {
            width: calc(100% - 64px);
            padding: 10px;
            margin: 8px 0;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .sidebar button {
            background-color: #3d7676;
            color: white;
            border: none;
            cursor: pointer;
            padding: 10px 20px;
            font-size: 16px;
            margin: 8px 0;
            border-radius: 4px;
        }

        .sidebar button:hover {
            background-color: black;
        }

        .closebtn {
            position: absolute;
            top: 0;
            right: 25px;
            font-size: 25px;
            margin-left: 50px;
            text-decoration: none;
            color: #333;
        }

        .sidebar .input-group {
            display: flex;
            justify-content: space-between;
        }

        .sidebar .input-group input {
            width: 100%;
        }

        .hidden {
            display: none;
        }
    </style>
</head>
<body>
    <form action="PaymentServlet" method="post">
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
            <label for="paymentMethod">Select Payment Method:</label>
            <div class="payment-method" onclick="selectPaymentMethod('cod')">
                <label for="cod"><input type="radio" id="cod" name="paymentMethod" value="Cash on Delivery" required> Cash on Delivery</label>
            </div>
            <div class="payment-method" onclick="selectPaymentMethod('upi')">
                <label for="upi"><input type="radio" id="upi" name="paymentMethod" value="UPI Method"> UPI Method</label>
            </div>
            <div class="payment-method" onclick="selectPaymentMethod('card')">
                <label for="card"><input type="radio" id="card" name="paymentMethod" value="Credit/Debit Card"> Credit/Debit Card</label>
            </div>
        </div>
        <div>
            <button type="submit" name="action" value="placeOrder">Continue</button>
        </div>
    </form>

    <div id="cardFormSidebar" class="sidebar">
        <a href="javascript:void(0)" class="closebtn" onclick="closeCardForm()">&times;</a>
        <div>
            <h2>Enter Card</h2>
            <div>
                <label for="cardNumber">Card Number</label>
                <input type="text" id="cardNumber" name="cardNumber" placeholder="1234-5678-9876-54321">
            </div>
            <div class="input-group">
                <div >
                    <label for="validThrough">Valid Through</label>
                    <input type="text" id="validThrough" name="validThrough" placeholder="MM/YY">
                </div>
                <div style=" line-height: 42px">
                    <label for="cvv">CVV</label>
                    <input type="text" id="cvv" name="cvv" placeholder="123">
                </div>
            </div>
            <div>
                <label for="nameOnCard">Name on Card</label>
                <input type="text" id="nameOnCard" name="nameOnCard" placeholder="e.g.Manoj Kumar">
            </div>
            <div>
                <button type="submit" class="submitBtn">Pay ${sessionScope.total}</button>
            </div>
        </div>
    </div>

    <div id="upiFormSidebar" class="sidebar">
        <a href="javascript:void(0)" class="closebtn" onclick="closeUPIForm()">&times;</a>
        <div>
            <h2>UPI Payment</h2>
            <div>
                <label for="upiId">Add UPI ID</label>
                <input type="text" id="upiId" name="upiId" placeholder="Enter your UPI ID">
            </div>
            <div>
                <button type="submit" class="submitBtn">Pay ${sessionScope.total}</button>
            </div>
        </div>
    </div>

    <script>
        function selectPaymentMethod(method) {
            closeSidebars();
            if (method === 'card') {
                toggleCardForm();
            } else if (method === 'upi') {
                toggleUPIForm();
            }
        }

        function toggleCardForm() {
            document.getElementById("cardFormSidebar").style.width = "400px";
        }

        function closeCardForm() {
            document.getElementById("cardFormSidebar").style.width = "0";
        }

        function toggleUPIForm() {
            document.getElementById("upiFormSidebar").style.width = "400px";
        }

        function closeUPIForm() {
            document.getElementById("upiFormSidebar").style.width = "0";
        }

        function closeSidebars() {
            closeCardForm();
            closeUPIForm();
        }

        document.addEventListener('DOMContentLoaded', function () {
            var amount = parseFloat(document.getElementById('amount').value);
            if (amount < 300) {
                document.getElementById('cod').disabled = true;
            }
        });
    </script>
</body>
</html>
