<%@ page import="java.util.Random" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
	<p>
		<font
			color=
			<%
			String[] colors = new String[]{"red", "green", "blue", "black"};
			Random rand = new Random();
			int n = rand.nextInt(4);
			
			out.println(colors[n]);
			%>>
			Napije se orao kao zemlja i padne na neku livadu, vrti mu se u glavi i kaze: 
			STA JE OVO LJUDI, KAO DA SAM KOPAO, A NE ORAO... 
			
		</font>
	</p>
</body>
</html>