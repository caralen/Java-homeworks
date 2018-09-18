<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Home</title>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/form.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/index.css" />" />
</head>

<body>
	<%
		Object sessionId = session.getAttribute("current.user.id");
		String firstName = null;
		String lastName = null;
		if (sessionId != null) {
			firstName = session.getAttribute("current.user.fn").toString();
			lastName = session.getAttribute("current.user.ln").toString();
		}
	%>

	<c:choose>
		<c:when test="<%= sessionId != null %>">
			<p class="logout">
				<a href="logout">Logout</a>
			</p>
			<h1>Welcome <%= firstName + " " + lastName %></h1>
		</c:when>
		<c:otherwise>
			<h2>Login for more options</h2>
			
			<div class="form">
				<form action="login" method="post">
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
						<span class="formLabel">&nbsp;</span> <input id="btnAccept" type="submit"
							name="method" value="Login"> <input id="btnCancel" type="submit"
							name="method" value="Cancel">
					</div>
				</form>
				<br>
				<p class="register">Not yet registered? Register <a href="register">here</a></p>
			</div>
			
				

			<br><br><br>

		</c:otherwise>
	</c:choose>


	<div class="list">
		<h3>Here is a list of registered authors</h3>
		<c:forEach var="user" items="${authors}">
			<li><c:out value="${user.firstName}" /> <c:out
					value="${user.lastName}" /> (<c:out value="${user.nick}" />) <a
				href="author/${user.nick}">Show</a></li>
		</c:forEach>
	</div>
	

</body>
</html>
