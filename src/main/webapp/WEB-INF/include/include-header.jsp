<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Responsive navbar-->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container">
          <a class="navbar-brand" href="#!"><img src="/static/assets/img/bxsystem.png" style="width:auto; height:50px; display: block; margin: auto;"></a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
          <c:if test="${session != null}">
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                  <li class="nav-item"><a class="nav-link" href="/myPage/myPage.do">${session.userNm }로 로그인</a></li>
                  <li class="nav-item"><a class="nav-link" href="/myPage/myPage.do">MYPAGE</a></li>
                  <c:if test="${session.roleId == 'ADMIN'}">
                  <li class="nav-item dropdown">
				    <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="true">ADMIN</a>
				    <div class="dropdown-menu" style="position: absolute; inset: 0px auto auto 0px; margin: 0px; transform: translate(0px, 42px);" data-popper-placement="bottom-start">
				      <a class="dropdown-item" href="/admin/member/memberList.do">회원관리</a>
				      <a class="dropdown-item" href="/admin/code/codeList.do">공통코드 관리</a>
				    </div>
				  </li>
                  </c:if>
                  <li class="nav-item"><a class="nav-link" href="/weekrep/weekrepList.do">주간보고</a></li>
                  <li class="nav-item"><a class="nav-link" href="/member/logout.do">LOGOUT</a></li>
              </ul>
          </div>
          </c:if>
      </div>
  </nav>