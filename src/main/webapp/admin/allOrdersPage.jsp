<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.* , java.util.*, java.time.format.DateTimeFormatter"%>
    
<%
	User user = (User)request.getSession().getAttribute("user");
	ArrayList<Order> orders = (ArrayList<Order>)request.getAttribute("orders");
	
	if(orders == null) {
		request.getRequestDispatcher("/admin/GetAllOrdersServlet").forward(request, response);
		return;	
	}
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Ordini</title>
	
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/table.css">
</head>
<body>
	<jsp:include page = "/fragments/header.jsp"/>
	<div class = "page">
		<h1>Tutti gli ordini</h1>
		
		<form>
			<label for ="from">Dal</label>
			<input type="date" id = "from">
			
			<label for ="to">al</label>
			<input type="date" id = "to">
		</form>
		
		<div class = "tablecontainer">
			<table>
				<tr>
					<th>Ordine</th>
					<th>Data</th>
					<th>Totale</th>
					<th>Email</th>
					<th></th>
				</tr>
				<%
				for(Order order : orders) {
					%>
					<tr>
						<td><%= order.getId() %></td>
						<td><%= order.getDate().format(formatter) %></td>
						<td><%= order.getAmount() %>€</td>
						<td><%= order.getUser().getEmail() %></td>
						<td><a href ="<%= request.getContextPath() %>/browse/GetOrdersServlet?userid=<%=order.getUser().getId()%>&orderid=<%=order.getId()%>">Dettagli</a></td>
					</tr>
					<%
				}
				%>
			</table>
		</div>
	</div>
</body>
</html>