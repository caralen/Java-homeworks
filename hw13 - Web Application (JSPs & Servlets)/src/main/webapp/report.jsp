<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
<style>
	body{
		background-color: ${pickedBgCol}
	}
</style>
</head>

<body>
	<h1>OS usage</h1>
	<p>Here are the results of OS usage in survey that we completed.</p>
	<img alt="Pie-chart" src="reportImage" />
</body>
</html>