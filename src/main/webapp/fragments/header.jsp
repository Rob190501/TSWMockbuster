<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.*"%>
<%
	User user = (User)request.getSession().getAttribute("user");
%>

<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/header.css">
	
<script type="text/javascript" src = "<%= request.getContextPath() %>/scripts/header.js"></script>

<header>
	<a href = "<%= request.getContextPath() %>/common/index.jsp" class = "logo">
		MOCKBUSTER
	</a>
	
	<div class = "nav">
	<% if(user == null) { %>
		<a href = "<%= request.getContextPath() %>/common/login.jsp">Accedi</a>	
	<% } else { %>
		<img src ="<%= request.getContextPath() %>/images/icons/menu.png"
			id = "hamburger" class = "hamburger"
			onclick = "toggleMenuVisibility('<%= request.getContextPath() %>')">
		
		<div id = "menu" class = "menu">
			<% if(user.isAdmin()) { %>
				<a href = "<%= request.getContextPath() %>/admin/adminPage.jsp">Admin Page</a>
				<a href = "<%= request.getContextPath() %>/admin/movieUpload.jsp">Movie upload</a>
				<a href = "<%= request.getContextPath() %>/admin/allOrdersPage.jsp">Tutti gli ordini</a>	
			<% } %>
			<a href = "<%= request.getContextPath() %>/browse/ordersPage.jsp">I miei ordini</a>
			<a href = "<%= request.getContextPath() %>/browse/myAccountPage.jsp">Il mio account</a>
			<a href = "<%= request.getContextPath() %>/common/LogoutServlet">Esci</a>
		</div>
		
		<img src ="<%= request.getContextPath() %>/images/icons/search.png"
			id = "lens" class = "lens"
			<% if(request.getRequestURI().equals(request.getContextPath()+"/common/index.jsp")) {%>
				onclick = "toggleSearchbarVisibility('<%= request.getContextPath() %>')"
			<% } else { %>
				onclick = "window.location.href = '<%= request.getContextPath() %>/common/index.jsp?search='"
			<% } %>
		>
		
		<a href = "<%= request.getContextPath() %>/browse/cartPage.jsp">
			<img src = "<%= request.getContextPath() %>/images/icons/cart.png">
		</a>
		
		<div class = "greetings">
			<span>Ciao <%= user.getFirstName() %>!</span>
			<span>Saldo: <%= user.getCredit() %>€</span>
		</div>
	<% } %>
	</div>
</header>