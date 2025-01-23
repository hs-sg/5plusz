<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>새 축제 작성</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
          rel="stylesheet" 
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
          crossorigin="anonymous" />
</head>
<body>
        <div class="container-fluid">
            <c:set var="pageTitle" value="포스트 작성" />
            <%@ include file="../fragments/header.jspf" %>
        </div>
        
    <div class="container">
        <h2 class="mt-5">새 축제 작성</h2>
        <form action="/create" method="post">
            <!-- 축제 이름 -->
            <div class="mb-3">
                <label for="feName" class="form-label">축제 이름</label>
                <input type="text" class="form-control" id="feName" name="feName" placeholder="축제 이름" required>
            </div>

            <!-- 시작 날짜 -->
            <div class="mb-3">
                <label for="feStartDate" class="form-label">축제 시작 날짜</label>
                <input type="datetime-local" class="form-control" id="feStartDate" name="feStartDate" required>
            </div>

            <!-- 종료 날짜 -->
            <div class="mb-3">
                <label for="feEndDate" class="form-label">축제 종료 날짜</label>
                <input type="datetime-local" class="form-control" id="feEndDate" name="feEndDate" required>
            </div>

            <!-- 지역 ID -->
            <div class="mb-3">
                <label for="lcId" class="form-label">지역 ID</label>
                <input type="text" class="form-control" id="lcId" name="lcId" placeholder="지역 ID" required>
            </div>

            <!-- 주소 -->
            <div class="mb-3">
                <label for="feAddress" class="form-label">주소</label>
                <input type="text" class="form-control" id="feAddress" name="feAddress" placeholder="주소" required>
            </div>

            <!-- 전화번호 -->
            <div class="mb-3">
                <label for="fePhone" class="form-label">전화번호</label>
                <input type="text" class="form-control" id="fePhone" name="fePhone" placeholder="전화번호" required>
            </div>

            <!-- 후원자 -->
            <div class="mb-3">
                <label for="meSponsor" class="form-label">작성자</label>
                <input type="text" class="form-control" id="meSponsor" name="meSponsor" placeholder="후원자" required>
            </div>

            <!-- 참가비 -->
            <div class="mb-3">
                <label for="feFee" class="form-label">축제비용</label>
                <input type="text" class="form-control" id="feFee" name="feFee" placeholder="참가비" required>
            </div>

            <!-- 테마 ID -->
            <div class="mb-3">
                <label for="theId" class="form-label">테마</label>
                <input type="number" class="form-control" id="theId" name="theId" placeholder="테마 ID" required>
            </div>

            <!-- 내용 -->
            <div class="mb-3">
                <label for="feContents" class="form-label">내용</label>
                <textarea class="form-control" id="feContents" name="feContents" rows="5" placeholder="내용" required></textarea>
            </div>

            <!-- 홈페이지 -->
            <div class="mb-3">
                <label for="feHomepage" class="form-label">홈페이지 링크</label>
                <input type="url" class="form-control" id="feHomepage" name="feHomepage" placeholder="홈페이지 URL">
            </div>

            <!-- 대표 이미지 -->
            <div class="mb-3">
                <label for="feImageMain" class="form-label">축제 대표 이미지</label>
                <input type="text" class="form-control" id="feImageMain" name="feImageMain" placeholder="대표 이미지 파일명">
            </div>

            <!-- 포스터 -->
            <div class="mb-3">
                <label for="fePoster" class="form-label">축제 포스터</label>
                <input type="text" class="form-control" id="fePoster" name="fePoster" placeholder="포스터 파일명">
            </div>

            <!-- 이미지 파일들 -->
            <div class="mb-3">
                <label for="festivalImages" class="form-label">축제 추가 이미지 (쉼표로 구분)</label>
                <textarea class="form-control" id="festivalImages" name="festivalImages" rows="2" placeholder="이미지1.jpg, 이미지2.jpg"></textarea>
            </div>

            <!-- 제출 버튼 -->
            <div class="mb-3">
                <button type="submit" class="btn btn-primary">축제 등록</button>
            </div>
        </form>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
