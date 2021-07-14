<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일간보고</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

$( function() {
	$("#datepicker").datepicker({
			dateFormat : "yy-mm-dd",
			maxDate : new Date,
			regional: "ko",
			beforeShowDay: function(date){
				var day = date.getDay();
				return [(day != 0 && day != 6)];
			}			
		});
	});
	$(function() {
		$("#datepicker").datepicker({
			maxDate : '0'
		});
	});
	$.datepicker.setDefaults({
		dateFormat : 'yyyymmdd',
		prevText : '이전 달',
		nextText : '다음 달',
		monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월',
				'10월', '11월', '12월' ],
		monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월',
				'9월', '10월', '11월', '12월' ],
		dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
		showMonthAfterYear : true,
		yearSuffix : '년'
	});
	
	
	// 검색 버튼
	function fn_search() {
		var repDate = $("#datepicker").val();
		var comNm = $("select[name=comNm]").val();
		var searchKeyword = $("#searchKeyword").val();
		var searchType = $("select[name=searchType]").val();
		
		console.log(comNm);
		location.href = '/dayrep/dayrepList.do?comNm=' + comNm
				+ '&repDate=' + repDate + '&searchType=' + searchType + '&searchKeyword=' + searchKeyword;

	};
	
	// 등록 버튼
	function fn_Insert(dayrepId, userId) {
		var dayrepCont = $('[name *= "'+userId+'"]').val();
		$.ajax({
			url : '/dayrep/insertDayrep.ajax',
			method : "POST",
			data : {
				dayrepCont : dayrepCont,
				dayrepId : dayrepId,
				userId : userId
			},
			dataType : "json",
			success : function(data) {
				if (data.formState == "successInsert") {
					alert("정상적으로 등록되었습니다.");
					location.reload();
				} else if (data.formState == "successUpate") {
					alert("정상적으로 수정되었습니다.");
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
		<h2>일간보고</h2>
		<div>
			  <input type="text" id="datepicker" value="${repDate}">
			  <select name="comNm" id="comNm">
				    <option <c:if test="${comNm == '' }"> selected</c:if>  value="" >--소속--</option>
				    <option <c:if test="${comNm == 'BXSYSTEM' }"> selected</c:if>  value="BXSYSTEM">BX시스템</option>
				    <option <c:if test="${comNm == 'kongam' }"> selected</c:if>  value="kongam">공감</option>
			  </select>
			  <select name="searchType" id="searchType">
				    <option <c:if test="${searchType == 'userNm' }"> selected</c:if> value="userNm">이름</option>
				    <option <c:if test="${searchType == 'content' }"> selected</c:if> value="content">내용</option>
			  </select>
			  <input type="text" id="searchKeyword" name="searchKeyword" value="${searchKeyword}">
			  <button type="button" onclick="javascript:fn_search()">검색</button>
		</div>
    <form id="dayrepForm" method="post" action="">
	<table class="table">
	 <thead>
	  <tr>
	   <th>이름</th>
	   <th>소속</th>
	   <th>일간보고</th>
	   <th></th>
	  </tr>
	 </thead>
	 <tbody>
	 <c:forEach items="${dayrepList}" var="dayrepList">
	 <tr>
		 	<td>
		 		 ${dayrepList.userNm}
		 	</td>
		 	<td>
		 		 ${dayrepList.comNm}
		 	</td>
		 	<td>
		 		<textarea name="dayrepCont_${dayrepList.userId}" id="dayrepCont_${dayrepList.userId}"  style="margin: 0px; width: 842px; height: 98px;" <c:if test="${dayrepList.userId ne session.userId  || dayFlag ne 'Y' }" >disabled</c:if>> ${dayrepList.dayrepCont}</textarea>
		 	</td>
		 	<td>
		 	<c:if test="${dayFlag == 'Y' && dayrepList.userId eq session.userId}">
		 		<button type="button" onclick="javascript:fn_Insert('${dayrepList.dayrepId}', '${dayrepList.userId}')" style="float:right;">등록</button>
		 	</c:if>
		 	</td>
	 	</tr>
	 </c:forEach>
	 </tbody>
	</table>
	</form>
		<%@include file="/WEB-INF/include/footer.jsp" %>
</body>
</html>