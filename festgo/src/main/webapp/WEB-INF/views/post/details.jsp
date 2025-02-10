<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

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
          
          <style>
			/* 썸네일 이미지 크기 고정 */
			.preview-img {
			    width: 200px;  /* 일정한 너비 */
			    height: 200px; /* 일정한 높이 */
			    object-fit: cover; /* 이미지가 깨지지 않게 자동 조정 */
			    cursor: pointer; /* 클릭 가능 */
			    border-radius: 8px; /* 둥근 모서리 */
			    transition: transform 0.2s ease-in-out;
			}
			
			.preview-img:hover {
			    transform: scale(1.05); /* 마우스 호버 시 확대 효과 */
			}
			
			/* 모달 내 이미지 크기 조정 */
			.modal-img {
			    max-width: 100%;
			    max-height: 600px; /* 최대 높이 설정 */
			    width: auto;
			    height: auto;
			    display: block;
			    margin: auto;
			}
			</style>
</head>

<body>
    <div class="container-fluid">
        <c:set var="pageTitle" value="상세보기" />
        <%@ include file="../fragments/header.jspf" %>

        <main class="mt-2">
            <div class="card">
                <div class="card-body">

                    <form>
                        <div class="mt-2">
                            <label class="form-label" for="id">번호</label>
                            <input class="form-control" id="id" type="text" value="${postWithAttachments.post.poId}" readonly />
                        </div>
                        <div class="mt-2">
                            <label class="form-label" for="title">제목</label>
                            <input class="form-control" id="title" type="text" value="${postWithAttachments.post.poTitle}" readonly />
                        </div>
                        <div class="mt-2">
                            <label class="form-label" for="content">내용</label>
                            <div class="form-control" id="content" style="min-height: 200px; overflow-y: auto;">
                                <c:out value="${fn:trim(postWithAttachments.post.poContent)}"/>
                            </div>
                        </div>

                        <!-- 이미지 미리보기 영역 -->
						<div class="mt-2">
						    <label class="form-label">첨부 이미지</label>
						    <div class="d-flex flex-wrap gap-2">
						        <c:forEach var="attachment" items="${postWithAttachments.attachments}">
						            <c:set var="fileNameParts" value="${fn:split(attachment.paAttachments, '.')}" />
						            <c:set var="fileExt" value="${fn:toLowerCase(fileNameParts[fn:length(fileNameParts) - 1])}" />
						            
						            <c:choose>
						                <c:when test="${fileExt eq 'jpg' || fileExt eq 'jpeg' || fileExt eq 'png' || fileExt eq 'gif'}">
						                    <img src="${pageContext.request.contextPath}/post/uploads/${attachment.paAttachments}"
						                         alt="첨부 이미지"
						                         class="img-thumbnail preview-img"
						                         data-bs-toggle="modal"
						                         data-bs-target="#imageModal"
						                         onclick="showModal(this)" />
						                </c:when>
						                <c:otherwise>
						                    <!-- 비 이미지 파일의 경우 -->
						                    <span class="material-icons">attachment</span>
						                    <span>${attachment.paAttachments}</span>
						                </c:otherwise>
						            </c:choose>
						        </c:forEach>
						    </div>
						</div>

                        <div class="mt-2">
                            <label class="form-label" for="author">작성자</label>
                            <input class="form-control" id="author" type="text" value="${postWithAttachments.post.poAuthor}" readonly />
                        </div>
                        <div class="mt-2">
                            <label class="form-label" for="createdTime">작성시간</label>
                            <input class="form-control" id="createdTime" type="text" value="${postWithAttachments.post.poCreatedTime}" readonly/>
                            <label class="form-label" for="modifiedTime">최종수정시간</label>
                            <input class="form-control" id="modifiedTime" type="text" value="${postWithAttachments.post.poModifiedTime}" readonly/>
                        </div>
                        <div class="mt-2">
                            <label class="form-label" for="views">조회수</label>
                            <input class="form-control" id="views" type="text" value="${postWithAttachments.post.poViews}" readonly />
                        </div>
                    </form>

                    <%-- 수정 버튼 --%>
                    <%-- 로그인 사용자와 포스트 작성자가 같은 경우에만 수정하기 버튼 보여줌 --%>
                    <c:if test="${sessionScope.signedInUser eq postWithAttachments.post.poAuthor}">

                        <div class="card-footer d-flex justify-content-center">
                            <c:url var="postModifyPage" value="/post/modify">
                                <c:param name="poId" value="${postWithAttachments.post.poId}" />
                            </c:url>
                            <a class="btn btn-outline-primary" href="${postModifyPage}">수정하기</a>
                        </div>
                    </c:if>
                </div>
            </div>
        </main>
    </div>



	<!-- 모달 (클릭하면 크게 보기) -->
	<div class="modal fade" id="imageModal" tabindex="-1" aria-labelledby="imageModalLabel" aria-hidden="true">
	    <div class="modal-dialog modal-lg modal-dialog-centered">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="imageModalLabel">이미지 미리보기</h5>
	            </div>
	            <div class="modal-body text-center">
	                <img id="modalImage" src="" class="modal-img" />
	            </div>
	        </div>
	    </div>
	</div>



    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- JavaScript 코드 -->
	<script>
		document.addEventListener("DOMContentLoaded", function () {
		    // 모든 이미지 요소 가져오기
		    document.querySelectorAll(".img-thumbnail").forEach(img => {
		        img.addEventListener("click", function () {
		            const modalImage = document.getElementById("modalImage"); // 모달 내부 이미지
		            modalImage.src = this.src; // 클릭한 이미지의 src 적용
		            const imageModal = new bootstrap.Modal(document.getElementById("imageModal")); // 부트스트랩 모달 객체 생성
		            imageModal.show(); // 모달 표시
		        });
		    });
		});
	</script>
	
	<script>
		function showModal(imgElement) {
		    const modalImage = document.getElementById("modalImage"); // 모달 내부 이미지
		    modalImage.src = imgElement.src; // 클릭한 이미지의 src 적용
		    modalImage.alt = "첨부 이미지 미리보기"; // 접근성 향상
		}
	</script>

</body>
</html>
