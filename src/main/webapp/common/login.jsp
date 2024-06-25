<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<%
		List<String> errors = (List<String>) request.getAttribute("errors");
		if(errors != null) {
			for(String e: errors) { %>
				<%= e %> <br>	
			<%}
		}
	%>
	<form action="<%= request.getContextPath() %>/common/LoginServlet" method="post">
		Email: <input type="text" name="email">
		<br>
		Password: <input type="password" name="password">
		<br>
		<input type="submit" value="log in">
	</form>
	<a href="signup.jsp">Sign up</a>
</body>
</html>