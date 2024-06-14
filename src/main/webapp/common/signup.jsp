<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Signup</title>
</head>
<body>
	<form action="<%= request.getContextPath() %>/SignupServlet" method="post">
		Username <input type="text" name="username"> <br>
		Password <input type="text" name="password"> <br>
		is admin? <input type="checkbox" name="isAdmin"> <br>
		<input type="submit">
	</form>
</body>
</html>