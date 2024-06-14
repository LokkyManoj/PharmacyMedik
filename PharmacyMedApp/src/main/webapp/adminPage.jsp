<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Medik Pharmacy</title>
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

form {
    background-color: #fff;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    width: 400px;
    margin: 0 auto;
}

label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}

input[type="text"],
input[type="number"],
textarea {
    width: calc(100% - 20px);
    padding: 8px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

input[type="file"] {
    width: calc(100% - 20px);
    padding: 8px;
    margin-bottom: 10px;
    box-sizing: border-box;
}

input[type="submit"] {
    background-color:#3d7676;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

input[type="submit"]:hover {
    background-color:black;
}

a {
    display: block;
    text-align: center;
    margin-top: 10px;
    text-decoration: none;
    color: #333;
}

a:hover {
    text-decoration: underline;
}

.logo-container {
	display: flex;
	align-items: center;
}

.logo {
	height: 50px;
	margin-right: 10px;
}

.logo1 {
	height: 50px;
	margin-left:-30px;
	margin-bottom:20px
}
.top-nav ul {
	list-style: none;
	margin: 0;
	padding: 0;
	display: flex;
	gap: 20px;
}

.top-nav a {
	text-decoration: none;
	color: black;
	font-size: 16px;
	display: flex;
	align-items: center;
}

.top-nav img {
	margin-right: 5px;
	height: 20px;
}

.top-nav a:hover {
	text-decoration: underline;
}

    body {
	font-family: 'Gill Sans', sans-serif;
	margin: 0;
	padding: 0;
}
    
    </style>
</head>

<body>
  
    <header>
    			<div class="logo-container">
				<img src="images1/pharmlogo2.png" alt="Pharmacy Logo" class="logo">
				<img src="images1/MedikLogo.png" alt="Medik Logo" class="logo1">
			</div>
			<nav class="top-nav">
				<ul>
					<%
					if (session.getAttribute("email") != null) {
					%>
					<li><a href="#"><img src="images1/hii2.png"
							alt="HiIn Icon">Hello <%=session.getAttribute("name")%></a></li>
					
					<%
					} else {
					%>
					<li><a href="PharmacyReg.jsp"><img
							src="images1/Registericon2.png" alt="SignIn Icon">SignIn</a></li>
					<li><a href="PharmacyLogin.jsp"><img
							src="images1/Loginicon2.png" alt="LogIn Icon">LogIn</a></li>
					<%
					}
					%>
	
				</ul>
			</nav>
    
    </header>
        <form action="AddImageServlet" method="post" enctype="multipart/form-data">
    
        <label for="product_id">Product ID:</label>
        <input type="number" id="product_id" name="product_id" required min=1><br><br>

        <label for="product_name">Product Name:</label>
        <input type="text" id="product_name" name="product_name" pattern="[A-Za-z\s]+" title="Please enter only letters (A-Z, a-z) and spaces" required><br><br>

        <label for="product_image">Product Image:</label>
        <input type="file" id="product_image" name="product_image" accept="image/*" required><br><br>

        <label for="product_quantity">Product Quantity:</label>
        <input type="number" id="product_quantity" name="product_quantity" required min=1><br><br>

        <label for="product_price">Product Price:</label>
        <input type="number" id="product_price" name="product_price" required min=1><br><br>
        
         <label for="product_category">Product Category:</label>
        <input type="text" id="product_category" name="product_category" pattern="[A-Za-z\s]+" title="Please enter only letters (A-Z, a-z) and spaces" required><br><br>
        
         <label for="mfd_date">Mfd Date:</label>
        <input type="date" id="mfd_date" name="mfd_date" required><br><br>
        
        <label for="exp_date">Exp Date:</label>
        <input type="date" id="exp_date" name="exp_date" required><br><br>
        

        <label for="description">Description:</label>
        <textarea id="description" name="description" maxlength="900"  required></textarea><br><br>

        <label for="uses">Uses:</label>
        <textarea id="uses" name="uses" maxlength="900" pattern="[A-Za-z\s]+" title="Please enter only letters (A-Z, a-z) and spaces" required></textarea><br><br>

        <label for="contains">Contains:</label>
        <textarea id="contains" name="contains" maxlength="900"  required></textarea><br><br>

       <center> <input type="submit" value="Add Product"></center>
        <a href="ViewProductServlet">View the Products</a>
    </form>
    
    
</body>
</html>