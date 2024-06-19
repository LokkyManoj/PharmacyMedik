<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Blob"%>
<%@ page import="java.util.Base64"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="pharmacy.model.Product"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.IOException"%>
<%@ page import="pharmacy.model.CartItem"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Medik Pharmacy</title>
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
<style>
/* Your CSS styles */
body {
    font-family: 'Gill Sans', sans-serif;
    margin: 0;
    padding: 0;
    background-color: #b2f9ff;
}

header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    background-color: #83d4eb;
    border-bottom: 1px solid black;
    position: relative;
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

.search-container {
    display: flex;
    align-items: center;
    margin-right: 20px;
    width: 300%;
}

.search-bar {
    padding: 10px;
    border-radius: 4px;
    border: 1px solid #ccc;
    margin-right: 10px;
}

.search-icon,
.cart-icon {
    font-size: 24px;
    cursor: pointer;
}

.home-icon {
    font-size: 23px;
    cursor: pointer;
    padding-left: 100px;
}

.container {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    margin-top: 20px;
}

.product-card {
    border: 1px solid #ccc;
    padding: 16px;
    margin: 16px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    width: 45%;
    box-sizing: border-box;
    background-color: #ffffff;
}

.product-card img {
    max-width: 100%;
    height: auto;
}

.product-info {
    margin-top: 12px;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 12px;
}

table,
th,
td {
    border: 1px solid #ddd;
}

th,
td {
    padding: 8px;
    text-align: left;
}

button {
    background-color: #3d7676;
    color: white;
    border: none;
    cursor: pointer;
    padding: 10px 20px;
    font-size: 16px;
    margin: 8px 0;
    border-radius: 4px;
}

.top-nav img {
    margin-right: 5px;
    height: 25px;
}

.top-nav a:hover {
    text-decoration: underline;
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

.cart-count {
    position: absolute;
    top: 25px;
    right: 13px;
    background-color: red;
    color: white;
    border-radius: 50%;
    padding: 2px 6px;
    font-size: 12px;
    font-weight: bold;
    line-height: 1;
    text-align: center;
}
</style>
</head>
<body>
<header>
<%HttpSession session1= request.getSession(); %>
    <div class="logo-container">
        <img src="images1/pharmlogo2.png" alt="Pharmacy Logo" class="logo">
        <img src="images1/MedikLogo.png" alt="Medik Logo" class="logo1">
    </div>
    <div class="search-container">
        <input type="text" id="search-bar" class="search-bar" placeholder="Search for medicines..." oninput="searchProducts()">
        <i class='bx bx-search search-icon' onclick="searchProducts()"></i>
    </div>
    <nav class="top-nav">
        <ul>
            <li>
                <div class="home-icon" id="home-icon">
                    <a href="PharmacyHome.jsp" style="text-decoration: none;">
                        <img src="images1/homeicon.png" alt="Home Icon">
                    </a>
                </div>
            </li>
            <li>
                <div class="cart-icon" id="cart-icon">
                    <a href="ViewCartServlet" style="text-decoration: none;">
                        <img src="images1/icons8-cart-32.png" alt="Cart Icon">
                         <span class="cart-count"><%= session1.getAttribute("cartItemCount") != null ? session1.getAttribute("cartItemCount") : 0 %></span>
                    </a>
                </div>
            </li>
        </ul>
    </nav>
</header>

<div class="container" id="product-container">
    <%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if (products != null) {
        for (Product product : products) {
            String category = product.getCategory().toLowerCase();
    %>
    <div class="product-card" data-name="<%=product.getProductName().toLowerCase()%>" data-category="<%=category%>">
        <h3><%=product.getProductName()%></h3>
        <%
        Blob blob = product.getProductImage();
        if (blob != null) {
            InputStream inputStream = blob.getBinaryStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                byte[] imageBytes = outputStream.toByteArray();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        %>
        <img src="data:image/jpeg;base64,<%=base64Image%>" alt="<%=product.getProductName()%>">
        <%
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputStream.close();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        %>
        <div class="product-info">
            <p><strong>Price:</strong> Rs.<%=product.getProductPrice()%></p>
            <p><strong>Description:</strong> <%=product.getDescription()%></p>
            <p><strong>Category:</strong> <%=product.getCategory()%></p>
            <table>
                <tr>
                    <th>Uses</th>
                    <td><%=product.getUses()%></td>
                </tr>
                <tr>
                    <th>Contains</th>
                    <td><%=product.getContains()%></td>
                </tr>
            </table>
            <br>
            <%
            int id = (int) session.getAttribute("id");
            %>
            <div class="product-actions">
                <form action="AddToCartServlet1" class="add-to-cart-form" method="post" style="display: inline;">
                    <input type="hidden" name="product_id" value="<%=product.getProductId()%>">
                    <input type="hidden" name="user_id" value="<%=id%>">
                    <input type="number" name="quantity" min="1" max="<%=product.getProductQuantity()%>" value="1">
                    <button type="submit">Add to Cart</button>
                </form>
            </div>
        </div>
    </div>
    <%
        }
    }
    %>
</div>

<script>
function searchProducts() {
    const searchBar = document.getElementById('search-bar');
    const filter = searchBar.value.trim().toLowerCase();
    const productContainer = document.getElementById('product-container');
    const productCards = productContainer.getElementsByClassName('product-card');
    
    let hasResults = false;

    for (let i = 0; i < productCards.length; i++) {
        const productName = productCards[i].getAttribute('data-name');
        
        if (filter === "") {
            productCards[i].style.display = '';
            hasResults = true;
        } else {
            if (productName.includes(filter)) {
                productCards[i].style.display = '';
                hasResults = true;
            } else {
                productCards[i].style.display = 'none';
            }
        }
    }

    productContainer.style.display = hasResults ? 'flex' : 'none';
}

</script>

</body>
</html>
