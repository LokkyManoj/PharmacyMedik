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
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background:url('images1/medbg1.jpg');
	 background-repeat: no-repeat;
  background-size: cover;
   background-position: center;
  min-height: 100vh;
}

header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
   background-color:#83d4eb;
	border-bottom: 1px solid black;
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
    margin-left: -30px;
    margin-bottom: 20px;
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
    border: 1px solid black;
    padding: 10px;
    border-radius: 5px;
    width: 200px;
    text-align: center;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    justify-content: center;
	gap: 50px;
	flex-wrap: wrap;
	margin-top: 20px;
}

.card img {
    border-radius: 5px;
    object-fit: cover;
    max-width: 100%;
    height: 150px;
}

.card h3 {
    font-size: 18px;
    margin: 10px 0;
    cursor:pointer;
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
                            src="images1/Registericon2.png" alt="SignIn Icon">SignUp</a></li>
                    <li><a href="PharmacyLogin.jsp"><img
                            src="images1/Loginicon2.png" alt="LogIn Icon">LogIn</a></li>
                    <%
                    }
                    %>
                </ul>
            </nav>
        </header>

        <main>
            <div class="category-cards">
                <div class="card">
                    <a href="addProducts.jsp"><img src="images1/pharmlogo2.png" alt="Add Product"></a>
                    <h3>Add Products</h3>
                </div>
                
                <div class="card">
                    <a href="ViewProductServlet"><img src="images1/pharmlogo2.png" alt="Add Product"></a>
                    <h3>View Products</h3>
                </div>
                
              
            </div>
        </main>
    </form>
</body>
</html>
