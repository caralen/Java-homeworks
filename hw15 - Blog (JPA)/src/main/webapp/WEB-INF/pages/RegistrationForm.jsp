<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Registration</title>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/form.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/index.css" />" />
</head>

<body>
	<h1>Register new user</h1>

	<div class="form">
		<form action="save" method="post">
	
			<div>
				<div>
					<span class="formLabel">First name</span><input type="text"
						name="firstName" value='<c:out value="${entry.firstName}"/>'
						size="20">
				</div>
				<c:if test="${entry.hasError('firstName')}">
					<div class="error">
						<c:out value="${entry.fetchError('firstName')}" />
					</div>
				</c:if>
			</div>
	
			<div>
				<div>
					<span class="formLabel">Last name</span><input type="text"
						name="lastName" value='<c:out value="${entry.lastName}"/>'
						size="20">
				</div>
				<c:if test="${entry.hasError('lastName')}">
					<div class="error">
						<c:out value="${entry.fetchError('lastName')}" />
					</div>
				</c:if>
			</div>
	
			<div>
				<div>
					<span class="formLabel">Nickname</span><input type="text"
						name="nick" value='<c:out value="${entry.nick}"/>' size="20">
				</div>
				<c:if test="${entry.hasError('nick')}">
					<div class="error">
						<c:out value="${entry.fetchError('nick')}" />
					</div>
				</c:if>
			</div>
	
			<div>
				<div>
					<span class="formLabel">Email</span><input type="email" name="email"
						value='<c:out value="${entry.email}"/>' size="20">
				</div>
				<c:if test="${entry.hasError('email')}">
					<div class="error">
						<c:out value="${entry.fetchError('email')}" />
					</div>
				</c:if>
			</div>
	
			<div>
				<div>
					<span class="formLabel">Password</span><input type="password"
						name="password" value='<c:out value="${entry.password}"/>'
						size="20">
				</div>
				<c:if test="${entry.hasError('password')}">
					<div class="error">
						<c:out value="${entry.fetchError('password')}" />
					</div>
				</c:if>
			</div>
	
			<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit"
					name="method" value="Save"> <input type="submit"
					name="method" value="Cancel">
			</div>
	
		</form>
	</div>
	

	<a class="home" href="<%=request.getServletContext().getContextPath() + "/servleti/" + "main"%>">Home</a>
</body>
</html>
