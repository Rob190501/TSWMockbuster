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
		<h1><%= movie.getTitle() %></h1>
		<p><%= movie.getPlot() %></p>
		<div>
		</div>
	</div>
</body>
</html>