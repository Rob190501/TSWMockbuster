<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
    
<%
     Utente utente = (Utente)session.getAttribute("utente");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	browse <%= utente.getEmail() %>
	<a href="<%= request.getContextPath() %>/LogoutServlet">Log out</a>
</body>
</html>