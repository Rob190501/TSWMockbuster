<%@ page language = "java" contentType = "text/html; charset = UTF-8"
    pageEncoding = "UTF-8" import = "java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset = "UTF-8">
	
	<title>Signup</title>
	
	<link rel  =  "stylesheet" href  =  "<%= request.getContextPath() %>/styles/signup.css">
	
	<script type  =  "text/javascript" src  =  "<%= request.getContextPath() %>/scripts/signup.js"></script>
</head>
<body>
	<form id  =  "signupForm" action = "<%= request.getContextPath() %>/SignupServlet"
	 	method = "post">
		<div>
			<label for = "email">Email</label>
			<input type = "email" id = "email" name = "email"
				required onblur = "validateFormField(this, 'emailErrorSpan', emailError)">
			<span id = "emailErrorSpan" class = "error"></span>
		</div>
		
		<div>
			<label for = "password">Password</label>
			<input type = "password" id = "password" name = "password"
				required pattern = "^(\w+){4,10}$"
				onblur = "validateFormField(this, 'passwordErrorSpan', passwordError)">
			<span id = "passwordErrorSpan" class = "error"></span>
		</div>
		
		<div>
			<label for = "firstName">Nome</label>
			<input type = "text" id = "firstName" name = "firstName"
				required pattern = "^[A-Za-z]+$"
				onblur = "validateFormField(this, 'firstNameErrorSpan', nameError)">
			<span id = "firstNameErrorSpan" class = "error"></span>
		</div>
		
		<div>
			<label for = "lastName">Cognome</label>
			<input type = "text" id = "lastName" name = "lastName"
				required pattern = "^[A-Za-z]+$"
				onblur = "validateFormField(this, 'lastNameErrorSpan', nameError)">
			<span id = "lastNameErrorSpan" class = "error"></span>
		</div>
		
		<div>
			<label for = "billingAddress">Indirizzo fatturazione</label>
			<input type = "text" id = "billingAddress" name = "billingAddress"
				required pattern = "^([A-Za-z]+\s)+\d+\s\d{5}\s[A-Za-z]+$"
				onblur = "validateFormField(this, 'billingAddressErrorSpan', addressError)">
			<span id = "billingAddressErrorSpan" class = "error"></span>
		</div>
		
		<input type = "submit" onclick="return validateForm()">
	</form>
	
	<div id="prova">
	</div>
	
	<div>
		<%
		List<String> errors = (List<String>) request.getAttribute("errors");
		if(errors != null) {
			for(String e: errors) { %>
				<%= e %> <br>	
			<%}
		}
		%>
	</div>
</body>
</html>