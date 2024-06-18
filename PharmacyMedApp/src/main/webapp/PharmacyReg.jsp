<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Medik Pharmacy</title>
<link
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap">

<style>
body {
	font-family: 'Roboto', sans-serif;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	background-color: #f0f2f5;
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

input[type="text"], input[type="tel"], input[type="email"], input[type="password"]
	{
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
	background-color: #0056b3;
}

input:focus, button:focus {
	outline: none;
	border-color: #007bff;
}

input::placeholder {
	font-style: italic;
	color: #aaa;
}
</style>
</head>
<body>
	<div class="container">

		<h2>
			<img src="images1/pharmlogo2.png" alt="Logo"> SignIn
		</h2>
		<form action="FirstServlet" method="post">
			<div class="form-group">
				<label for="name">Name</label> <input type="text" id="name"
					name="name" placeholder="Name" required>
			</div>
			<div class="form-group">
				<label for="mobileNumber">Mobile Number</label> <input type="tel"
					id="mobileNumber" name="mobileNumber" placeholder="MobileNumber" required pattern="[0-9]{10}">
			</div>
			<div class="form-group">
				<label for="email">Email</label> <input type="email" id="email"
					name="email" placeholder="Email" required>
			</div>
			<div class="form-group">
				<label for="password">Password</label> <input type="password"
					id="password" name="password" placeholder="Password" required>
			</div>
			<br>
			<button type="submit">Sign In</button>
		</form>
	</div>
</body>
</html>