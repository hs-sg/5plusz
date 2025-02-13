<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
            <c:set var = "pageTitle" value = "FestGo" />
            <%@ include file="../fragments/header.jspf" %>
        </div>
        <main>
            <div id="signin" class="d-grid col-7 mx-auto mt-5">
                <div class="card card-body">
                    <form method="post" class="row justify-content-center" id="formSignIn">
                        <!-- 아이디 입력 -->
                        <div class="form-floating">
                            <input type="text" class="form-control mb-2" 
                                id="signinUsername" placeholder="아이디" autofocus required />
                            <label for="signinUsername">아이디</label>
                        </div>
                        <!-- 비밀번호 입력 -->
                        <div class="form-floating">
                            <input type="password" class="form-control mb-2" 
                                id="signinPassword" placeholder="비밀번호" required />
                            <label for="signinPassword">비밀번호</label>
                        </div>
                        <!-- 경고 문구 출력용 div -->
                        <c:if test="${ not empty param.result && param.result eq 'f' }">
                            <div class="text-danger mb-3" id="warningText">아이디 또는 비밀번호를 확인하세요.</div>
                        </c:if>
                        <!-- 로그인 버튼 -->
                        <div class="d-grid gap-2 mx-auto">
                            <button class="btn btn-primary btn-lg" id="btnSignin">로그인</button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
        
        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
            crossorigin="anonymous"></script>
        <c:url var="signInPageJS" value="/js/signInPage.js" />
        <script src="${ signInPageJS }"></script>
    </body>
</html>

