<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정. -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
		
        <title>Fest Go</title>
        
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
        <style>
            div#signup {
                max-width: 450px;
            }
            
            input.dangerBorder {
                border-color:red;
            }
        </style>
	</head>
	<body>
		<div class="container-fluid">
            <c:set var = "pageTitle" value = "회원가입" />
            <%@ include file="../fragments/header.jspf" %>
        </div>
		<main>
            <div id=signup class="d-grid col-7 mx-auto mt-5">
                <ul class="nav nav-underline d-flex justify-content-center">
                    <li class="nav-item">
                        <a class="nav-link link-dark active" href="#" id="linkGeneral">일반</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link link-dark" href="#" id="linkBusiness">사업자</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link link-dark" href="#" id="linkAdmin">관리자</a>
                    </li>
                </ul>
                <div class="card card-body">
                    <form method="post" class="row justify-content-center">
                        <div class="m-2">
                            <input type="text" id="username" class="form-control" 
                                name="meUsername" placeholder="아이디" required autofocus />
                        </div>

                        <div class="m-2">
                            <input type="password" id="password" class="form-control" 
                                name="mePassword" placeholder="비밀번호" required />
                        </div>
                        
                        <div class="m-2">
                            <input type="password" id="passwordCheck" class="form-control" 
                                name="passwordCheck" placeholder="비밀번호 확인" required />
                        </div>
                        
                        <div class="form-check form-switch form-check-reverse col-11">
                            <input class="form-check-input" type="checkbox" role="switch" id="showPasswordCheckbox"/>
                            <label class="form-check-label text-secondary" for="showPasswordCheckbox">
                                비밀번호 표시
                            </label>                        
                        </div>
                        
                        <div class="m-2">
                            <input type="email" id="email" class="form-control" 
                                name="meEmail" placeholder="이메일 주소" required />
                        </div>
                        
                        <div class="m-2 d-none" id="divSponsor">
                            <input type="text" id="sponsor" class="form-control"
                                name="meSponsor" placeholder="업체/기관명" />
                        </div>
                        
                        <!-- 로그인/비밀번호/이메일/업체/기관명 중복체크 결과를 출력할 영역 -->
                        <div id="inputCheckResult" class="text-danger">
                            <p id="checkUsernameResult"></p>
                            <p id="checkPasswordResult"></p>
                            <p id="checkPasswordCheckResult"></p>
                            <p id="checkEmailResult"></p>
                            <p id="checkSponsorResult"></p>
                        </div>
                        
                        <!-- 데이터 저장용 input(화면에 보이지 않음) -->
                        <input type="text" id="memberRole" class="d-none"
                            name="mrId" value="1"/>
                        
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
        
        <!-- Axios Http JS -->    
        <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
            
        <c:url var="signUpJS" value="/js/signUp.js"/>
        <script src="${ signUpJS }"></script>
	</body>
</html>