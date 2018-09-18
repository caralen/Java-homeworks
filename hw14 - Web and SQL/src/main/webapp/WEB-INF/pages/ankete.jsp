<%@ page import="hr.fer.zemris.java.hw14.model.PollOption" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Glasanje</title>
</head>
<body>
	<h1>Dobrodošli u web aplikaciju za ankete</h1>
	<p>Izaberite jednu od ponuđenih anketa i glasajte za svoj izbor</p>
		
	<ol>
		<c:forEach var="poll" items="${polls}">
			<li><a href="glasanje?pollID=${poll.id}">${poll.title}</a></li>
		</c:forEach>
	</ol>
</body>
</html>