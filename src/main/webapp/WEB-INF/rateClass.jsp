<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Classes4Anyone</title>
<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class = "grid">
		<div class = "navDash">
			<p><a href = "/dash">dashboard</a></p>
			<p><a href = "/clear">Log out</a></p>
		</div>
		<div class = "lgContainer4">
			<h1><span><c:out value = "${ klass.title }"/> by <c:out value = "${ klass.teacher.userName }" /></span></h1>
		<form action = "/rate/class" method = "post" class = "form" >
			<div class = "add">
				<label for = "rating">Rating (1-5):</label>
				<input name = "rating" type = "number" min="1" max = "5"/>
			</div>
			<div class = "add">
				<input type = "hidden" name = "student" value = "${student.id}"/>
				<input type = "hidden" name = "klass" value = "${klass.id}"/>
				<input class="btn" id ="submit" type = "submit" value="Submit"/>
			</div>
		</form>
		</div>
	</div>
</body>
</html>