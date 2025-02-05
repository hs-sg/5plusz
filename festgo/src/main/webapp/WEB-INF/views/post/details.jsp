<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fest Go</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
          rel="stylesheet" 
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
          crossorigin="anonymous" />
</head>

<body>
    <div class="container-fluid">
        <c:set var="pageTitle" value="포스트 상세보기" />
        <%@ include file="../fragments/header.jspf" %>

        <main class="mt-2">
            <div class="card">
                <div class="card-body">

                    <form>
                        <div class="mt-2">
                            <label class="form-label" for="id">번호</label>
                            <input class="form-control" id="id" type="text" value="${post.poId}" readonly />
                        </div>
                        <div class="mt-2">
                            <label class="form-label" for="title">제목</label>
                            <input class="form-control" id="title" type="text" value="${post.poTitle}" readonly />
                        </div>
                        <div class="mt-2">
                            <label class="form-label" for="content">내용</label>
                            <div class="form-control" id="content" style="min-height: 200px; white-space: pre-wrap; overflow-y: auto;">
                                ${post.poContent}
                            </div>
                        </div>

                        <!-- 이미지 미리보기 영역 -->
                        
<c:forEach var="attachment" items="${postWithAttachments.attachments}">
    <img id="previewPoContentContainer"
         src="${attachment}"
         alt="이미지 미리보기"
         class="img-thumbnail"
         style="max-width: 200px; height: auto;" />
</c:forEach>



                        <div class="mt-2">
                            <label class="form-label" for="author">작성자</label>
                            <input class="form-control" id="author" type="text" value="${post.poAuthor}" readonly />
                        </div>
                        <div class="mt-2">
                            <label class="form-label" for="createdTime">작성시간</label>
                            <input class="form-control" id="createdTime" type="text" value="${post.poCreatedTime}" readonly/>
                            <label class="form-label" for="modifiedTime">최종수정시간</label>
                            <input class="form-control" id="modifiedTime" type="text" value="${post.poModifiedTime}" readonly/>
                        </div>
                        <div class="mt-2">
                            <label class="form-label" for="views">조회수</label>
                            <input class="form-control" id="views" type="text" value="${post.poViews}" readonly />
                        </div>
                    </form>

                    <%-- 수정 버튼 --%>
                    <div class="card-footer d-flex justify-content-center">
                        <c:url var="postModifyPage" value="/post/modify">
                            <c:param name="poId" value="${post.poId}" />
                        </c:url>
                        <a class="btn btn-outline-primary" href="${postModifyPage}">수정하기</a>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <%-- JavaScript 분리된 파일 로드 --%>
    <c:url var="postPreviewJS" value="/js/post-image-preview.js" /> 
    <script src="${postPreviewJS}"></script>

</body>
</html>
