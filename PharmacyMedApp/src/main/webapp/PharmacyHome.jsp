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
    background: linear-gradient(to bottom,#ebf6f7,#cfeefd);
    height:600px;
}

header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    box-shadow: 0 5px 3px -2px rgba(0, 0, 0, 0.3);
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

.order-medicines {
    background-color: #008080;
    color: white;
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    margin: 20px 0;
}

.order-medicines input[type="text"] {
    padding: 10px;
    border-radius: 5px;
    border: none;
    width: 250px;
}

.order-medicines button {
    padding: 10px 20px;
    border-radius: 5px;
    border: none;
    background-color: #006666;
    color: white;
    cursor: pointer;
}

.order-medicines button:hover {
    background-color: #004c4c;
}

.category-cards {
    display: flex;
    justify-content: center;
    gap: 60px;
    flex-wrap: wrap;
    margin-top: 20px;
}

.card {
    box-shadow: 0 5px 3px -2px rgba(0, 0, 0, 0.7);
    padding: 10px;
    border-radius: 5px;
    width: 150px;
    text-align: center;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    background-color:white;
}

.card img {
    /* width: 100%; */
    /* height: auto; */
    border-radius: 5px;
    object-fit: cover;
    max-width: 100%;
    height: 100px;
    object-fit: cover;
}

.card h3 {
    font-size: 11px;
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


.cart-count {
    position: absolute;
    top: -4px;
    right: 29px;
    background-color: red;
    color: white;
    border-radius: 50%;
    padding: 2px 6px;
    font-size: 12px;
    font-weight: bold;
    line-height: 1;
    text-align: center;
}
a{
text-decoration:none;
color:white;
}
</style>
</head>
<body>
<%HttpSession session1= request.getSession(); %>
    <form action="FirstServlet" method="get">
        <header ">
            <div class="logo-container">
                <img src="images1/pharmlogo2.png" alt="Pharmacy Logo" class="logo">
                <img src="images1/MedikLogo.png" alt="Medik Logo" class="logo1">
            </div>
            <nav class="top-nav" >
                <ul>
                    <% if (session.getAttribute("email") != null) { %>
                    <li><a href="#"><img src="images1/hii2.png"
                            alt="HiIn Icon">Hello <%=session.getAttribute("name")%></a></li>
                    <li><a href="LogoutServlet" class="logout-btn"><img
                            src="images1/logouticon.png" alt="Logout Icon">Logout</a></li>
                    <% } else { %>
                    <li><a href="PharmacyReg.jsp"><img
                            src="images1/Registericon2.png" alt="SignIn Icon">SignUp</a></li>
                    <li><a href="PharmacyLogin.jsp"><img
                            src="images1/Loginicon2.png" alt="LogIn Icon">LogIn</a></li>
                    <% } %>
                    <li><a href="ViewCartServlet" style="text-decoration: none;" class="cart"><img
                            src="images1/icons8-cart-32.png" alt="Cart Icon">Cart
                            <span class="cart-count"><%= session1.getAttribute("cartItemCount") != null ? session1.getAttribute("cartItemCount") : 0 %></span>
                             </a></li>
                </ul>
            </nav>
        </header>
        <main>
            <div class="order-medicines">
                <h2>Order Medicines Online</h2>
                <div>
                    <span>1 Lakh+ Products</span> | 
                    <span>Easy Returns</span>
                </div>
 <form>
                    <input type="text"  id="searchInput" class="searchy" placeholder="Search Medicines..............">
                    <button type="submit"><a href="PharmacyMainServlet">Search</a></button>
                </form>
            </div>
            <section class="category-cards">
                <div class="card">
                    <a href="PharmacyMainServlet"><img src="images1/tablets.jpg"
                        alt="Medicines"></a>
                    <h3>Medicines</h3>
                </div>
                <div class="card">
                    <a href="PharmacyMainServlet1"><img src="images1/skincare.jpg" alt="HealthCare"></a>
                    <h3>SkinCare Products</h3>
                </div>
                <div class="card">
                    <a href="PharmacyMainServlet2"><img src=images1/hea.jpg alt="HealthCare Devices"></a>
                    <h3>Fitness Supplements</h3>
                </div>
                <div class="card">
                    <img src=images1/healthfood.jpg alt="Health Food and Drinks">
                    <h3>Healthy Food and Drinks</h3>
                </div>
            </section>
        </main>
    </form>

</body>
</html>
