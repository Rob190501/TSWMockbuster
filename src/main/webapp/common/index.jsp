<%@page import="javax.sql.DataSource"%>
<%@page import="model.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="<%= request.getContextPath() %>/common/login.jsp">Log in</a>
</body>
</html>