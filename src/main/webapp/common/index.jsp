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
	
	<title>Insert title here</title>
	
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/header.css">
	
	<script type="text/javascript" src = "<%= request.getContextPath() %>/scripts/header.js"></script>
</head>
<body>
	<jsp:include page = "/fragments/header.jsp" />
	<div id = "page">
		<%
			for(Movie movie : movieList) {%>
				<!-- <%= movie.getTitle() %><br>-->
				<div class = "container">
					<img src = "<%= request.getContextPath() %>/images/posters/<%= movie.getPosterPath() %>">
				</div>
				<%
			}
		%>
	</div>
</body>
</html>