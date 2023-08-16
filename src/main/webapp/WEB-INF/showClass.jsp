<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value = "${klass.title }"/> Class</title>
<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class = "grid">
		<div class = "lgContainer4">
			<div class = "navBar">
			<p><a href = "/dash">Dashboard</a></p>
			<c:if test = "${klass.teacher.id != student.id }">
				<p><a href = "/classes/${klass.id }/rate">Rate</a></p>
			</c:if>
		</div>
		<h1><c:out value = "${ klass.title }"/> by <c:out value = "${ klass.teacher.userName }"/></h1>
		<p>Description: <c:out value = "${ klass.description }"/></p>
		<p>Average Rating: <c:if test = "${averageRatings.containsKey(klass.id) }">
		<c:out value = "${averageRatings.get(klass.id) }"/>
		</c:if></p>
		<c:choose>
			<c:when test = "${klass.teacher.id == student.id}">
				<p><a href = "/classes/${klass.id }/edit">edit</a></p>
				<p><a href = "/classes/${klass.id }/delete">delete</a></p>
			</c:when>
		</c:choose>
		</div>	
	</div>
</body>
</html>