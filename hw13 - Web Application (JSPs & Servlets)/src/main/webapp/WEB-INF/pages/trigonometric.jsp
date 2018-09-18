<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<style>
		body{
			background-color: ${pickedBgCol}
		}
	</style>
	<meta charset="utf-8">
</head>

 <body>
	<h1>Stranica za sinus i kosinus</h1>
	<p>Rezultat je prikazan u nastavku.</p>
	
	<table>
		<thead>
			<tr><th>Broj</th><th>sinus</th><th>kosinus</th></tr>
		</thead>
		<tbody>
			<c:forEach var="entry" items="${trigonometricValues}">
				<tr><td>${entry.number}</td><td>${entry.sinValue}</td><td>${entry.cosValue}</td></tr>
			</c:forEach>
		</tbody>
	</table>
 </body>
</html>