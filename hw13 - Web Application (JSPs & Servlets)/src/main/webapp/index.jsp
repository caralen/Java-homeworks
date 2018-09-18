<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
	body{
		background-color: ${pickedBgCol}
	}
</style>
</head>
<head>
	<title>hw13</title>
</head>
<body>
	<a href="colors.jsp">Background color chooser</a>
	<br><br>
	
	<a href="trigonometric?a=0&b=90">Trigonometric from 0 to 90</a>
	<br><br>
	
	<form action="trigonometric" method="GET">
		Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
	</form>
	<br><br>
	
	<a href="stories/funny.jsp">Funny story</a>
	<br><br>
	
	<a href="report.jsp">Show OS usage</a>
	<br><br>
	
	<a href="powers?a=1&b=100&n=3">Generate excel file with powers</a>
	<br><br>
	
	<a href="appinfo.jsp">App info</a>
	<br><br>
	
	<a href="glasanje">Voting page</a>
</body>
</html>