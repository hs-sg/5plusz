<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  <%-- JSTL Functions 추가 --%>

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
                    <c:set var="post" value="${postWithAttachments.post}" />
                    <c:set var="attachments" value="${postWithAttachments.attachments}" />
                    <c:set var="imageAttachments" value="" />
                    <c:set var="fileAttachments" value="" />
                    
                    <c:forEach var="attachment" items="${attachments}">
                        <c:choose>
                            <%-- 이미지 파일이면 imageAttachments에 추가 --%>
                            <c:when test="${fn:endsWith(attachment.paAttachments, '.jpg') 
                                           or fn:endsWith(attachment.paAttachments, '.jpeg') 
                                           or fn:endsWith(attachment.paAttachments, '.png') 
                                           or fn:endsWith(attachment.paAttachments, '.gif')}">
                                <c:set var="imageAttachments" value="${imageAttachments},${attachment.paAttachments}" />
                            </c:when>
                            <%-- 일반 파일이면 fileAttachments에 추가 --%>
                            <c:otherwise>
                                <c:set var="fileAttachments" value="${fileAttachments},${attachment.paAttachments}" />
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    
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
                                <c:if test="${not empty imageAttachments}">
                                    <br>
                                    <c:forTokens var="image" items="${imageAttachments}" delims="," >
                                        <img src="/attachments/${image}" 
                                             alt="첨부 이미지" class="img-fluid rounded mt-2" 
                                             style="max-width: 100%; height: auto;" />
                                    </c:forTokens>
                                </c:if>
                            </div>
                        </div>
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
                    
                    <!-- 일반 파일 다운로드 목록 -->
                    <c:if test="${not empty fileAttachments}">
                        <div class="mt-3">
                            <h5>첨부파일</h5>
                            <ul class="list-group">
                                <c:forTokens var="file" items="${fileAttachments}" delims="," >
                                    <li class="list-group-item">
                                        <a href="/attachments/${file}" download="${file}">
                                            ${file} 다운로드
                                        </a>
                                    </li>
                                </c:forTokens>
                            </ul>
                        </div>
                    </c:if>

                    <!-- 수정 버튼 -->
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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
            crossorigin="anonymous">
    </script>
</body>
</html>
