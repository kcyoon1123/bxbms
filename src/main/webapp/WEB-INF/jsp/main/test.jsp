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
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="/static/css/styles.css" rel="stylesheet" /> 
<link href="/static/css/bootstrap.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

// 수정버튼
function fn_memberModify() {
	
	$.ajax({
        url : '/myPage/memberModify.ajax'
        , method : "POST"
        , data : $("#memModiForm").serialize()
        , dataType : "json"
        , success : function (data) {
        	if (data.formState == "success") {
        	alert("정상적으로 수정되었습니다.");
        	location.href='/';
        	}
        }
    });
	
}
// 취소버튼
function backPage() {
	location.href='/';
	};
// 모달닫기
function closeModal() {
	 $('#passForm')[0].reset();
	 $("#passModal").hide();
	};
// 모달열기
function openModal() {
	$('#passForm')[0].reset();
	 $("#passModal").show();
	};
	

// 비밀번호 수정
function fn_memPassModify(){
	var userPass = $("#userPass").val();
	var userPass_chk = $("#userPass_chk").val();
	console.log($("#userPass").val());
	if(userPass != userPass_chk){
		alert("비밀번호가 일치하지 않습니다.");
		return;
	}else{
		// 수정되고 모달창 닫기
		$.ajax({
            url : '/myPage/memberPassModify.ajax'
            , method : "POST"
            , data : $("#passForm").serialize()
            , dataType : "json"
            , success : function (data) {
            	alert("비밀번호가 정상적으로 수정되었습니다.");
            	// 모달 리셋
            	 $('#passForm')[0].reset();
            	// 모달창 닫기
            	 $("#passModal").hide();
            }
        });
	}
}
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
                         <h1 class="mt-4">MYPAGE</h1>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                	MYPAGE
                            </div>
                            <div class="card-body">
					     	 <div class="dataTable-container">
                     	    	<form role="form" id="memModiForm" method="post" autocomplete="off">
		                     	   <div class="form-group">
									  <fieldset disabled>
									    <label class="form-label" for="userId">ID</label>
									    <input class="form-control" id="userId" name="userId" type="text" value="${session.userId }" disabled="disabled">
									  </fieldset>
									  <button type="button" onclick="javascript:openModal();"  data-toggle="modal" data-target="#passModal" class="btn btn-outline-primary">비밀번호변경</button>
									</div>
								    <div class="form-group">
									  <label class="col-form-label mt-4" for="userNm">이름</label>
									  <input type="text" class="form-control"  id="userNm" name="userNm" value="${member.userNm }">
									</div>
									<div class="form-group">
									  <label class="col-form-label mt-4" for="comNm">소속</label>
									  <input type="text" class="form-control" id="comNm" name="comNm" value="${member.comNm }" readonly="readonly">
									</div>
									<div class="form-group">
									  <label class="col-form-label mt-4" for="deptNm">부서</label>
									  <input type="text" class="form-control"  id="deptNm" name="deptNm" value="${member.deptNm }" readonly="readonly">
									</div>
								    <div class="form-group">
									  <label class="col-form-label mt-4" for="position">직급</label>
									  <input type="text" class="form-control" id="position" name="position" value="${member.position }">
									</div>
								    <div class="form-group">
									  <label class="col-form-label mt-4" for="emlAddr">이메일</label>
									  <input type="text" class="form-control" id="emlAddr" name="emlAddr" value="${member.emlAddr }">
									</div>
								    <div class="form-group">
									  <label class="col-form-label mt-4" for="addr">주소</label>
									  <input type="text" class="form-control"  id="addr" name="addr"value="${member.addr }">
									</div>
									<br>
								    <button type="button" onclick="javascript:fn_memberModify();" class="btn btn-secondary" >정보수정</button>
								    <button type="button" onclick="javascript:backPage();" class="btn btn-secondary" >메인으로</button>
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
		 <div class="modal" id="passModal">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">Modal title</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true"></span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <form role="form" id="passForm" method="post" autocomplete="off">
		        <%-- <input type="hidden" id="userId" name="userId" value="${member.userId }" />
		        <input type="hidden" id="memId" name="memId" value="${member.memId }"/> --%>
							<p>
								<label for="userPass">비밀번호</label> 
								<input type="password" id="userPass" name="userPass"  />
							</p>
							<p>
								<label for="userPass_chk">비밀번호확인</label> 
								<input type="password" id="userPass_chk" name="userPass_chk"/>
							</p>
				 </form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-primary">Save changes</button>
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
		      </div>
		    </div>
		  </div>
		</div>
    </body>
</html>
