<%@page import="javax.websocket.Session"%>
<%@page import="model.UserDAO"%>
<%@page import="model.User"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%
	User user = (User)session.getAttribute("user");
	UserDAO userDAO = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
	List<User> userList = (List<User>)userDAO.retrieveAll("");
%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Admin <%= user.getUsername() %>
	
	<table style="border: 2px solid black">
		<tr>
			<th>Username</th>
			<th>Password</th>
			<th>isAdmin</th>
		</tr>
		<% for(User u: userList) { %>
		<tr>
			<td><%= u.getUsername() %></td>
			<td><%= u.getPassword() %></td>
			<td><%= u.isAdmin() %></td>
		</tr>
		<%}%>
	</table>
	
	<a href="<%= request.getContextPath() %>/LogoutServlet">Log out</a>
</body>
</html>