<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.*"%>
<%
	User user = (User)request.getSession().getAttribute("user");
%>

<header>
	<a href = "<%= request.getContextPath() %>/common/index.jsp" class = "logo">
		MOCKBUSTER
	</a>
	<div class = "userinfo">
	<% if(user == null) { %>
		<a href = "<%= request.getContextPath() %>/common/login.jsp">Accedi</a>	
	<% } else { %>
		<div class = "greetings">Ciao <%= user.getFirstName() %>!</div>
		
		<img src ="<%= request.getContextPath() %>/images/icons/menu.png"
			class = "hamburger" onclick = "toggleMenuVisibility()">
	
		
		<div id = "menu" class = "menu">
			<% if(user.isAdmin()) { %>
				<a href = "<%= request.getContextPath() %>/admin/adminPage.jsp">Admin Page</a>
				<a href = "<%= request.getContextPath() %>/admin/movieUpload.jsp">Movie upload</a>	
			<% } %>
			<a href = "<%= request.getContextPath() %>/browse/browsePage.jsp">Browse Page</a>
			<a href="<%= request.getContextPath() %>/common/LogoutServlet">Log out</a> 
		</div>
	<% } %>
	</div>
</header>