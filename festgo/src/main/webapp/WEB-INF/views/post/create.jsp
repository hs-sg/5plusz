<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>   
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Fest Go</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
              rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
              crossorigin="anonymous">
    </head>
        <body>
            <div class="container-fluid">
                <c:set var="pageTitle" value="새 글 작성"/>
                <%@ include file="../fragments/header.jspf" %>
            </div>
            <div class="container mt-4">
                <form action="${pageContext.request.contextPath}/post/create" method="post" enctype="multipart/form-data">
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
                        <input type="text" id="poAuthor" name="poAuthor" class="form-control" required>
                    </div>
        
                    
                    <!-- 게시글 유형 선택 (관리자만 보이도록 설정) -->
                    <c:if test="${sessionScope.mrId == 3}">
                        <div class="mb-3">
                            <label for="pcId" class="form-label">게시글 유형</label>
                            <select id="pcId" name="pcId" class="form-select">
                                <option value="1" selected>일반 글</option>
                                <option value="2">공지사항</option>
                            </select>
                        </div>
                    </c:if>
                    
                    <!-- 일반 사용자 및 사업자는 pcId를 1(일반글)로 고정 -->
                    <c:if test="${sessionScope.mrId != 3}">
                        <input type="hidden" name="pcId" value="1">
                    </c:if>

        
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
        </body>
</html>
