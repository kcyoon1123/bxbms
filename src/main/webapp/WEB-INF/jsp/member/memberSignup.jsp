<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript">


// 아이디 중복체크
function fn_idChk() {
	var userId = $('input[name=userId]').val();
	
	if(userId == ''){
		alert("아이디를 입력하세요");
		return false;
	}
	
		$.ajax({
	        url : '/member/idCheck.ajax'
	        , method : "POST"
	        , data : {userId : $("#userId").val()}
	        , dataType : "json"
	        , success : function (data) {
	        	console.log(data.idChk);
	        	if (data.idChk == "pass") {
	        		
	        	alert("사용가능한 아이디입니다.");
	        	$("#signUp").removeAttr("disabled");
	        	
	        	}else if(data.idChk == "fail"){
	        		alert("중복된 아이디가 존재합니다.");
	        		$("#signUp").attr("disabled", "disabled");
	        		return false;
	        	}
	        	
	        }
	    });
		
};
// 회원가입
function fn_signUp() {
	var userId = $("#userId").val();
	var userPass = $("#userPass").val();
	var userNm = $("#userNm").val();
	var deptNm = $("#deptNm").val();
	
	if(userId == null || userId == ''){
		alert("아이디를 입력하세요");
		return false;
	}else if(userPass == null || userPass == ''){
		alert("비밀번호를 입력하세요");
		return false;
	}else if(userNm == null || userNm == ''){
		alert("사용자명을 입력하세요");
		return false;
	}else if(deptNm == null || deptNm == ''){
		alert("부서명을 입력하세요");
		return false;
	}
	
		$.ajax({
	        url : '/member/memberSignup.ajax'
	        , method : "POST"
	        , data : $("#signupForm").serialize()
	        , dataType : "json"
	        , success : function (data) {
	        	if (data.formState == "success") {
	        		alert("가입이 완료됐습니다");
	        		location.href='/';
	        		
	        	}
	        }
	    });
};
/* 	$('#userId').click(function() {
		$('#userId').keyup();
	});
	 */
	$('#userId').keydown(function(){
		$("#signUp").attr("disabled", "disabled");
	});
	

	
</script>
</head>
<body>
<%@include file="/WEB-INF/include/header.jsp" %>
<%@include file="/WEB-INF/include/nav.jsp" %>
		
		<form role="form" id="signupForm" method="post" autocomplete="off">
			<p>
				<label for="userId">아이디</label>
				<input type="text" id="userId" name="userId"/>
				<button type="button" onclick="javascript:fn_idChk();" >중복확인</button>
			</p>
			<p>
				<label for="userPass">비밀번호</label>
				<input type="password" id="userPass" name="userPass"/>
			</p>
			<p>
				<label for="userNm">이름</label>
				<input type="text" id="userNm" name="userNm"/>
			</p>
			<p>
				<label for="comNm">회사명</label>
<!-- 				<input type="text" id="comNm" name="comNm"/> -->
				<select name="comNm" >
				    <option value="BXSYSTEM" selected="selected">BX시스템</option>
				    <option value="kongam">공감</option>
				  </select>
			</p>
			<p>
				<label for="deptNm">부서명</label>
				<input type="text" id="deptNm" name="deptNm"/>
			</p>
			<p>
				<label for="position">직급</label>
				<input type="text" id="position" name="position"/>
			</p>
			<p>
				<label for="phoneNo">전화번호</label>
				<input type="text" id="phoneNo" name="phoneNo"/>
			</p>
			<p>
				<label for="emlAddr">이메일</label>
				<input type="text" id="emlAddr" name="emlAddr"/>
			</p>
			<p><button type="button" onclick="javascript:fn_signUp();" id ="signUp" disabled="disabled">회원가입</button></p>
		</form>
		
		<%@include file="/WEB-INF/include/footer.jsp" %>
</body>
</html>
