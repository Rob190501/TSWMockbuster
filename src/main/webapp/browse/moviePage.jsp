<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
    
<%
	User user = (User)session.getAttribute("user");
	Movie movie = (Movie)request.getAttribute("movie");
%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	
	<title><%= movie.getTitle() %></title>
	
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/moviePage.css">
	
	<script type="text/javascript" src = "<%= request.getContextPath() %>/scripts/moviePage.js"></script>
	
	<style type="text/css">
		body {
			background-image: url("<%= request.getContextPath() %>/images/posters/<%= movie.getPosterPath() %>");
		}
	</style>
</head>
<body>
	<div class = "overlay"></div>
	
	<jsp:include page = "/fragments/header.jsp" />
	
	<div class = "page">
		<div class = "details">
			<h1><%= movie.getTitle() %></h1>
			<p><%= movie.getPlot() %></p>
		</div>
		
		<div class = "controls">
			<p>Disponibilità: <%= movie.getAvailableLicenses() %></p>
			
			<% if(movie.getAvailableLicenses() > 0) { %>
				<a href = "<%= request.getContextPath() %>/browse/UpdateCartServlet?action=add&type=purchase&movieid=<%= movie.getId()%>">
					Acquista: <%= movie.getPurchasePrice() %>€
				</a>
				
				<a id = "rentButton" href = "<%= request.getContextPath() %>/browse/UpdateCartServlet?action=add&type=rent&movieid=<%= movie.getId() %>&days=1">
					Noleggia: <%= movie.getDailyRentalPrice() %>€/gg x 1gg
				</a>
				
				<input type = "range" min = "1" max = "<%= movie.getAvailableLicenses() %>" value = "1"
					class = "slider" id = "rentDays"
					oninput = "updateRentButton('<%= request.getContextPath() %>', <%= movie.getId() %>, <%= movie.getDailyRentalPrice() %>)">
			<% } %>
		</div>
	</div>
</body>
</html>