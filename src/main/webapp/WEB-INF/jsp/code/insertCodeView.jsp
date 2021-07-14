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

	// 등록 버튼
	function fn_insert() {
		var codeGrpNm = $('#codeGrpNm').val();
		var codeGrpDesc = $('#codeGrpDesc').val();
		console.log(codeGrpNm);
	 	$.ajax({
			url : '/admin/code/insertCodeGrp.ajax',
			method : "POST",
			data :{
				codeGrpNm : codeGrpNm,
				codeGrpDesc : codeGrpDesc
			},
			dataType : "json",
			success : function(data) {
				if (data.formState == "success") {
					alert("정상적으로 등록되었습니다.");
					location.href = '/admin/code/codeList.do';
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
                        <div class="card">
						  <div class="card-body">
						   <h4 class="card-title">공통코드그룹 등록</h4>
							<form id="codeGrpForm" method="post" action="" accept-charset="UTF-8">
								<div class="form-group">
								  <fieldset disabled>
								    <label class="form-label" for="disabledInput">그룹아이디</label>
								    <input class="form-control" id="disabledInput" type="text" placeholder="그룹아이디 자동 부여" disabled>
								  </fieldset>
								</div>
								<div class="form-group">
									<label class="col-form-label mt-4" for="codeGrpNm">그룹명</label> <input type="text" class="form-control" placeholder="Default input" id="codeGrpNm" name="codeGrpNm">
								</div>
								<div class="form-group">
							      <label for="codeGrpDesc" class="form-label mt-4">설명</label>
							      <textarea class="form-control" id="codeGrpDesc" name="codeGrpDesc" rows="3"></textarea>
							    </div>
								</form>
								<br>
								<button type="button" class="btn btn-primary"  onclick="javascript:fn_insert();" >등록</button>
                        </div>
                        </div>
                </main>
            </div>
        	
             </div>
        </div>
       <%@include file="/WEB-INF/include/include-footer.jsp" %>
    </body>
</html>
