<%@ page import="java.text.SimpleDateFormat, java.util.Date" language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
	body{
		background-color: ${pickedBgCol}
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Time info</title>
</head>
<body>
	<p>
		<%
			String time = application.getAttribute("time").toString();
			long difference = System.currentTimeMillis() - Long.parseLong(time);
			
			SimpleDateFormat days = new SimpleDateFormat("DDD");
			SimpleDateFormat hours = new SimpleDateFormat("HH");
			SimpleDateFormat minutes = new SimpleDateFormat("mm");
			SimpleDateFormat seconds = new SimpleDateFormat("ss");
			SimpleDateFormat miliseconds = new SimpleDateFormat("SS");
			
			Date timePassed = new Date(difference);
			
			out.print(Integer.parseInt(days.format(timePassed))-1 + " days ");
			out.print(Integer.parseInt(hours.format(timePassed))-1 + " hours ");
			out.print(Integer.parseInt(minutes.format(timePassed)) + " minutes ");
			out.print(Integer.parseInt(seconds.format(timePassed)) + " seconds ");
			out.print(Integer.parseInt(miliseconds.format(timePassed)) + " miliseconds");
		%>
	</p>
</body>
</html>