<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Blog Home - Start Bootstrap Template</title>
<!-- Core theme CSS (includes Bootstrap)-->
<link href="/static/css/styles.css" rel="stylesheet" /> 
<link href="/static/css/bootstrap.css" rel="stylesheet" />
<!-- js cdn  -->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- Core theme CSS (includes Bootstrap)-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 <script src="/static/js/scripts.js"></script>
<script type="text/javascript">
// 저장 버튼
function fn_memModify(id) {
	
	var membSt = $("#membSt_"+id).val();
	var roleId = $("#roleId_"+id).val();
 	$.ajax({
        url : '/admin/member/adminmemberModify.ajax'
        , method : "POST"
        , data : {memId : id, roleId : roleId, membSt : membSt}
        , dataType : "json"
        , success : function (data) {
        	if (data.formState == "success") {
        	alert("정상적으로 수정되었습니다.");
        	location.reload();
        	}
        }
    }); 
	
	};
// 삭제 버튼
function fn_deleteMember(id) {
	
 	$.ajax({
        url : '/admin/member/deleteMember.ajax'
        , method : "POST"
        , data : {userId : id}
        , dataType : "json"
        , success : function (data) {
        	if (data.formState == "success") {
        	alert("이(가) 정상적으로 삭제되었습니다.");
        	location.reload();
        	}
        }
    }); 
	
	};

</script>
    </head>
    <body>
        <%@include file="/WEB-INF/include/include-header.jsp" %>
        <!-- Page content-->
        <div class="container" style="width:auto;">
        	<div class="row justify-content-center">
        		<div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                         <h1 class="mt-4">ADMIN</h1>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                	회원목록
                            </div>
                            <div class="card-body">
					      <div class="dataTable-container">
					       <form id="weekrepForm" method="post" enctype="multipart/form-data" action="" accept-charset="UTF-8">
					      <table id="weekrepTable" class="dataTable-table" style="text-align: center;">
                                    <thead>
                                        <tr>
                                            <th style="width:10%;">이름</th>
											<th style="width:10%;">소속</th>
											<th style="width:10%;">아이디</th>
											<th style="width:10%;">권한</th>
											<th style="width:10%;">상태</th>
											<th style="width:15%;">가입일</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${memberList}" var="memberList">
									 	<tr>
										  <td>${memberList.comNm}</td>
										  <td>${memberList.userNm}</td>
										  <td>${memberList.userId}</td>
										 	<td>
										 		<select class="dataTable-selector" name="roleId_${memberList.memId}" id="roleId_${memberList.memId}">
												    <option value="USER" <c:if test="${memberList.roleId == 'USER'}"> selected</c:if> >일반사용자</option>
												    <option value="ADMIN" <c:if test="${memberList.roleId == 'ADMIN'}"> selected</c:if> >관리자</option>
												</select>
										 	</td>
										 	<td>
										 		<select class="dataTable-selector" name="membSt_${memberList.memId}" id="membSt_${memberList.memId}">
										 			 <option value="Y" <c:if test="${memberList.membSt == 'Y'}"> selected</c:if> >Y</option>
			    									 <option value="N" <c:if test="${memberList.membSt == 'N'}"> selected</c:if> >N</option>
										 		</select>
										 	</td>
										 	  <td>${memberList.regDt}</td>
											  <td><button type="button" class="btn btn-secondary" onclick="javascript:fn_memModify('${memberList.memId}');">수정</button></td>
											  <td><button type="button" class="btn btn-secondary" onclick="javascript:fn_deleteMember('${memberList.memId}');">삭제</button></td>
									 	</tr>
									 	</c:forEach>
                                    </tbody>
                                </table>
                                </form>
                            </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        	
             </div>
        </div>
       <%@include file="/WEB-INF/include/include-footer.jsp" %>
    </body>
</html>
