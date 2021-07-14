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
<title>BMS</title>
<!-- Core theme CSS (includes Bootstrap)-->
<link href="../static/css/styles.css" rel="stylesheet" /> 
<link href="../static/css/bootstrap.css" rel="stylesheet" />
<!-- js cdn  -->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- 엑셀업로드 CDN -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.14.3/xlsx.full.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/1.3.8/FileSaver.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
    setDateBox();
}); 

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
    // 주찻수 뿌려주기(1~5)
    var weekNum;
    // $("#weekNum").append("<option value=''>주차</option>");
    for(var i = 1; i <= 5; i++){
    	if(i == '${weekNum}'){
    		$("#weekNum").append("<option value='"+ i +"' selected='selected'>"+ i + " 주차" +"</option>");
    	}else{
    		$("#weekNum").append("<option value='"+ i +"'>"+ i + " 주차" +"</option>");
    	}
    } 
}


	// 검색 버튼
	function fn_search() {
		var comNm = $("select[name=comNm]").val();
		var searchKeyword = $("#searchKeyword").val();
		var searchType = $("select[name=searchType]").val();
		var month = $("select[name=month]").val();
		var year = $("select[name=year]").val();
		var weekNum = $("select[name=weekNum]").val();
		// repdate => 2021071 >> 2021년 7월 첫째주
		var repdate = year + month + weekNum;
		if(repdate > "${today}"){
			alert("차주 보고는 조회가 불가능합니다.");
			return;
		}
		console.log(repdate);
		location.href = '/weekrep/weekrepList.do?comNm=' + comNm
				+ '&repdate=' + repdate + '&searchType=' + searchType + '&searchKeyword=' + searchKeyword;

	};
	// 수정버튼
	function fn_modify(userId, weekrepId) {
		
		var uniqueness = $("[name=uniqueness] :enabled").val();
		var form = $('#weekrepForm')[0];
		var formData = new FormData(form);
		var month = $("select[name=month]").val();
		var year = $("select[name=year]").val();
		var weekNum = $("select[name=weekNum]").val();
		// repdate => 2021071 >> 2021년 7월 첫째주
		var repdate = year + month + weekNum;
		
		
		formData.append("file", $("#uploadFile")[0].files[0]);
		formData.append("userId", userId);
		formData.append("repdate", repdate);
		formData.append("weekrepId", weekrepId);
		
		$.ajax({
			url : '/weekrep/insertWeekrep.ajax',
			method : "POST",
			data : formData,
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
	

	//댓글 탭 이벤트(weekrepId가 있으면 댓글 버튼 보이게 ,클릭시 댓글 내용div에 뿌리기)
	function fn_doDisplay(weekrepId) {
		console.log(weekrepId);
		var con = document.getElementById("replyList");
		var conbtn = document.getElementById("reply");
		if(con.style.display=='none'){
			 $.ajax({
				url : '/weekrep/replyList.ajax',
				method : "POST",
				data : {weekrepId : weekrepId},
				dataType : "json",
				success : function(data) {
					var results = data.replyList;
					var str = '';
					 $.each(results , function(i){
			                str += '<p style="font-weight:bold">'+ results[i].regId + '</p>' ;
			                str += '<p>'+ results[i].repCont + '</p>' ;
			                str += '<p>'+ results[i].regDt + '</p><hr>' ;
			           });
					 $("#replyList").append(str);
				}
			}); 
			con.style.display = 'block';
			conbtn.textContent  = '댓글▲';
		}else{
			$("#replyList").empty();
			con.style.display = 'none';
			conbtn.textContent  = '댓글▼';
		}
		
	};
	
	// 파일 삭제
	function fn_deleteFile(atchFileId) {
		$.ajax({
			url : '/weekrep/fileDelete.ajax',
			method : "POST",
			data : {atchFileId : atchFileId},
			dataType : "json",
			success : function(data) {
				if (data.formState == "successDelete") {
					alert("정상적으로 삭제되었습니다.");
					location.reload();
				}
			}
		});
		
	};
	//상세
	function fn_openweekrepDetail(weekrepId) {
		location.href = '/weekrep/weekrepDetail.do?weekrepId='+ weekrepId;
	};
	
	
  //공통
 // 참고 출처 : https://redstapler.co/sheetjs-tutorial-create-xlsx/
 function s2ab(s) { 
     var buf = new ArrayBuffer(s.length); //convert s to arrayBuffer
     var view = new Uint8Array(buf);  //create uint8array as viewer
     for (var i=0; i<s.length; i++) view[i] = s.charCodeAt(i) & 0xFF; //convert to octet
     return buf;    
 }
 function exportExcel(){ 
     // step 1. workbook 생성
     var wb = XLSX.utils.book_new();

     // step 2. 시트 만들기 
     var newWorksheet = excelHandler.getWorksheet();
     
     // step 3. workbook에 새로만든 워크시트에 이름을 주고 붙인다.  
     XLSX.utils.book_append_sheet(wb, newWorksheet, excelHandler.getSheetName());

     // step 4. 엑셀 파일 만들기 
     var wbout = XLSX.write(wb, {bookType:'xlsx',  type: 'binary'});

     // step 5. 엑셀 파일 내보내기 
     saveAs(new Blob([s2ab(wbout)],{type:"application/octet-stream"}), excelHandler.getExcelFileName());
 }
 $(document).ready(function() { 
     $("#excelFileExport").click(function(){
         exportExcel();
     });
 });
 var excelHandler = {
         getExcelFileName : function(){
             return '주간보고.xlsx';
         },
         getSheetName : function(){
             return '주간보고';
         },
         getExcelData : function(){
              return ${jArray};
         },
         getWorksheet : function(){
             return XLSX.utils.json_to_sheet(this.getExcelData());
         }
 }
	// 포커스 이동
 function fn_focusblurtest(memId) { 
	  console.log(memId);
	  $('#weekrepForm').find('input[name=hidden_'+ memId +']').focus();
	 //$(userId).focusin();
	 // document.getElementById('nickname').focus();
 }

</script>
    </head>
    <body>
        <%@include file="/WEB-INF/include/include-header.jsp" %>
        <!-- Page content-->
        <div class="container" style="width:auto;">
        	<div class="row justify-content-center">
        		<div id="layoutSidenav_content" style="margin-bottom:5rem;">
                <main>
<!--                     <div class="container-fluid px-4"> -->
                         <h1 class="mt-4">주간보고</h1>${year}년 ${month}월  ${weekNum}주차
<!--                         <div class="card mb-4"> -->
<!--                             <div class="card-header"> -->
                                <i class="fas fa-table me-1"></i>
<!--                             </div> -->
<!--                             <div class="card-body"> -->
                          <div class="d-flex" style="margin-bottom: 0.5rem;">
								<select id="year" name="year" class="dataTable-selector"></select> 
								<select id="month" name="month" class="dataTable-selector"></select> 
								<select id="weekNum" name="weekNum" class="dataTable-selector"></select> 
								<select name="comNm" id="comNm" class="dataTable-selector">
									<option <c:if test="${comNm == '' }"> selected</c:if> value="" >--소속--</option>
								    <option <c:if test="${comNm == 'BXSYSTEM' }"> selected</c:if> value="BXSYSTEM">BX시스템</option>
								    <option <c:if test="${comNm == 'kongam' }"> selected</c:if> value="kongam">공감</option>
								</select> 
								<select class="dataTable-selector" name="searchType" id="searchType">
								 	<option <c:if test="${searchType == 'all' }"> selected</c:if> value="all" >--전체--</option>
								    <option <c:if test="${searchType == 'userNm' }"> selected</c:if> value="userNm">이름</option>
								    <option <c:if test="${searchType == 'content' }"> selected</c:if> value="content">내용</option>
								</select> 
							<input class="form-control me-sm-2" type="text" id="searchKeyword" name="searchKeyword" value="${searchKeyword}" placeholder="Search" style="width: 200px;">
					        <button class="btn btn-secondary my-2 my-sm-0" type="submit" onclick="javascript:fn_search()">Search</button>
					           <div style="float: right; margin-left: 410px;">
                                	 <button type="button" class="btn btn-outline-dark" id="excelFileExport"><img src="../static/assets/img/excelpng.png" style="width:20px; height:20px;"></button>
                                </div>
					      </div>
					      <div class="card border-light mb-3" style="width:250px; max-width: 20rem; float:left; position: fixed;">
							  <div class="card-header">회원목록</div>
							  <div class="card-body">
							    <p class="card-text">
							    <c:forEach items="${weekrepList}" var="weekrepList">
							<button type="button" class="btn btn-link" onclick="javascript:fn_focusblurtest(${weekrepList.memId})" >${weekrepList.userNm}</button>
							    </c:forEach>
							    </p>
							  </div>
							</div>
					      <div class="dataTable-container" style="width:1030px; float:right;">
					       <form id="weekrepForm" method="post" enctype="multipart/form-data" action="" accept-charset="UTF-8">
					      <table id="weekrepTable" class="dataTable-table">
                                    <thead>
                                        <tr>
                                            <th style="width:10%;">이름</th>
											<th style="width:10%;">소속</th>
											<th>주간보고</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                     <c:if test="${empty weekrepList }">
									 	<tr>
										 	<td></td>
										 	<td></td>
										 	<td></td>
									 	</tr>
									 </c:if>
									 <c:if test="${!empty weekrepList }">
									  	<c:forEach items="${weekrepList}" var="weekrepList">
										 <tr>
											<td>${weekrepList.userNm}<input type="text" id="hidden_${weekrepList.memId}" name="hidden_${weekrepList.memId}" style="opacity: 0;">
											</td>
											<td>${weekrepList.comNm}</td>
											<td>
												<ul style="list-style:none;">
													<li>
														<label for="exampleTextarea" class="form-label mt-4">금주업무</label><br>
														<textarea name="weekrepCont" id="weekrepCont" class="form-control" rows="3" <c:if test="${weekrepList.userId ne session.userId  || dayFlag eq 'N' }" >disabled</c:if> >${weekrepList.weekrepCont}</textarea>
													</li>
													<li>
														<label for="nextCont">차주업무계획</label><br>
														<textarea name="nextCont" id="nextCont" class="form-control" rows="3"<c:if test="${weekrepList.userId ne session.userId  || dayFlag eq 'N' }" >disabled</c:if> >${weekrepList.nextCont}</textarea>
													</li>
													<li>
														<label for="uniqueness">특이사항</label><br>
													    <textarea name="uniqueness" id="uniqueness" class="form-control" rows="3" <c:if test="${weekrepList.userId ne session.userId  || dayFlag eq 'N' }" >disabled</c:if> >${weekrepList.uniqueness}</textarea>
													</li>
													<li>
													<br>
														<div>
															<c:if test="${weekrepList.userId eq session.userId && dayFlag == 'Y' && weekrepList.weekrepId ne null}" >
												     	 		<input type='file' class="form-control" name='uploadFile' id="uploadFile" style="width: 400px;"/>
												       		</c:if>
															<c:if test="${weekrepList.userId eq session.userId && dayFlag == 'Y' }" >
																<button type="button" class="btn btn-primary" onclick="javascript:fn_modify('${weekrepList.userId}', '${weekrepList.weekrepId}')" style="float:right; margin-left: 10px;">등록</button>
												       		</c:if>
															<c:if test="${weekrepList.weekrepId ne null}" >
															 	<button type="button" class="btn btn-secondary" onclick="javascript:fn_openweekrepDetail('${weekrepList.weekrepId}')" style="float:right;">상세 </button>
															</c:if>
														</div>
											       		<br>
												   		<c:if test="${null ne weekrepList.atchFileId}">
										     				<p class="mb-0"><a href="/weekrep/downloadWeekrepFile.do?atchFileId=${weekrepList.atchFileId}" name="downLoadFile">${weekrepList.atchFileOrgnNm }</a></p> (${weekrepList.atchFileSize }kb)
													    	<c:if test="${weekrepList.userId eq session.userId && weekrepList.weekrepId ne null && dayFlag == 'Y' }" >
													     		<button type="button" class="btn btn-outline-dark" onclick="javascript:fn_deleteFile('${weekrepList.atchFileId}')">X</button>
													     	</c:if>
												   		</c:if>
													</li>
													<c:if test="${weekrepList.weekrepId ne null}" >
													<li>
													<!-- 댓글 리스트 -->
													<span class="more" id="reply" onclick="javascript:fn_doDisplay('${weekrepList.weekrepId}')">댓글▼</span>
														<div class="replyList" id="replyList" style="display:none;">
														</div>
													</li>
													
													</c:if>
												</ul>
											</td>
										 </tr>
										</c:forEach>
									  </c:if>
                                    </tbody>
                                </table>
                                </form>
                            </div>
<!--                             </div> -->
<!--                         </div> -->
<!--                     </div> -->
                </main>
            </div>
        	
             </div>
        </div>
        <%@include file="/WEB-INF/include/include-footer.jsp" %>
    </body>
</html>
