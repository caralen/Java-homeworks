<%@ page import="hr.fer.zemris.java.hw14.model.PollOption" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Glasanje</title>
</head>
<body>
	<h1>${poll.title}</h1>
	<p>${poll.message}</p>
		
	<ol>
		<c:forEach var="option" items="${options}">
			<li><a href="glasanje-glasaj?pollID=${poll.id}&optionID=${option.id}">${option.optionTitle}</a></li>
		</c:forEach>
	</ol>
	
	<br><br>
	<a href="index.html">Back</a>
</body>
</html>