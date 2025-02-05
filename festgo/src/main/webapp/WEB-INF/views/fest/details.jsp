<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${festival.feName} 상세 정보</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
          rel="stylesheet" 
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
          crossorigin="anonymous" />

    <!-- Custom CSS -->
    <style>
        /* 메인 배경 이미지 스타일 */
        .main-visual {
            background: url(${festival.feImageMain}) no-repeat center center;
            background-size: cover;
            background-attachment: fixed; /* 스크롤해도 고정 */
            height: 400px; /* 원하는 높이 */
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 2rem;
            font-weight: bold;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
            position: relative;
        }

        /* 투명 오버레이 효과 */
        .overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.4); /* 어두운 반투명 효과 */
        }

        /* 본문 컨텐츠 */
        .content {
            padding: 50px 0;
        }
    </style>
</head>
<body>

    <div class="container-fluid">
        <c:set var="pageTitle" value="축제 상세 정보" />
        <%@ include file="../fragments/header.jspf" %>
    </div>

    <!-- 메인 비주얼 -->
    <div class="main-visual">
        <div class="overlay"></div>
        <div>${festival.feName}</div>
    </div>

    <main class="container content">
        <h2>📖 축제 내용</h2>
        <p>${festival.feContents}</p>

        <h3>📅 축제 날짜</h3>
        <p>${festival.feStartDate} ~ ${festival.feEndDate}</p>

        <h3>📍 ${festival.feAddress}</h3>
        <p>${festival.feAddress}</p>
        <a href="https://map.kakao.com/link/to/${festival.feName},${festival.feLat},${festival.feLong}" target="_blank" class="btn btn-outline-primary">
            📍 길찾기
        </a>

        <h3 class="mt-3">💰 참가비</h3>
        <p>${festival.feFee}</p>
    </main>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
        crossorigin="anonymous"></script>

</body>
</html>
