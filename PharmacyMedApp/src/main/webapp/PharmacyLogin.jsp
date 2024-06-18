<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Medik Pharmacy</title>
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
<style>
    body {
        font-family: 'Roboto', sans-serif;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
         background-color:#b2f9ff; 
        margin: 0;
    }

    .container {
        background-color: #fff;
        padding: 20px 40px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        width: 300px;
    }

    h2 {
        text-align: center;
        margin-bottom: 20px;
        font-weight: 500;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    h2 img {
        margin-right: 10px;
        height: 30px; 
    }

    .form-group {
        margin-bottom: 15px;
    }

    label {
        display: block;
        margin-bottom: 8px;
        font-weight: 400;
    }

    input[type="email"], input[type="password"] {
        width: 100%;
        padding: 7px;
        border: 1px solid black;
        border-radius: 10px;
        font-size: 16px;
        font-family: 'Roboto', sans-serif;
    }

    button {
        width: 100%;
        padding: 10px;
        background-color: #475461;
        border: none;
        border-radius: 20px;
        color: #fff;
        font-size: 16px;
        font-family: 'Roboto', sans-serif;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    button:hover {
        background-color:black;
    }

    input:focus, button:focus {
        outline: none;
        border-color: #007bff;
    }

    input::placeholder {
        font-style: italic;
        color: #aaa;
    }

    .logout-btn {
        background-color: #dc3545;
        margin-top: 10px;
    }

    .logout-btn:hover {
        background-color: #c82333;
    }
    .error-box {
        background-color: #ffe6e6; 
        color: #ff0000;
        padding: 5px 12px; 
        border-radius: 5px;
        margin-top: 5px;
         width: 95%;
    }
    
</style>
</head>
<body>
    <div class="container">
        <h2><img src="images1/pharmlogo2.png" alt="Logo"> Login</h2>
        <form id="login" action="LoginServlet" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Email" required>
                <% String emailError = (String)request.getAttribute("emailError");
                if (emailError != null) { %>
                    <div class="error-box"><%= emailError %></div> <!-- Display email error message -->
                <% } %>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Password" required>
                <% String passwordError = (String)request.getAttribute("passwordError");
                if (passwordError != null) { %>
                    <div class="error-box"><%= passwordError %></div> <!-- Display password error message -->
                <% } %>
            </div>
            <button type="submit">Login</button>
        </form>
    </div>
</body>

</html>
