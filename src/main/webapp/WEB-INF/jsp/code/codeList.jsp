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

	// 등록페이지 이동 버튼
	function fn_insert() {
		location.href = '/admin/code/insertCodeView.do';
	};

	// tr클릭 이벤트(목록에서 클릭 시 상세로)
	$("#codeListTable tr").click(function(){ 	
		var tr = $(this);
		var td = tr.children();
		var codeGrpId = td.eq(0).text();
		console.log(codeGrpId);
		location.href = '/admin/code/codeView.do?codeGrpId=' + codeGrpId;
	});
	
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
                         <!-- <h1 class="mt-4">ADMIN</h1> -->
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                	공통코드
                            </div>
                            <div class="card-body">
							<div class="dataTable-container">
							<form id="codeListForm" method="post" action="">
								<table id="codeListTable" class="dataTable-table" style="text-align: center;">
									<thead>
										<tr>
											<th style="width: 10%;">그룹아이디</th>
											<th style="width: 10%;">그룹명</th>
											<th style="width: 10%;">설명</th>
										</tr>
									</thead>
									<tbody>
									 <c:forEach items="${codeGrpList}" var="codeGrpList">
										<tr>
											<td>${codeGrpList.codeGrpId}</td>
											<td>${codeGrpList.codeGrpNm}</td>
											<td>${codeGrpList.codeGrpDesc}</td>
										</tr>
									  </c:forEach>
									</tbody>
								</table>
							</form>
								<button type="button" class="btn btn-primary"  onclick="javascript:fn_insert()" >등록</button>
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
