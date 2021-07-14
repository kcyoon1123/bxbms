<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<hr/>
<nav>
<c:if test="${session == null}">
	<a href="/">로그인</a>
</c:if>
<c:if test="${session != null}">
	<div class="main">
	    <div style="display: inline;"><a href="/dayrep/dayrepList.do">일간보고</a></div>&emsp;
	    <div style="display: inline;"><a href="/weekrep/weekrepList.do">주간보고</a></div>&emsp;
	    <div style="display: inline;"><a href="/monthrep/monthrepList.do">월간보고</a></div>
	</div>
</c:if>
</nav>
<hr/>
<section>