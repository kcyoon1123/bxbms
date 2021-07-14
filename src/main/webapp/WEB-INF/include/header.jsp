<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<div class="container">
	<header>
<h1>BMS</h1>
<div style="float:right;">
<c:if test="${session != null}">
		<p>${session.userNm }로 로그인중</p>
		<input type="hidden" id ="memId" name="memId" value="${session.memId}" />
		<p><a href="/myPage/myPage.do">MYPAGE</a></p>
		<a href="/member/logout.do">로그아웃</a>
		<c:if test="${session.roleId == 'ADMIN'}">
			<a href="/admin/member/memberList.do">관리자페이지</a>		
		</c:if>
</c:if>
</div>	
</header>