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
		Email <input type="text" name="email"> <br>
		Password <input type="password" name="password"> <br>
		Indirizzo fatturazione <input type="text" name="indirizzoFatturazione"> <br>
		<input type="submit">
	</form>
</body>
</html>