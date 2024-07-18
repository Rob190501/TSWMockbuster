<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.*"%>

<%
	Cart cart = (Cart)request.getSession().getAttribute("cart");
%> 
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Carrello</title>
	
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/table.css">
</head>
<body>
	<jsp:include page = "/fragments/header.jsp" />
	
	<div class = "page">
		<% if(cart.isEmpty()) { %>
			<h1>Carrello vuoto</h1>
		<% } else { %>
			
			<div>
				<h3>Totale carrello: <%= cart.getTotal() %>€</h3>
				<a href = "">Ordina</a>
				<a href = "<%= request.getContextPath() %>/browse/UpdateCartServlet?action=empty">
					Svuota carrello
				</a>
			</div>
			
		<% } %>
		
		<% if(!cart.getPurchasedMovies().isEmpty()) { %>
			<div class = "tablecontainer">
				<table>
					<caption>Acquisti</caption>
					<tr>
						<th>Titolo</th>
						<th>Prezzo</th>
						<th></th>
					</tr>
					<%
					for(PurchasedMovie movie : cart.getPurchasedMovies()) {
						%>
						<tr>
							<td><%= movie.getTitle() %></td>
							<td><%= movie.getPrice() %>€</td>
							<td>
								<a href = "<%= request.getContextPath() %>/browse/UpdateCartServlet?action=remove&movieid=<%= movie.getId() %>">
									X
								</a>
							</td>
						</tr>
						<%
					}
					%>
				</table>
			</div>
		<% } %>
		
		<% if(!cart.getRentedMovies().isEmpty()) { %>
			<div class = "tablecontainer">
				<table>
					<caption>Noleggi</caption>
					<tr>
						<th>Titolo</th>
						<th>Prezzo Giornaliero</th>
						<th>Giorni</th>
						<th>Totale</th>
						<th></th>
					</tr>
					<%
					for(RentedMovie movie : cart.getRentedMovies()) {
						%>
						<tr>
							<td><%= movie.getTitle() %></td>
							<td><%= movie.getDailyPrice() %>€</td>
							<td><%= movie.getDays() %></td>
							<td><%= movie.getDailyPrice() * movie.getDays() %>€</td>
							<td>
								<a href = "<%= request.getContextPath() %>/browse/UpdateCartServlet?action=remove&movieid=<%= movie.getId() %>">
									X
								</a>
							</td>
						</tr>
						<%
					}
					%>
				</table>
			</div>
		<% } %>
	</div>
</body>
</html>