<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정 -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Fest Go</title>

        <!-- Bootstrap CSS 링크. -->
        <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
        crossorigin="anonymous">
        
        <!-- Swiper CSS -->
        <link href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" rel="stylesheet"/>
            
        <c:url var="mypageCSS" value="/css/mypage.css"/>
        <link href="${ mypageCSS }" rel="stylesheet"/>
    </head>
    <body>
		<div class="container-fluid">
			<c:set var="pageTitle" value="마이페이지" />
			<%@ include file="../fragments/header.jspf"%>
	    </div>
        <main>
            <div class="mypage-container">
	            <div class="row">
					<div class="col-2 sticky-sidebar d-flex flex-column align-item-center">
						<button class="btn" id="btnToggleMyProfile">내 프로필</button>
						<br />
						<c:if test="${member.mrId eq 1 }">
	                        <button class="btn" id="btnToggleFestivalList">찜한축제</button>
		                    <br />
		                    <button class="btn" id="btnTogglePostList">작성글</button>
		                    <br />
		                    <button class="btn" id="btnToggleReviewList">작성리뷰</button>
	                    </c:if>
						<c:if test="${member.mrId eq 2 }">
					        <button class="btn" id="btnToggleFestivalList">등록축제</button>
	                        <br />
	                        <button class="btn" id="btnTogglePostList">작성글</button>
	                    </c:if>
						<c:if test="${member.mrId eq 3 }">
						    <button class="btn" id="btnToggleFestivalList">전체축제</button>
	                        <br />
	                        <button class="btn" id="btnTogglePostList">전체글</button>
	                        <br />
	                        <button class="btn" id="btnToggleReviewList">전체리뷰</button>
	                        <br />
	                        <button class="btn" id="btnToggleSponsorCheckList">사업자승인</button>
	                    </c:if>
					</div>
					<div class="col sticky-div">
		                <section>
		                    <div id="divMyProfile">
		                    </div>
		                    <div id="divFestivalList">
		                    </div>
		                    <div id="divPostList">
		                    </div>
		                    <c:if test="${member.mrId eq 1 or member.mrId eq 3 }">
		                       <div id="divReviewList">
		                       </div>
		                    </c:if>
		                    <c:if test="${member.mrId eq 3 }">
			                    <div id="divSponsorCheckList">
			                    </div>
		                    </c:if>
		                </section>
	                </div>
				</div>
            </div>
		</main>
        <!-- Bootstrap JS -->
        <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
	
        <!-- Axios Http JS -->
        <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
        
        <script>
            const signedInUser = '${signedInUser}';
            const role = '${member.mrId}';
        </script>

        <c:url var="mypageJS" value="/js/mypage.js" />
        <script src="${mypageJS}"></script>
    </body>
</html>