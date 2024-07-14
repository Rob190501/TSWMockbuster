<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.* , java.util.*"%>
    
<%
	User user = (User)request.getSession().getAttribute("user");
	List<Order> orders = (List<Order>)request.getAttribute("orders");
	
	if(orders == null) {
		request.getRequestDispatcher("/browse/GetOrdersServlet").forward(request, response);
		return;	
	}
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Ordini</title>
	
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
</head>
<body>
	<jsp:include page = "/fragments/header.jsp"/>
	<div class = "page">
		<%
			for(Order order : orders) {
				%> 
				<div>
					<p>Ordine n. <%= order.getId() %></p>
					<p>Data ordine <%= order.getDate() %></p>
					<p>Totale: <%= order.getAmount() %>€</p>
					<a href ="<%= request.getContextPath() %>/browse/GetOrdersServlet?userid=<%=user.getId()%>&orderid=<%=order.getId()%>">Dettagli</a>
				</div>
				<hr>
				<%
			}
		%>
	</div>
</body>
</html>