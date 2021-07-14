<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주간보고</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

function fn_insertReply(){
    var weekrepId=  $("#weekrepId").val();
    alert(weekrepId);
    $.ajax({
        url : '/weekrep/insertReply.ajax'
        , method : "POST"
        , data : $("#replyInsert").serialize()
        , dataType : "json"
        , success : function (data) {
        	if (data.formState == "successInsert") {
        		alert('댓글 작성 성공');
        		location.reload();
        	}
        }
    });

};
// 수정
function fn_repModify(repId){
	var repCont = $('#repCont').val();
    $.ajax({
        url : '/weekrep/modifyReply.ajax'
        , method : "POST"
        , data : {repId : repId , repCont : repCont}
        , dataType : "json"
        , success : function (data) {
        	if (data.formState == "successUpate") {
        		alert('댓글 수정 성공');
        		location.reload();
        	}
        }
    });

};
// 삭제
function fn_repDelete(repId){
    $.ajax({
        url : '/weekrep/repDelete.ajax'
        , method : "POST"
        , data : {repId : repId}
        , dataType : "json"
        , success : function (data) {
        	if (data.formState == "successDelete") {
        		alert('댓글 삭제 성공');
        		location.reload();
        	}
        }
    });

};
</script>
</head>
<body>
<%@include file="/WEB-INF/include/header.jsp" %>
<%@include file="/WEB-INF/include/nav.jsp" %>
		<h2>주간보고 상세</h2>
		<div>
		<!--주간보고 상세 -->
		<span id="regId" style="font-weight:bold">${weekrep.regId }</span><br>
			<p>
			<label for="weekrepCont">금주업무</label> <br>
			<input type="text" id="weekrepCont" name="weekrepCont" value="${weekrep.weekrepCont }" disabled />
			</p>
			<p>
			<label for="nextCont">차주업무계획</label> <br>
			<input type="text" id="nextCont" name="nextCont" value="${weekrep.nextCont }" disabled />
			</p>
			<p>
			<label for="uniqueness">특이사항</label> <br>
			<input type="text" id="uniqueness" name="uniqueness" value="${weekrep.uniqueness }" disabled />
			</p>
		</div>
		<!--주간보고 댓글 목록 -->
		<h3>코멘트</h3>
		<c:forEach items="${replyList}" var="replyList">
			<div>
	    		<div>
		     		<span id="regId" style="font-weight:bold">${replyList.regId}</span><br>
		     		<c:if test="${replyList.regId eq session.userId}" >
		     			<input type="text" id="repCont" name="repCont" value="${replyList.repCont}" />
		     		</c:if>
		     		<c:if test="${replyList.regId ne session.userId}" >
					<p id="repCont" >${replyList.repCont}</p>
					</c:if>
		     		<br>
		     		<c:if test="${replyList.mdfcnDt eq null}" >
		     		<p id="regDt" >${replyList.regDt}</p>
		     		</c:if>
		     		<c:if test="${replyList.mdfcnDt ne null}" >
		     		<p id="regDt" >${replyList.mdfcnDt}</p>
		     		</c:if>
	     		</div>
	     		<c:if test="${replyList.regId eq session.userId}" >
						<button type="button" onclick="javascript:fn_repModify('${replyList.repId}')">등록</button>
						<button type="button" onclick="javascript:fn_repDelete('${replyList.repId}')">삭제</button>
				</c:if>
	     		<hr>
	    	</div>
		</c:forEach>
	<!--주간보고 댓글 등록 -->
	  <c:if test="${session.roleId eq 'ADMIN'}" >
		<div>
	       	<form role="form" id="replyInsert" name="replyInsert" method="post">
				<input type="hidden" id="weekrepId" name="weekrepId" value="${weekrep.weekrepId}"/>
				<input type="hidden" id="regId" name="regId" value="${session.userId}"/>
	        	<span id="regId" name="regId" style="font-weight:bold; float:left;" value="${session.userId}">${session.userId}</span><br>
	        	<textarea name=repCont cols="30" rows="5" style="float:left;"></textarea>
	       	</form>
       </div>
       <div>
          <button type="button" onclick="javascript:fn_insertReply()">작성</button>
        </div>
       </c:if>
		<%@include file="/WEB-INF/include/footer.jsp" %>
</body>
</html>