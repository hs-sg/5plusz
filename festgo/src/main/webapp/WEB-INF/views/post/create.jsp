<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>   
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="user-role" content="${mrId}" /> <!-- 사용자 역할 -->
        <title>Fest Go</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
              rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
              crossorigin="anonymous">
    <style>
    /* Font Faces */
        @font-face {        
            font-family: 'sansMedium';      
            src: url('../font/GmarketSansTTFMedium.ttf') format('truetype');        
        }       
        @font-face {        
            font-family: 'sansLight';       
            src: url('../font/GmarketSansTTFLight.ttf') format('truetype');     
        }       
        
        div {
            font-family: 'sansMedium';
        }
        .btn {
            height: 36px;  /* 버튼 높이 */
            line-height: 36px;  /* 버튼 높이와 동일한 line-height 설정 */
            text-align: center;
            padding: 0 16px;
        }
    </style>
    </head>
    <body>
        <div class="container-fluid">
            <c:set var="pageTitle" value="새 글 작성"/>
            <%@ include file="../fragments/header.jspf" %>
        </div>
        <div class="container mt-4">
            <form action="${pageContext.request.contextPath}/post/create" method="post" enctype="multipart/form-data">
                <!-- 글 유형 -->
                <div class="mb-3">
                    <label for="postType" class="form-label">글 유형</label>
                    <select id="postType" name="pcId" class="form-select" required>
                        <!-- JavaScript에서 동적으로 옵션 추가 -->
                    </select>
                </div>
                <div class="mb-3">
                    <label for="poTitle" class="form-label">제목</label>
                    <input type="text" id="poTitle" name="poTitle" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="poContent" class="form-label">내용</label>
                    <textarea id="poContent" name="poContent" class="form-control" rows="6" required></textarea>
                </div>
                <div class="mb-3">
                    <label for="poAuthor" class="form-label">작성자</label>
                    <input type="text" id="poAuthor" name="poAuthor" value= "${signedInUser}" class="form-control" required>
                </div>
    
                <div class="mb-3">
                    <label for="paAttachments" class="form-label">첨부파일</label>
                    <input type="file" name="files" multiple class="form-control">
                </div>
                <div class="text-end">
                    <button type="submit" class="btn btn-primary">작성하기</button>
                </div>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
                crossorigin="anonymous"></script>
        <!-- JavaScript 파일 연동 -->
        <c:url var="postTypeJS" value="/js/post-type.js"/>
        <script src="${postTypeJS}"></script>
    </body>
</html>
