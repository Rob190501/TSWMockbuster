<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
	<form action="<%= request.getContextPath() %>/LoginServlet" method="post">
		Username: <input type="text" name="username">
		<br>
		Password: <input type="password" name="password">
		<br>
		<input type="submit" value="log in">
	</form>
</body>
</html>