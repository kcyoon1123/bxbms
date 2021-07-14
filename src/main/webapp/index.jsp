<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주간보고</title>
</head>
<body>
<%@include file="/WEB-INF/include/header.jsp" %>
<%@include file="/WEB-INF/include/nav.jsp" %>
<c:if test="${session == null}">
		<form role="form" method="post" action="/member/login.do" autocomplete="off">
			<p>
				<label for="userId">아이디:</label>
				<input name="userId" type="text"/>
			</p>
			<p>
				<label for="userPass">비밀번호:</label>
				<input name="userPass" type="password"/>
			</p>
			<p><button type="submit">로그인</button></p>
			<p><a href="/member/memberSignup.do">회원가입</a></p>
		</form>
	</c:if>
	<c:if test="${msg == false}">
		<p>로그인실패</p>
	</c:if>
<%@include file="/WEB-INF/include/footer.jsp" %>
</body>
</html>