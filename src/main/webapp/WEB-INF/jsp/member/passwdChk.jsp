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
	// 다음단계
	function fn_passwdChk() {
	 	var userId = $("#userId").val();
		var userPass = $("#userPass").val();
		var data = {
				userId : userId,
				userPass : userPass
		}; 
		$.ajax({
            url : "/myPage/passwdChk.ajax"
            , method : "post"
            , data :data
            , dataType : "json"
            , success : function (data) {
                if(data.formState == "success"){
                	location.href= '/myPage/memberModify.do?memId=${session.memId}';
                }else{
                	alert("비밀번호가 일치하지 않습니다.");
                	$('#userPass').val('');
                	return ;
                }
            }
        });
		
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
                     	    	<form role="form" id="chkForm" method="post" autocomplete="off">    
		                     	   <div class="form-group">
									  <fieldset disabled="">
									    <label class="form-label" for="disabledInput">ID</label>
									    <input class="form-control" id="userId" name="userId" type="text" value="${session.userId }" disabled="disabled">
									  </fieldset>
									</div>
								    <div class="form-group">
								      <label for="exampleInputPassword1" class="form-label mt-4">Password</label>
								      <input type="password" class="form-control" name="userPass" id="userPass" placeholder="Password">
								    </div>
								    <br>
								    <button type="button" onclick="javascript:fn_passwdChk();" class="btn btn-secondary" >다음</button>
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
