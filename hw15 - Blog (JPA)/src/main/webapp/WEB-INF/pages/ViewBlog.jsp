<%@ page import="hr.fer.zemris.java.hw15.model.BlogEntry"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Blog entry</title>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/form.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/main.css" />" />
</head>

<%
	Object sessionId = session.getAttribute("current.user.id");
	String firstName = null;
	String lastName = null;
	if (sessionId != null) {
		firstName = session.getAttribute("current.user.fn").toString();
		lastName = session.getAttribute("current.user.ln").toString();
	}
	
	String nick = request.getAttribute("nick").toString();

	String logoutPath = request.getServletContext().getContextPath() + "/servleti/logout";
	
	Object obj = session.getAttribute("current.user.nick");
	String sessionNick = null;
	if (obj != null) {
		sessionNick = session.getAttribute("current.user.nick").toString();
	}
	BlogEntry entry = (BlogEntry) request.getAttribute("blog");
%>

<body>

	<c:choose>
		<c:when test="<%=sessionId == null%>">
			<h1>Not logged in</h1>
		</c:when>
		<c:otherwise>
			<p align="right">
				<a href="<%=logoutPath%>">Logout</a>
			</p>
			<h1 align="center"><%=firstName + " " + lastName%></h1>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${blog == null}">
      		No entry!
    	</c:when>
		<c:otherwise>
			<h2>
				<c:out value="${blog.title}" />
			</h2>
			<p>
				<c:out value="${blog.text}" />
			</p>


			<c:choose>
				<c:when test="<%= sessionNick != null && sessionNick.equals(nick) %>">
					<a href="<%=request.getServletContext().getContextPath() + "/servleti/author/" + sessionNick
									+ "/edit?id=" + entry.getId()%>">Edit</a>
				</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>

			<c:if test="${!blog.comments.isEmpty()}">
				<ul>
					<c:forEach var="comment" items="${blog.comments}">
						<li><div style="font-weight: bold">
								[User=
								<c:out value="${comment.usersEMail}" />
								]
								<c:out value="${comment.postedOn}" />
							</div>
							<div style="padding-left: 10px;">
								<c:out value="${comment.message}" />
							</div></li>
					</c:forEach>
				</ul>
			</c:if>
		</c:otherwise>
	</c:choose>

	<br>
	<br>

	<%
		String path = request.getServletContext().getContextPath() + "/servleti/";
	%>

	<form action="<%=path + "saveComment"%>" method="post">

		<div>
			<div>
				<span class="formLabel"></span><input type="hidden" name="entryId"
					size="20" value='<c:out value="${form.entryId}"/>' size="20">
			</div>
		</div>

		<div>
			<div>
				<span class="formLabel">Add comment: </span><input type="text"
					name="comment" size="50">
			</div>
			<c:if test="${form.hasError('message')}">
				<div class="error">
					<c:out value="${form.fetchError('message')}" />
				</div>
			</c:if>
		</div>

		<br>

		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Save"> <input type="submit"
				name="method" value="Cancel">
		</div>

	</form>

	<br>
	<br>
	<a href="<%=request.getServletContext().getContextPath() + "/servleti/" + "main"%>">Home</a>
</body>
</html>
