<%@page import="javax.sql.DataSource"%>
<%@page import="model.dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<a href="<%= request.getContextPath() %>/common/login.jsp">Log in</a>
</body>
</html>