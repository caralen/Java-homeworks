<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Error</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/main.css" />" />
</head>

<body>
	<h1>Error!</h1>
	<p>
		<c:out value="${message}" />
	</p>

	<p>
		<a
			href="<%=request.getServletContext().getContextPath() + "/index.jsp"%>">Home</a>
	</p>
</body>
</html>