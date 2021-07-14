<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>월간보고</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    setDateBox();
    $("#fileupload").on("change", addFiles);
});    
var filesTempArr = [];
// 파일 추가
function addFiles(e) {
    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);
    var filesArrLen = filesArr.length;
    var filesTempArrLen = filesTempArr.length;
    for( var i=0; i<filesArrLen; i++ ) {
        filesTempArr.push(filesArr[i]);
        $("#fileList").append("<div>" + filesArr[i].name + "<button type='button' class='button-delete-file' onclick=\"deleteFile(event, " + (filesTempArrLen+i)+ ");\" >X</button></div>");
    }
    $(this).val('');
}
// 파일 삭제
function deleteFile (eventParam, orderParam) {
    filesTempArr.splice(orderParam, 1);
    var innerHtmlTemp = "";
    var filesTempArrLen = filesTempArr.length;
    for(var i=0; i<filesTempArrLen; i++) {
        innerHtmlTemp += "<div>" + filesTempArr[i].name +"<button type='button' class='button-delete-file' onclick=\"deleteFile(event, " + i + ");\" >X</button></div>";
    }
    $("#fileList").html(innerHtmlTemp);
}

//댓글 탭 이벤트(weekrepId가 있으면 댓글 버튼 보이게 ,클릭시 댓글 내용div에 뿌리기)
function fn_fileLidtDisplay(monthrepId) {
	console.log(monthrepId);
	var con = document.getElementById("downloadList");
	var conbtn = document.getElementById("filebtn");
	 if(con.style.display=='none'){
		
		// 첨부파일 목록
		$.ajax({
			url : '/monthrep/monthFileList.ajax',
			method : "POST",
			data : {monthrepId : monthrepId},
			dataType : "json",
			success : function(data) {
				var results = data.fileList;
				var str = '';
				var atchFileId = '';
				 $.each(results , function(i){
					 console.log( results[i].atchFileOrgnNm );
					 console.log( results[i].atchFileId );
					 atchFileId = results[i].atchFileId;
		                str += '<a href="/monthrep/downloadMonthrepFile.do?atchFileId='+ atchFileId + '" name="downLoadFile">'+results[i].atchFileOrgnNm+'</a><br>' ;
		           });
				 console.log(str);
				 $("#downloadList").append(str);
			}
		}); 
		con.style.display = 'block';
		conbtn.textContent  = '첨부파일▲';
	}else{
		$("#downloadList").empty();
		con.style.display = 'none';
		conbtn.textContent  = '첨부파일▼';
	} 
	
};

// select box 연도 , 월 표시
function setDateBox(){
    var dt = new Date();
    var year = "";
    var com_year = dt.getFullYear();
    var com_month = dt.getMonth()+1;
    // 발행 뿌려주기
    $("#year").append("<option value=''>년도</option>");
    // 올해 기준으로 -1년부터 +5년을 보여준다.
    for(var y = (com_year-1); y <= (com_year); y++){
    	if(y == com_year){
    		$("#year").append("<option value='"+ y +"' selected='selected'>"+ y + " 년" +"</option>");
    	}else{
        	$("#year").append("<option value='"+ y +"'>"+ y + " 년" +"</option>");
    	}
    	
    }
    // 월 뿌려주기(1월부터 12월)
    var month;
    $("#month").append("<option value=''>월</option>");
    for(var i = 1; i <= 12; i++){
    	if(i < 10){
    		month = '0'+ i;
    	}
    	
    	if(i == '${month}'){
    		$("#month").append("<option value='"+ month +"' selected='selected'>"+ i + " 월" +"</option>");
    	}else{
    		$("#month").append("<option value='"+ month +"'>"+ i + " 월" +"</option>");
    	}
        
    }
}



	// 검색 버튼
	function fn_search() {
		 var year = $("select[name=year]").val();
		 var month = $("select[name=month]").val();
		 var repDate = year + '-' + month;
		var comNm = $("select[name=comNm]").val();
		var searchKeyword = $("#searchKeyword").val();
		var searchType = $("select[name=searchType]").val();
		location.href = '/monthrep/monthrepList.do?comNm=' + comNm
				+ '&repDate=' + repDate + '&searchType=' + searchType + '&searchKeyword=' + searchKeyword;
		$("#month").append("<option value='"+ i +"'>"+ i + " 월" +"</option>");
	};
	
	// 등록 버튼
	function fn_InsertMonthrep(userId, monthrepId) {
		var form = $('#monthrepForm')[0];
		var formData = new FormData(form);
		// var goalCont = $("#goalCont").val();
		console.log(userId);
		var goalCont = $('[name = "goalCont_'+userId+'"]').val();
		var premonCont = $('[name = "premonCont_'+userId+'"]').val();
		var monthCont = $('[name = "monthCont_'+userId+'"]').val();
		var uniqueCont = $('[name = "uniqueCont_'+userId+'"]').val();
		
		for(var i=0, filesTempArrLen = filesTempArr.length; i<filesTempArrLen; i++) {
			   formData.append("file", filesTempArr[i]);
			}
		
		formData.append("userId", userId);
		formData.append("monthrepId", monthrepId);
		formData.append("goalCont", goalCont);
		formData.append("premonCont", premonCont);
		formData.append("monthCont", monthCont);
		formData.append("uniqueCont", uniqueCont);
		
		$.ajax({
			url : '/monthrep/insertMonthrep.ajax',
			method : "POST",
			data : formData ,
			dataType : "json",
			contentType : false,
			processData : false,
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
		<h3>${year} 년 ${month}월 월간보고</h3>
		<div>
			  <select id="year" name="year" ></select>    
              <select id="month" name="month"></select>  
			  <select name="comNm" id="comNm">
				    <option <c:if test="${comNm == '' }"> selected</c:if>  value="" >--소속--</option>
				    <option <c:if test="${comNm == 'BXSYSTEM' }"> selected</c:if> value="BXSYSTEM">BX시스템</option>
				    <option <c:if test="${comNm == 'kongam' }"> selected</c:if> value="kongam">공감</option>
			  </select>
			  <select name="searchType" id="searchType">
				    <option selected <c:if test="${searchType == 'userNm' }"> selected</c:if> value="userNm">이름</option>
				    <option <c:if test="${searchType == 'content' }"> selected</c:if> value="content">내용</option>
			  </select>
			  <input type="text" id="searchKeyword" name="searchKeyword" value="${searchKeyword}">
			  <button type="button" onclick="javascript:fn_search()">검색</button>
		</div>
    <form id="monthrepForm" method="post" enctype="multipart/form-data" action="" accept-charset="UTF-8">
	<table class="table">
	 <thead>
	  <tr>
	   <th>이름</th>
	   <th>소속</th>
	   <th>월간보고</th>
	   <th></th>
	  </tr>
	 </thead>
	 <tbody>
	  <c:forEach items="${monthrepList}" var="monthrepList">
	 <tr>
		 	<td>${monthrepList.userNm}</td>
		 	<td>${monthrepList.comNm}</td>
		 	<td>
		 	<ul style="list-style:none;">
					<li>
						<label for="goalCont">월간 목표</label><br>
						<textarea name="goalCont_${monthrepList.userId}" id="goalCont_${monthrepList.userId}" style="margin: 0px; width: 842px; height: 98px;" <c:if test="${monthrepList.userId ne session.userId  || dayFlag eq 'N' }" >disabled</c:if> >${monthrepList.goalCont}</textarea>
					</li>
					<li>
						<label for="premonCont">전월업무현황</label><br>
						<textarea name="premonCont_${monthrepList.userId}" id="premonCont_${monthrepList.userId}" style="margin: 0px; width: 842px; height: 98px;" <c:if test="${monthrepList.userId ne session.userId  || dayFlag eq 'N' }" >disabled</c:if> >${monthrepList.premonCont}</textarea>
					</li>
					<li>
						<label for="monthCont">금월예정업무</label><br>
						<textarea name="monthCont_${monthrepList.userId}" id="monthCont_${monthrepList.userId}" style="margin: 0px; width: 842px; height: 98px;" <c:if test="${monthrepList.userId ne session.userId  || dayFlag eq 'N' }" >disabled</c:if> >${monthrepList.monthCont}</textarea>
					</li>
					<li>
						<label for="uniqueCont">특이사항</label><br>
						<textarea name="uniqueCont_${monthrepList.userId}" id="uniqueCont_${monthrepList.userId}" style="margin: 0px; width: 842px; height: 98px;" <c:if test="${monthrepList.userId ne session.userId  || dayFlag eq 'N' }" >disabled</c:if> >${monthrepList.uniqueCont}</textarea>
					</li>
					<li>
					<!-- 파일 리스트 -->
					<span class="more" id="filebtn" onclick="fn_fileLidtDisplay('${monthrepList.monthrepId}')">첨부파일▼</span>
						<div id="downloadList" style="display:none;"></div>
					</li>
					<li>
						<c:if test="${monthrepList.userId eq session.userId  && dayFlag eq 'Y' }" > 
							<input name="files" id="fileupload" type="file" multiple />
							<div id="fileList"></div>
					 	</c:if>
					</li>
				</ul>
		 	</td>
		 	<td>
		 	<c:if test="${monthrepList.userId eq session.userId  && dayFlag eq 'Y' }" > 
		 		<button type="button" onclick="javascript:fn_InsertMonthrep('${monthrepList.userId}', '${monthrepList.monthrepId}')" style="float:right;">등록</button>
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