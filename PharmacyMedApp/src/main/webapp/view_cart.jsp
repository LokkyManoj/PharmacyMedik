<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.Blob"%>
<%@ page import="java.util.Base64"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.InputStream"%>
<%-- <%@ page import="pharmacy.servlet.ViewProductServlet.Product"%>
 --%><%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.IOException"%>
<%@ page import="pharmacy.servlet.ViewCartServlet.CartItem"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Products</title>
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css'
	rel='stylesheet'>
<style>
.container {
	display: flex;
	flex-wrap: wrap;
	justify-content: space-around;
}

.product-card {
	border: 1px solid #ccc;
	padding: 16px;
	margin: 16px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	width: 20%;
	box-sizing: border-box;
}

.product-card img {
	max-width: 20%;
	height: 15%;
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


.product-actions {
	margin-top: 12px;
	display: flex;
	justify-content: space-between;
}

.product-actions button {
	background-color: #4CAF50;
	color: #fff;
	border: none;
	padding: 8px 12px;
	cursor: pointer;
	border-radius: 4px;
}

.product-actions button.delete {
	background-color: #dc3545;
}
</style>
</head>
<body>
	<form action="ViewProductServlet" method="get">
		
		</div>
		<h2>Cart</h2>
		<div class="container">
			<%
			List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
			if (cartItems != null && !cartItems.isEmpty()) {
				for (CartItem item : cartItems) {
			%>
			<div class="product-card">
				<h3><%=item.getProductName()%></h3>
				<%
				Blob blob = item.getProductImage();
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
					alt="<%=item.getProductName()%>">
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
						<strong>Price:</strong> Rs.<%=item.getProductPrice()%></p>
					
					<p>
						<strong>Quantity:</strong>
						<%=item.getQuantity()%></p>

					<p>
						<strong>TotalPrice:</strong> Rs.
						<%=item.getProductPrice() * item.getQuantity()%></p>

					<div class="product-actions">
						<form action="UpdateProductServlet" method="get"
							style="display: inline;">
							
							<button type="submit">Buy</button>
						</form>
					</div>
				</div>
			</div>
			<%
			}
			} else {
			%>
			<p>No item added to cart.</p>
			<%
			}
			%>
		</div>
	</form>
</body>
</html>
