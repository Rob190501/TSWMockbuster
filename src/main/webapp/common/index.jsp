<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*, java.io.File, model.*"%>

<%
	ArrayList<Movie> movieList = (ArrayList<Movie>)request.getAttribute("movieList");
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
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/container.css">
	<script type="text/javascript" src = "<%= request.getContextPath() %>/scripts/search.js"></script>
</head>
<body>
	<jsp:include page = "/fragments/header.jsp" />
	<div class = "page">
		
		<input type="text" id = "searchbar" class = "searchbar"
			onkeyup = "retrieveMovies('<%=request.getContextPath()%>')">
		
		<div id = "containers">
			<%
			for(Movie movie : movieList) {%>
				<div class = "container">
					<a href = "<%= request.getContextPath() %>/browse/MoviePageServlet?id=<%= movie.getId() %>">
						<img src = "<%= request.getContextPath() %>/images/posters/<%= movie.getPosterPath() %>">
					</a>
				</div>
				<%
			}
			%>
		</div>
	</div>
</body>
</html>