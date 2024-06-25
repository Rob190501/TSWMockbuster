<%@page import="javax.websocket.Session"%>
<%@page import="model.dao.UserDAO"%>
<%@page import="model.User"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%
	User user = (User)session.getAttribute("user");
	UserDAO userDAO = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
	List<User> usersList = (List<User>)userDAO.retrieveAll("");
%>

<!DOCTYPE html>

<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	Admin <%=user.getEmail()%>
	
	<table style="border: 2px solid black">
		<tr>
			<th>Email</th>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Indirizzo Fatturazione</th>
			<th>isAdmin</th>
		</tr>
		<%
		for(User u: usersList) {
		%>
		<tr>
			<td><%= u.getEmail() %></td>
			<!-- <td><%= u.getPassword() %></td>-->
			<td><%= u.getFirstName() %></td>
			<td><%= u.getLastName() %></td>
			<td><%= u.getBillingAddress() %></td>
			<td><%= u.isAdmin() %></td>
		</tr>
		<%}%>
	</table>
	
	<a href="<%= request.getContextPath() %>/common/LogoutServlet">Log out</a>
</body>
</html>