<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
    
<%
	User user = (User)session.getAttribute("user");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	browse <%= user.getEmail() %> <%= user.getId() %>
	<a href="<%= request.getContextPath() %>/LogoutServlet">Log out</a>
</body>
</html>