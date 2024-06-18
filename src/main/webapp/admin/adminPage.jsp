<%@page import="javax.websocket.Session"%>
<%@page import="model.dao.UtenteDAO"%>
<%@page import="model.Utente"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%
	Utente utente = (Utente)session.getAttribute("utente");
	UtenteDAO utenteDAO = new UtenteDAO((DataSource)getServletContext().getAttribute("DataSource"));
	List<Utente> userList = (List<Utente>)utenteDAO.retrieveAll("");
%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Admin <%= utente.getEmail() %>
	
	<table style="border: 2px solid black">
		<tr>
			<th>Username</th>
			<th>Password</th>
			<th>isAdmin</th>
		</tr>
		<%
		for(Utente u: userList) {
		%>
		<tr>
			<td><%= u.getEmail() %></td>
			<td><%= u.getPassword() %></td>
			<td><%= u.getIndirizzoFatturazione() %></td>
			<td><%= u.isAdmin() %></td>
		</tr>
		<%}%>
	</table>
	
	<a href="<%= request.getContextPath() %>/LogoutServlet">Log out</a>
</body>
</html>