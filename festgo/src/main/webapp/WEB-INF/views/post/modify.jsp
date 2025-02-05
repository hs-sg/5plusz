<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
        <head>
            <meta charset="UTF-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Fest go</title>
            
            <!-- Bootstrap CSS -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
                rel="stylesheet" 
                integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
                crossorigin="anonymous">
        </head>
            <body>
                <div class="container-fluid">
                    <c:set var="pageTitle" value="수정"/>
                    <%@ include file="../fragments/header.jspf" %>
            
                    <main class="mt-2">
                        <div class="card">
                            <div class="card-body">
                                <form id="modifyForm" action="/post/update" method="post" enctype="multipart/form-data">
                                   <!-- 게시글 ID -->
                                    <input type="hidden" name="poId" value="${postWithAttachments.post.poId}" />
                                    
                                    <!-- 제목 입력 -->
                                    <div class="mt-2">
                                        <label class="form-label" for="title">제목</label>
                                        <input class="form-control" id="title" type="text" name="poTitle" value="${postWithAttachments.post.poTitle}" required />
                                    </div>
                                    
                                    <!-- 내용 입력 -->
                                    <div class="mt-2">
                                        <label class="form-label" for="content">내용</label>
                                        <textarea class="form-control" id="content" rows="5" name="poContent" required>${postWithAttachments.post.poContent}</textarea>
                                    </div>
            
                                    <!-- 기존 첨부파일 목록 -->
                                    <!-- 기존 첨부파일 목록 -->
                                    <div class="mt-3">
                                        <label class="form-label">현재 첨부파일</label>
                                        <c:choose>
                                            <c:when test="${not empty postWithAttachments.attachments}">
                                                <ul class="list-group">
                                                    <c:forEach var="attachment" items="${postWithAttachments.attachments}">
                                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                                            <c:set var="fileNameParts" value="${fn:split(attachment.paAttachments, '.')}" />
                                                            <c:set var="fileExt" value="${fn:toLowerCase(fileNameParts[fn:length(fileNameParts) - 1])}" />
                                    
                                                            <!-- 이미지 파일이면 미리보기 -->
                                                            <c:choose>
                                                                <c:when test="${fileExt eq 'jpg' or fileExt eq 'jpeg' or fileExt eq 'png' or fileExt eq 'gif'}">
                                                                    <img src="/attachments/${attachment.paAttachments}" class="img-thumbnail" style="max-width: 100px;">
                                                                </c:when>
                                                            </c:choose>
                                    
                                                            <span>${attachment.paAttachments}</span>
                                                            <input type="checkbox" name="removeFiles" value="${attachment.paId}" class="form-check-input">
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                                <small class="text-danger">삭제할 파일을 선택하세요.</small>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="text-muted">첨부파일이 없습니다.</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
            
            
                                    <!-- 새 첨부파일 추가 -->
                                    <div class="mt-3">
                                        <label class="form-label">새로운 첨부파일 추가</label>
                                        <input type="file" name="files" class="form-control" multiple />
                                    </div>
            
                                    <!-- 수정 & 삭제 버튼 -->
                                    <div class="card-footer d-flex justify-content-end mt-3">
                                        <button type="button" id="btnDelete" class="me-2 btn btn-outline-danger">삭제</button>
                                        <button type="submit" id="btnUpdate" class="btn btn-outline-success">업데이트</button>       
                                    </div>
                                </form>
                            </div>
                        </div>
                    </main>
                </div>
            
                <!-- JavaScript 및 Bootstrap -->
                <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
                    integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
                    crossorigin="anonymous"></script>
            
                <!-- JavaScript 파일 연동 -->
                <c:url var="postModifyJS" value="/js/post-modify.js"/>
                <script src="${postModifyJS}"></script>
            </body>
</html>
