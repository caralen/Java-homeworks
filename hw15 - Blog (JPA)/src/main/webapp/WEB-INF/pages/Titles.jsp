<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Blog</title>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/form.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/main.css" />" />
</head>

<% 
	Object sessionId = session.getAttribute("current.user.id");
	String firstName = null;
	String lastName = null;
	String sessionNick = null;
	
	if(sessionId != null){
		firstName = session.getAttribute("current.user.fn").toString();
		lastName = session.getAttribute("current.user.ln").toString();
		sessionNick = session.getAttribute("current.user.nick").toString();
	}
	
	String logoutPath = request.getServletContext().getContextPath() + "/servleti/logout";
	%>

<body>

	<c:choose>
		<c:when test="<%=sessionId == null%>">
			<h1>Not logged in</h1>
		</c:when>
		<c:otherwise>
			<p class="logout">
				<a href="<%=logoutPath%>">Logout</a>
			</p>
			<h1><%=firstName + " " + lastName%></h1>
		</c:otherwise>
	</c:choose>

	<h2>Here is a list of blog titles written by the chosen author</h2>

	<c:forEach var="entry" items="${entries}">
		<li><a href="${nick}/${entry.id}">${entry.title}</a></li>
	</c:forEach>


	<c:choose>
		<c:when
			test="<%= sessionId != null && sessionNick.equals(request.getAttribute(\"nick\")) %>">
			<br>
			<a href="<%= sessionNick %>/new">Create new blog entry</a>
		</c:when>
	</c:choose>

	<br>
	<br>
	<a
		href="<%= request.getServletContext().getContextPath() + "/servleti/" + "main" %>">Home</a>
</body>
</html>
