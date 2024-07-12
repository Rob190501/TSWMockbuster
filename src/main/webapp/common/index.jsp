<%@page import="javax.sql.DataSource"%>
<%@page import="model.dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, java.io.File, model.*"%>

<%
	List<Movie> movieList = (List<Movie>)request.getAttribute("movieList");
	if(movieList == null) {
		request.getRequestDispatcher("/common/MovieRetrieveServlet").forward(request, response);
		return;	
	}
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset = "UTF-8">
	
	<title>Mockbuster</title>
	
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/index.css">
</head>
<body>
	<jsp:include page = "/fragments/header.jsp" />
	<div class = "page">
		<%
			for(Movie movie : movieList) {%>
				<div class = "container"
					onclick = "window.location.href = '<%= request.getContextPath() %>/browse/MoviePageServlet?id=<%= movie.getId() %>'">
					<img src = "<%= request.getContextPath() %>/images/posters/<%= movie.getPosterPath() %>">
				</div>
				<%
			}
		%>
	</div>
</body>
</html>