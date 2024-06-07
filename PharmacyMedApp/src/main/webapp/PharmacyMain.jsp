<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.Blob"%>
<%@ page import="java.util.Base64"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="pharmacy.servlet.PharmacyMainServlet.Product"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.IOException"%>
<%@ page import="pharmacy.model.CartItem"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Medicines</title>
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css'
	rel='stylesheet'>
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
	position: relative; /* Added */
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

.search-icon, .cart-icon {
	font-size: 24px;
	cursor: pointer;
}

.cart-icon {
	position: relative;
}

.container {
	display: none;
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
}

.product-card img {
	max-width: 50%;
	height: 30%;
}

.product-info {
	margin-top: 12px;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 12px;
}

table, th, td {
	border: 1px solid #ddd;
}

th, td {
	padding: 8px;
	text-align: left;
}

.cart-icon .red-dot {
	position: absolute;
	top: 0;
	right: 0;
	height: 10px;
	width: 10px;
	background-color: red;
	border-radius: 50%;
	display:none;
	
</style>
</head>
<body>
	<header>
		<div class="logo-container">
			<img src="images1/pharmlogo2.png" alt="Pharmacy Logo" class="logo">
			<img src="images1/MedikLogo.png" alt="Medik Logo" class="logo1">
		</div>
		<div class="search-container">
			<input type="text" id="search-bar" class="search-bar"
				placeholder="Search for medicines..." oninput="searchProducts()">
			<i class='bx bx-search search-icon' onclick="searchProducts()"></i>
		</div>
		
		<div class="cart-icon" id="cart-icon">
			<a href="ViewCartServlet"
				style="text-decoration: none; color: inherit;"> <i
				class='bx bx-cart'></i>
				<span class="red-dot" style="<% 
            Integer size = (Integer) session.getAttribute("cartItems");
            if(size == null || size == 0){
                out.print("display:block;");
            }
        %>"></span>
			</a>
		</div>
	</header>

	<div class="container" id="product-container">
		<%
		List<Product> products = (List<Product>) request.getAttribute("products");
		if (products != null) {
			for (Product product : products) {
		%>
		<div class="product-card"
			data-name="<%=product.getProductName().toLowerCase()%>">
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
			<img src="data:image/jpeg;base64,<%=base64Image%>"
				alt="<%=product.getProductName()%>">
			<%
			} catch (IOException e) {
			e.printStackTrace();
			} finally {
			inputStream.close();
			outputStream.close();
			}
			}
			%>
			<div class="product-info">
				<p>
					<strong>Price:</strong> Rs.<%=product.getProductPrice()%></p>
				<p>
					<strong>Description:</strong>
					<%=product.getDescription()%></p>
				<p>
					<strong>Category:</strong>
					<%=product.getCategory()%></p>
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
					<form action="AddToCartServlet" class="add-to-cart-form"
						method="post" style="display: inline;">
						<input type="hidden" name="product_id"
							value="<%=product.getProductId()%>"> <input
							type="hidden" name="user_id" value="<%=id%>"> <input
							type="number" name="quantity" min="1"
							max="<%=product.getProductQuantity()%>" value="1">
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
    const filter = searchBar.value.toLowerCase();
    const productContainer = document.getElementById('product-container');
    const productCards = productContainer.getElementsByClassName('product-card');
   
    if (filter === "") {
        productContainer.style.display = 'none';
        return;
    }

    let hasResults = false;

    for (let i = 0; i < productCards.length; i++) {
        const productName = productCards[i].getAttribute('data-name');
        if (productName.includes(filter)) {
            productCards[i].style.display = '';
            hasResults = true;
        } else {
            productCards[i].style.display = 'none';
        }
    }

    productContainer.style.display = hasResults ? 'flex' : 'none';
}


</script>
</body>
</html>
