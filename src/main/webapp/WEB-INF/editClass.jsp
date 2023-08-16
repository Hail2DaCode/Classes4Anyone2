<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Class</title>
<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class = "grid">
		<div class = "lgContainer4">
			<h2>Edit Class</h2>
		<p><a href = "/dash">dashboard</a></p>
		<form:form class="form" action="/classes/${klass.id}" method = "put" modelAttribute="klass">
		<div class="add" id = "add1">
			<form:label path="title">Title:</form:label>
			<form:errors class="error" path = "title"/>
			<form:input path="title"/>
		</div>
		<div class="add" id = "add2">
			<form:label path="description">Description:</form:label>
			<form:errors class="error" path = "description"/>
			<form:textarea path="description"  rows="5" columns = "30"/>
		</div>
		<div class = "add" id = "add3">
			<form:input type = "hidden" path = "teacher" value = "${klass.getTeacher().id}"/>
			<form:input type = "hidden" path = "students" values = "${klass.students}"/>
			<form:input type = "hidden" path = "ratings" values = "${klass.ratings}"/>
			<input class="btn" id ="submit" type = "submit" value="Submit"/>
		</div>
	</form:form>
		</div>
	</div>
</body>
</html>