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
    <div class="container mt-4">
        <h2 class="mb-4">새 글 작성</h2>
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
            <div class="mb-3">
                <label for="paAttachments" class="form-label">첨부파일</label>
                <input type="file" id="paAttachments" name="file" class="form-control">
            </div>
            <div class="text-end">
                <button type="submit" class="btn btn-primary">작성하기</button>
            </div>
        </form>
    </div>
</body>
</html>
