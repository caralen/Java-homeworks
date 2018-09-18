<%@ page import="hr.fer.zemris.java.hw15.model.BlogEntryForm"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Blog entry</title>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/form.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/main.css" />" />
</head>

<% 
	BlogEntryForm form = (BlogEntryForm) request.getAttribute("entry");
	
	Object sessionId = session.getAttribute("current.user.id");
	String firstName = null;
	String lastName = null;
	if(sessionId != null){
		firstName = session.getAttribute("current.user.fn").toString();
		lastName = session.getAttribute("current.user.ln").toString();
	}
	
	String logoutPath = request.getServletContext().getContextPath() + "/servleti/logout";
	%>

<body>

	<c:choose>
		<c:when test="<%= sessionId == null %>">
			<h1>Not logged in</h1>
		</c:when>
		<c:otherwise>
			<p align="right">
				<a href="<%= logoutPath %>">Logout</a>
			</p>
			<h1 align="center"><%= firstName + " " + lastName %></h1>
		</c:otherwise>
	</c:choose>

	<h2>
		<c:choose>
			<c:when test="<%= form.getId() == null %>">
		New blog entry
		</c:when>
			<c:otherwise>
		Edit blog entry
		</c:otherwise>
		</c:choose>
	</h2>

	<% String path = request.getServletContext().getContextPath() + "/servleti/"; %>

	<form action="<%= path + "saveBlog" %>" method="post">

		<div>
			<div>
				<span class="formLabel"></span><input type="hidden" name="id"
					value='<c:out value="${entry.id}"/>' size="20">
			</div>
			<c:if test="${entry.hasError('id')}">
				<div class="error">
					<c:out value="${entry.fetchError('id')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Title</span><input type="text" name="title"
					value='<c:out value="${entry.title}"/>' size="50">
			</div>
			<c:if test="${entry.hasError('title')}">
				<div class="error">
					<c:out value="${entry.fetchError('title')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Text</span><input type="text" name="text"
					value='<c:out value="${entry.text}"/>' size="50">
			</div>
			<c:if test="${entry.hasError('text')}">
				<div class="error">
					<c:out value="${entry.fetchError('text')}" />
				</div>
			</c:if>
		</div>


		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Save"> <input type="submit"
				name="method" value="Cancel">
		</div>

	</form>

	<br>
	<br>
	<a href="<%= path + "main" %>">Home</a>

</body>
</html>
