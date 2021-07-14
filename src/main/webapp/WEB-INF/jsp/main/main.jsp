<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>BMS</title>
<link href="static/css/bootstrap.css" rel="stylesheet" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
<script type="text/javascript">
function fn_singUp(){
	location.href = '/member/memberSignup.do';
}
</script>
</head>
    <body class="bg-primary">
        <div id="layoutAuthentication">
            <div id="layoutAuthentication_content">
                <main>
                    <div class="container">
                    <div class="row justify-content-center">
                            <div class="col-lg-5">
                    <div class="card border-dark shadow-lg border-0 rounded-lg mt-5">
					  <div class="card-header"><img src="../static/assets/img/bxsystem.png" style="width:auto; height:50px; display: block; margin: auto;"></div>
					  <div class="card-body">
					  	<c:if test="${session == null}">
							<form role="form" method="post" action="/member/login.do" autocomplete="off">
	                           <div class="form-floating mb-3">
	                               <input class="form-control" id="userId" placeholder="ID" name="userId" />
	                               <label for="inputEmail">ID</label>
	                           </div>
	                           <div class="form-floating mb-3">
	                               <input class="form-control" id="userPass" type="password" placeholder="Password" name="userPass" />
	                               <label for="inputPassword">PASSWORD</label>
	                           </div>
	                       <div class="d-grid gap-2">
							  <button class="btn btn-lg btn-primary" type="submit">로그인</button>
						  </div>
	                       </form>
                              <hr>
	                       <div class="d-grid gap-2">
                                 <button type="button" class="btn btn-lg btn-outline-primary" onclick="javascript:fn_singUp()">회원가입</button>
						  </div>
                       </c:if>
                       <c:if test="${session != null}">
                       <div class="d-grid gap-2">
                       <button class="btn btn-lg btn-primary" onclick="location.href='/member/logout.do'">LOGOUT</button>
                       </div>
                       </c:if>
					  </div>
					</div>
					</div>
					</div>
                    </div>
                </main>
            </div>
           <!-- Footer-->
        <footer class="py-5 bg-dark">
            <div class="container"><p class="m-0 text-center text-white">Copyright &copy; Your Website 2021</p></div>
        </footer>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="static/js/scripts.js"></script>
    </body>
</html>
