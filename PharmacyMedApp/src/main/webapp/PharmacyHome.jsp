<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.http.*, javax.servlet.*"%>
<%-- <%
session=request.getSession(false);
if(session == null) {
	response.sendRedirect("PharmacyLogin.jsp");
	return;
}
%> --%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Medik Pharmacy</title>
<style>
body {
	font-family: 'Gill Sans', sans-serif;
	margin: 0;
	padding: 0;
}

header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 10px 20px;
	background-color: #e9ecef;
	border-bottom: 1px solid #e9ecef;
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

h1 {
	font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
		sans-serif;
	font-size: 24px;
	margin: 0;
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

.cart {
	position: relative;
}

.cart img {
	height: 25px;
}

main {
	padding: 20px;
}

.category-cards {
	display: flex;
	justify-content: center;
	gap: 20px;
	flex-wrap: wrap;
	margin-top: 20px;
}

.card {
	border: 1px solid #ddd;
	padding: 10px;
	border-radius: 5px;
	width: 200px;
	text-align: center;
	transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.card img {
	/* width: 100%; */
	/* height: auto; */
	border-radius: 5px;
	object-fit: cover;
	max-width: 100%;
	height: 150px;
	object-fit: cover;
}

.card h3 {
	font-size: 18px;
	margin: 10px 0;
}

.card:hover {
	transform: translateY(-10px);
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

footer {
	background-color: #e9ecef;
	padding: 20px 0;
	text-align: center;
	border-top: 1px solid #e9ecef;
	width: 100%;
}
</style>
</head>
<body>
	<form action="FirstServlet" method="get">
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
					<li><a href="LogoutServlet" class="logout-btn"><img
							src="images1/logouticon.png" alt="Logout Icon">Logout</a></li>
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

					<!--                 <li><a href="PharmacyReg.jsp"><img src="images1/Registericon.png" alt="SignIn Icon">SignIn</a></li>
 -->
					<li><a href="#" class="cart"><img
							src="images1/icons8-cart-32.png" alt="Cart Icon">Cart</a></li>
				</ul>
			</nav>
		</header>
		<main>
			<section class="category-cards">
				<div class="card">
					<a href="PharmacyMainServlet"><img src="images1/tablets.jpg"
						alt="Medicines"></a>
					<h3>Medicines</h3>
				</div>
				<div class="card">
					<img src="images1/skincare.jpg" alt="HealthCare">
					<h3>SkinCare Products</h3>
				</div>
				<div class="card">
					<img src=images1/hea.jpg alt="HealthCare Devices">
					<h3>HealthCare Devices</h3>
				</div>
				<div class="card">
					<img src=images1/healthfood.jpg alt="Health Food and Drinks">
					<h3>Healthy Food and Drinks</h3>
				</div>
			</section>
		</main>
		<footer>
			<p>&copy; 2024 Medik Pharmacy. All rights reserved.</p>
		</footer>
	</form>
</body>
</html>
