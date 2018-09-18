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
	<a href="setcolor?color=white">WHITE</a>
	<a href="setcolor?color=red">RED</a>
	<a href="setcolor?color=green">GREEN</a>
	<a href="setcolor?color=cyan">CYAN</a>
</body>
</html>