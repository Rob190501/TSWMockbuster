<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.*"%>
<%
	User user = (User)request.getSession().getAttribute("user");
%>

<header>
	<div class = "userinfo">
	<% if(user == null) { %>
		<a href = "<%= request.getContextPath() %>/common/login.jsp">Log in</a>	
	<% } else { %>
		Benvenuto <%= user.getFirstName() %> <%= user.getId() %>
		<% if(user.isAdmin()) { %>
			<a href = "<%= request.getContextPath() %>/admin/adminPage.jsp">Admin Page</a>	
		<% } %>
		<a href = "<%= request.getContextPath() %>/browse/browsePage.jsp">Browse Page</a>
		<a href="<%= request.getContextPath() %>/common/LogoutServlet">Log out</a> 
	<% } %>
	</div>
</header>