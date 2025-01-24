<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정. -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
		
        <title>JSP</title>
        
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
	</head>
	<body>
		<div class="container-fluid">
            <c:set var = "pageTitle" value = "회원가입" />
            <%@ include file="../fragments/header.jspf" %>
        </div>
		<main>
            <div class="d-grid col-7 mx-auto mt-5">
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a class="nav-link active" href="#">일반</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">사업자</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">관리자</a>
                    </li>
                </ul>
                <div class="card card-body">
                    <form method="post" class="row justify-content-center">
                        <div class="m-2">
                            <input type="text" id="username" class="form-control" 
                                name="username" placeholder="사용자 아이디" required autofocus />
                        </div>
                        
                        <%-- username 중복체크 결과를 출력할 영역 --%>
                        <div id="checkUsernameResult"></div>
                        
                        <div class="m-2">
                            <input type="password" id="password" class="form-control" 
                                name="password" placeholder="비밀번호" required />
                        </div>
                        <div id="checkPasswordResult"></div>
                        
                        <div class="m-2">
                            <input type="email" id="email" class="form-control" 
                                name="email" placeholder="이메일 - example@email.com" required />
                        </div>
                        
                        <%-- 이메일 중복체크 결과를 출력할 영역 --%>
                        <div id="checkEmailResult"></div>
                        
                        <div class="mt-4 col-7">
                            <button type="submit" id="signUp" 
                                class="btn btn-primary form-control disabled">가입 요청</button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
		<!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
            crossorigin="anonymous"></script>
	</body>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정. -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
		
        <title>JSP</title>
        
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
	</head>
	<body>
		<div class="container-fluid">
            <c:set var = "pageTitle" value = "회원가입" />
            <%@ include file="../fragments/header.jspf" %>
        </div>
		<main>
            <div class="d-grid col-7 mx-auto mt-5">
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a class="nav-link active" href="#">일반</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">사업자</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">관리자</a>
                    </li>
                </ul>
                <div class="card card-body">
                    <form method="post" class="row justify-content-center">
                        <div class="m-2">
                            <input type="text" id="username" class="form-control" 
                                name="username" placeholder="사용자 아이디" required autofocus />
                        </div>
                        
                        <%-- username 중복체크 결과를 출력할 영역 --%>
                        <div id="checkUsernameResult"></div>
                        
                        <div class="m-2">
                            <input type="password" id="password" class="form-control" 
                                name="password" placeholder="비밀번호" required />
                        </div>
                        <div id="checkPasswordResult"></div>
                        
                        <div class="m-2">
                            <input type="email" id="email" class="form-control" 
                                name="email" placeholder="이메일 - example@email.com" required />
                        </div>
                        
                        <%-- 이메일 중복체크 결과를 출력할 영역 --%>
                        <div id="checkEmailResult"></div>
                        
                        <div class="mt-4 col-7">
                            <button type="submit" id="signUp" 
                                class="btn btn-primary form-control disabled">가입 요청</button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
		<!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
            crossorigin="anonymous"></script>
	</body>
>>>>>>> refs/heads/master
</html>