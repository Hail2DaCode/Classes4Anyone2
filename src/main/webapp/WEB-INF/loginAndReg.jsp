<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Classes4Anyone</title>
 <link rel="stylesheet" type="text/css" href="/css/style.css">
<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous"> -->
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script> -->
</head>
<body>
<!-- 	<div class = "super"> -->
		<div class = "grid">
		<div class = "nav">
			<h1>Welcome to Classes4Anyone!</h1>
			<p>Join our growing community by creating a class or joining one.</p>
		</div>
<!-- 		<div class = "rowParagraph"> -->
			<div class = "lg-1-3">
			<form:form action = "/register" method = "post" modelAttribute = "newUser" class="">
			<div class = "row" id = "header">
				<h2>Register</h2>
			</div>
			<div class="row" id = "row1">
				<form:label path="userName">User Name:</form:label>
				<form:errors class="error" path = "userName"/>
				<form:input path="userName"/>
			</div>
			<div class="row" id = "row2">
				<form:label path="email">Email:</form:label>
				<form:errors class="error" path = "email"/>
				<form:input path="email"/>
			</div>
			<div class="row" id = "row3">
				<form:label path="password">Password:</form:label>
				<form:errors class="error" path = "password"/>
				<form:input path="password" type = "password"/>
			</div>
			<div class="row" id = "row4">
				<form:label path="confirm">Confirm PW:</form:label>
				<form:errors class="error" path = "confirm"/>
				<form:input path="confirm" type = "password"/>
			</div>
			<div class="row" id = "finalRow">
				<input class="btn" id ="submit" type = "submit" value="Submit"/>
			</div>
		</form:form>
		</div>
		<div class = "lg-3-5">
		<form:form action = "/login" method = "post" modelAttribute = "newLogin" class="">
			<div class = "row" id = "header2">
				<h3>Login</h3>
			</div>
			<div class="row" id = "row6">
				<form:label path="email">Email:</form:label>
				<form:errors class="error" path = "email"/>
				<form:input path="email"/>
			</div>
			<div class="row" id = "row7">
				<form:label path="password">Password:</form:label>
				<form:errors class="error" path = "password"/>
				<form:input path="password" type = "password"/>
			</div>
			<div class="row" id = "row8">
				<input class="btn" id ="submit" type = "submit" value="Submit"/>
			</div>
		</form:form>
		</div>
		</div>
</body>
</html>